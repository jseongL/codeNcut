package com.jsL.codeNcut.buy.repository;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.jsL.codeNcut.buy.domain.BuyDocument;

public interface BuySearchRepository extends ElasticsearchRepository<BuyDocument, Integer> {
	@Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"model\", \"description\"], \"fuzziness\": \"AUTO\"}}")
	List<BuyDocument> search(String text);

}