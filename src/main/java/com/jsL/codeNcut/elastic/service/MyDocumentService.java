package com.jsL.codeNcut.elastic.service;

import org.springframework.stereotype.Service;

import com.jsL.codeNcut.elastic.domain.MyDocument;
import com.jsL.codeNcut.elastic.repository.MyDocumentRepository;

@Service
public class MyDocumentService {

    private final MyDocumentRepository repository;

    public MyDocumentService(MyDocumentRepository repository) {
        this.repository = repository;
    }

    public MyDocument saveA(MyDocument doc) {
        return repository.save(doc);
    }

    public Iterable<MyDocument> findAllA() {
        return repository.findAll();
    }
    
    
    
    public void deleteA(String id) {
        repository.deleteById(id);
    }
    
    
    
    
}