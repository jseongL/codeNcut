package com.jsL.codeNcut.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.jsL.codeNcut.elastic.domain.MyDocument;

public interface MyDocumentRepository extends ElasticsearchRepository<MyDocument, String> {
}
