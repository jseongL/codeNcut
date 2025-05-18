package com.jsL.codeNcut.buy.repository;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.jsL.codeNcut.buy.domain.BuyDocument;

public interface BuySearchRepository extends ElasticsearchRepository<BuyDocument, Integer> {

    @Query("{\"bool\": {\"should\": [" +
            "{\"match_phrase\": {\"model\": \"?0\"}}," +
            "{\"match_phrase\": {\"description\": \"?0\"}}" +
            "]}}")
    List<BuyDocument> search(String text);

    List<BuyDocument> findByUserId(int userId);
}