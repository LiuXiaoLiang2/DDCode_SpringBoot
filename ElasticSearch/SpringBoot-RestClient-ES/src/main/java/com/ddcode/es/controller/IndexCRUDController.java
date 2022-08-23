package com.ddcode.es.controller;

import com.ddcode.es.service.EsIndexService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/es")
public class IndexCRUDController {

    /**
     * 操作索引
     */

    @Resource
    private EsIndexService esIndexService;

    @RequestMapping("/createIndex1")
    public String createIndex1() throws IOException {
        esIndexService.createIndex1();
        return "ok";
    }

    @RequestMapping("/createIndex2")
    public String createIndex2() throws IOException {
        esIndexService.createIndex2();
        return "ok";
    }

    @RequestMapping("/createIndex3")
    public String createIndex3() throws IOException {
        esIndexService.createIndex3();
        return "ok";
    }

    @RequestMapping("/createIndexAsync")
    public String createIndexAsync() throws IOException {
        esIndexService.createIndexAsync();
        return "ok";
    }

    @RequestMapping("/existIndex")
    public String existIndex() throws IOException {
        esIndexService.existIndex();
        return "ok";
    }

    @RequestMapping("/closeIndex")
    public String closeIndex() throws IOException {
        esIndexService.closeIndex();
        return "ok";
    }


    @RequestMapping("/openIndex")
    public String openIndex() throws IOException {
        esIndexService.openIndex();
        return "ok";
    }
}
