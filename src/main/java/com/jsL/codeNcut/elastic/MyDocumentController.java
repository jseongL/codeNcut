package com.jsL.codeNcut.elastic;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsL.codeNcut.elastic.domain.MyDocument;
import com.jsL.codeNcut.elastic.service.MyDocumentService;

@RestController
@RequestMapping("/elastic")
public class MyDocumentController {

    private final MyDocumentService service;

    public MyDocumentController(MyDocumentService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public MyDocument save(@RequestBody MyDocument doc) {
        return service.saveA(doc);
    }

    @GetMapping("/get")
    public Iterable<MyDocument> findAll() {
        return service.findAllA();
    }
    
    @DeleteMapping("/delete")
    public void delete(@RequestParam String id) {
        service.deleteA(id);
    }
    
    
}