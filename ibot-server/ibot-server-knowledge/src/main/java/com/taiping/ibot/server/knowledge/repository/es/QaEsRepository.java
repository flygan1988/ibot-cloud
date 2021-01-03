package com.taiping.ibot.server.knowledge.repository.es;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taiping.ibot.server.knowledge.entity.qa.Qa;
import com.taiping.ibot.server.knowledge.service.IQaService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class QaEsRepository extends EsRepository<Qa> {

    @Value("${elasticsearch.index.name}")
    private String INDEX_NAME;

    @Value("${elasticsearch.index.type}")
    private String INDEX_TYPE;

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    @Lazy
    private IQaService qaService;

    @Autowired
    public QaEsRepository(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public boolean isExistIndex() {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(INDEX_NAME);
        boolean exist = false;
        try {
            exist = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exist;
    }

    @Override
    public void createIndex() {
        if (isExistIndex()) return;
        CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);
        CreateIndexResponse response = null;
        try {
            XContentBuilder builder = generateBuilder();
            request.mapping(INDEX_TYPE, builder);
            response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            log.info("Create Index: {}, Create Response: {}", INDEX_NAME, JSON.toJSONString(response));
        } catch (IOException e) {
            log.error("error occurs when creating Index: {}, Create Response: {}", INDEX_NAME, JSON.toJSONString(response));
            e.printStackTrace();
        }
    }

    private XContentBuilder generateBuilder() throws IOException {
        XContentBuilder builder = JsonXContent.contentBuilder();
        builder.startObject();
        builder.startObject("properties");
        builder.startObject("question");
        builder.field("type", "text");
        // 为message字段，设置分词器为 ik_smart(最粗粒度), ik_max_word(细粒度)
        builder.field("analyzer", "ik_max_word");
        builder.endObject();
        builder.endObject();
        builder.endObject();
        return builder;
    }

    @Override
    public void deleteIndex() {
        DeleteIndexRequest deleteRequest = new DeleteIndexRequest(INDEX_NAME);
        DeleteIndexResponse deleteResponse = null;
        try {
            deleteResponse = restHighLevelClient.indices().delete(deleteRequest, RequestOptions.DEFAULT);
            log.info("Delete Index: {}, Delete Response: {}", INDEX_NAME, JSON.toJSONString(deleteResponse));
        } catch (IOException e) {
            log.error("error occurs when deleting index: {}, Delete Response: {}", INDEX_NAME, JSON.toJSONString(deleteResponse));
            e.printStackTrace();
        }
    }

    @Override
    public void addOne(Qa qa) {
        IndexRequest request = new IndexRequest(INDEX_NAME, INDEX_TYPE, qa.getId().toString());
        request.source(JSON.toJSONString(qa), XContentType.JSON);
        IndexResponse response = null;
        try {
            response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("Add Knowledge Question: {}, Index Response: {}", qa, JSON.toJSONString(response));
        } catch (IOException e) {
            log.error("Error occured when adding Knowledge Question: {}, Index Response: {}", qa, JSON.toJSONString(response));
            e.printStackTrace();
        }
    }

    @Override
    public void addBatch(List<Qa> list) {
        BulkRequest request = new BulkRequest();
        for (Qa qa : list) {
            IndexRequest indexRequest = new IndexRequest(INDEX_NAME, INDEX_TYPE, qa.getId().toString());
            indexRequest.source(JSON.toJSONString(qa), XContentType.JSON);
            request.add(indexRequest);
        }
        BulkResponse response = null;
        try {
            response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            log.info("Batch Add Knowledge Question, total: {}, Bulk Response: {}", list.size(), JSON.toJSONString(response));
        } catch (IOException e) {
            log.error("error occurs when Batch Add Knowledge Question, total: {}, Bulk Response: {}", list.size(), JSON.toJSONString(response));
            e.printStackTrace();
        }
    }

    @Override
    public void update(Qa qa) {
        UpdateRequest updateRequest = new UpdateRequest(INDEX_NAME, INDEX_TYPE, qa.getId().toString());
        updateRequest.doc(JSON.toJSONString(qa), XContentType.JSON);
        UpdateResponse updateResponse = null;
        try {
            updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            log.info("Update Knowledge Question: {}, Update Response: {}", qa, JSON.toJSONString(updateResponse));
        } catch (IOException e) {
            log.error("error occurs when updating Knowledge Question: {}, Update Response: {}", qa, JSON.toJSONString(updateResponse));
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOne(Long id) {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX_NAME, INDEX_TYPE, id.toString());

        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            log.info("Delete Knowledge Question Id: {}, Delete Response: {}", id, JSON.toJSONString(deleteResponse));
        } catch (IOException e) {
            log.error("error occurs when deleting Knowledge Question Id: {}, Delete Response: {}", id, JSON.toJSONString(deleteResponse));
            e.printStackTrace();
        }
    }

    public void deleteBath(List<String> idList) {
        if (idList == null || idList.isEmpty()) return;
        BulkRequest bulkRequest = new BulkRequest();
        idList.forEach(id -> {
            DeleteRequest deleteRequest = new DeleteRequest(INDEX_NAME, INDEX_TYPE, id);
            bulkRequest.add(deleteRequest);
        });
        try {
            restHighLevelClient.bulk(bulkRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Page<Qa> highlighter(String query, Long kid, Pageable pageable) throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlighterQuestion = new HighlightBuilder.Field("question");
        HighlightBuilder.Field highlighterAnswer = new HighlightBuilder.Field("answer");
        HighlightBuilder.Field highlighterSim = new HighlightBuilder.Field("similarQuestion");
        highlightBuilder.field(highlighterQuestion);
        highlightBuilder.field(highlighterAnswer);
        highlightBuilder.field(highlighterSim);
        sourceBuilder.highlighter(highlightBuilder);
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(QueryBuilders.matchQuery("question", query));
        boolBuilder.should(QueryBuilders.matchQuery("answer", query));
        boolBuilder.should(QueryBuilders.matchQuery("similarQuestion", query));
        boolBuilder.must(QueryBuilders.termQuery("pid", 0L));
        boolBuilder.must(QueryBuilders.termQuery("kid", kid));

        int pageNum = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        sourceBuilder.query(boolBuilder);
        sourceBuilder.from(pageNum * pageSize);
        sourceBuilder.size(pageSize); // 获取记录数，默认10
        sourceBuilder.fetchSource(); // 第一个是获取字段，第二个是过滤的字段，默认获取全部
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.types(INDEX_TYPE);
        searchRequest.source(sourceBuilder);

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        List<Qa> result = new ArrayList<>();
        for (SearchHit hit : hits.getHits()) {
            JSONObject jsonObject = JSON.parseObject(hit.getSourceAsString());
            Qa qa = JSON.toJavaObject(jsonObject, Qa.class);
            qa = qaService.findQaDetailById(qa.getId());
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlight = highlightFields.get("question");
            Text[] fragments = highlight.fragments();
            String questionHighlighter = fragments[0].string();
            qa.setQuestion(questionHighlighter);

            highlight = highlightFields.get("answer");
            if (highlight != null) {
                fragments = highlight.fragments();
                String answerHighlighter = fragments[0].string();
                qa.setAnswer(answerHighlighter);
            }

            highlight = highlightFields.get("similarQuestion");
            if (highlight != null) {
                fragments = highlight.fragments();
                String simHighlighter = fragments[0].string();
                qa.setSimilarQuestion(simHighlighter);
            }

            result.add(qa);
        }
        Page<Qa> page = new PageImpl<>(result, pageable, hits.totalHits);
        return page;
    }

}
