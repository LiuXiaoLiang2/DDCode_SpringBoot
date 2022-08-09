package com.ddcode.es.controller;

import com.ddcode.es.service.EsAddService;
import com.ddcode.es.service.EsIndexService;
import com.ddcode.es.service.EsQueryService;
import com.ddcode.es.service.EsUpdateService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/es")
public class EsController {

    @Resource
    private EsQueryService esQueryService;

    @RequestMapping("/get")
    public String get(){
        esQueryService.get();
        return "ok";
    }

    @RequestMapping("/customQuery")
    public String customQuery(){
        esQueryService.customQuery();
        return "ok";
    }

    @RequestMapping("/asyncGet")
    public String AsyncGet(){
        esQueryService.AsyncGet();
        return "ok";
    }


    @Resource
    private EsAddService esAddService;

    @RequestMapping("/add1/{id}")
    public String add1(@PathVariable("id") Long id){
        esAddService.add1(id);
        return "ok";
    }


    @RequestMapping("/add2/{id}")
    public String add2(@PathVariable("id") Long id){
        esAddService.add2(id);
        return "ok";
    }

    @RequestMapping("/add3/{id}")
    public String add3(@PathVariable("id") Long id){
        esAddService.add3(id);
        return "ok";
    }

    @RequestMapping("/addVersion/{id}")
    public String addVersion(@PathVariable("id") Long id){
        esAddService.addVersion(id);
        return "ok";
    }


    @Resource
    private EsUpdateService esUpdateService;

    @RequestMapping("/update")
    public String update(){
        esUpdateService.update();
        return "ok";
    }

    @RequestMapping("/delete")
    public String delete(){
        esUpdateService.delete();
        return "ok";
    }

    @RequestMapping("/bulk")
    public String bulk(){
        esUpdateService.bulk();
        return "ok";
    }


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
