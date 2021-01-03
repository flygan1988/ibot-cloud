package com.taiping.ibot.server.knowledge.repository.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.ArrayList;
import java.util.List;

public abstract class EsRepository<T> {
    public abstract boolean isExistIndex();

    public abstract void createIndex();

    public abstract void deleteIndex();

    public abstract void addOne(T t);

    public abstract void addBatch(List<T> list);

    public abstract void update(T t);

    public abstract void deleteOne(Long id);

    public List<T> processSearch(RestHighLevelClient restHighLevelClient, SearchRequest request, Class<T> clzz){
        List<T> result = null;

        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

            if (response == null || response.getHits() == null) {
                return null;
            }

            SearchHits hits = response.getHits();
            SearchHit[] searchHits = hits.getHits();
            result = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
                JSONObject jsonObject = JSON.parseObject(hit.getSourceAsString());
                result.add(JSON.toJavaObject(jsonObject, clzz));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
