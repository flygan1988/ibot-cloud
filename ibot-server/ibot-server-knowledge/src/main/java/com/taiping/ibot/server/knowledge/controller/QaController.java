package com.taiping.ibot.server.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.google.common.collect.Lists;
import com.taiping.ibot.common.entity.IBotResponse;
import com.taiping.ibot.common.entity.QueryRequest;
import com.taiping.ibot.common.exception.IBotException;
import com.taiping.ibot.server.knowledge.entity.qa.Qa;
import com.taiping.ibot.server.knowledge.repository.es.QaEsRepository;
import com.taiping.ibot.server.knowledge.service.IQaService;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.taiping.ibot.common.utils.IBotUtil.getDataTable;

@RestController
@RequestMapping("qa")
public class QaController {

    @Autowired
    private IQaService qaService;

    @Autowired
    private QaEsRepository qaEsRepository;

    @GetMapping("test1")
    @PreAuthorize("hasAnyAuthority('user:add')")
    public String test1(){
        return "拥有'user:add'权限";
    }

    @GetMapping("test2")
    @PreAuthorize("hasAnyAuthority('user:update')")
    public String test2(){
        return "拥有'user:update'权限";
    }

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }

    @GetMapping("list")
    @PreAuthorize("hasAnyAuthority('qa:view')")
    public IBotResponse getQa(Qa qa, QueryRequest request) {
        IPage<Qa> page = qaService.findQaDetailPage(qa, request);
        Map<String, Object> data = getDataTable(page);
        return new IBotResponse().data(data);
    }

    @PostMapping("add")
    @PreAuthorize("hasAnyAuthority('qa:add')")
    public void addQa(@RequestBody @Valid Qa qa) {
        qaService.createQa(qa);
    }

    @PostMapping("update")
    @PreAuthorize("hasAnyAuthority('qa:update')")
    public void updateQa(@RequestBody @Valid Qa qa) throws IBotException{
        if (qa.getId() == null) throw new IBotException("QA ID为空");
        qaService.updateQa(qa);
    }

    @GetMapping("delete/{ids}")
    @PreAuthorize("hasAnyAuthority('qa:delete')")
    public void delQa(@PathVariable String ids) {
        String[] qids = ids.split(StringPool.COMMA);
        qaService.deleteQa(qids);
    }

    @PostMapping("import")
    @PreAuthorize("hasAnyAuthority('qa:import')")
    @ResponseBody
    public void excelImport(@RequestParam(value = "uploadFile", required = true) MultipartFile file,
                                    @RequestParam(value = "kid", required = true) Long kid) throws IOException {
        List<Qa> list = new ArrayList<>();
        List<Map<String, Object>> errorList = Lists.newArrayList();

        ExcelKit.$Import(Qa.class).readXlsx(file.getInputStream(), new ExcelReadHandler<Qa>() {

            @Override
            public void onSuccess(int i, int i1, Qa qa) {
                list.add(qa);
            }

            @Override
            public void onError(int i, int i1, List<ExcelErrorField> list) {
                Map<String, Object> map = new HashMap<>();
                map.put("sheetIndex", i);
                map.put("rowIndex", i1);
                map.put("errorFields", list);
                errorList.add(map);
            }
        });
        qaService.importQa(list, kid);
    }

    @GetMapping("download")
    public void downloadFile(HttpServletResponse response) throws IOException{
        Resource resource = new ClassPathResource("知识点导入模板.xlsx");
//        File file = resource.getFile();
        String filename = resource.getFilename();
        InputStream inputStream = resource.getInputStream(); //new FileInputStream(file);
        //强制下载不打开
        response.setContentType("application/force-download");
        OutputStream out = response.getOutputStream();
        //使用URLEncoder来防止文件名乱码或者读取错误
        response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(filename, "UTF-8"));
        int b = 0;
        byte[] buffer = new byte[1000000];
        while (b != -1) {
            b = inputStream.read(buffer);
            if(b!=-1) out.write(buffer, 0, b);
        }
        inputStream.close();
        out.close();
        out.flush();
    }

    @GetMapping("export")
    @PreAuthorize("hasAnyAuthority('qa:export')")
    public void export(HttpServletResponse response, Qa qa) {
        List<Qa> list = this.qaService.exportQa(qa);
        ExcelKit.$Export(Qa.class, response).downXlsx(list, false);
    }

    @GetMapping("search")
    public IBotResponse search(@RequestParam("query") String query,
                               @RequestParam("kid") Long kid,
                               @RequestParam("pageNum") Integer pageNum,
                               @RequestParam("pageSize") Integer pageSize) throws IOException {
        Page<Qa> page = qaEsRepository.highlighter(query, kid, PageRequest.of(pageNum-1, pageSize));
        Map<String, Object> data = getDataTable(page);
        return new IBotResponse().data(data);
    }
}
