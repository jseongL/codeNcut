package com.jsL.codeNcut.buy.elasticSynchronization;



import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jsL.codeNcut.buy.domain.Buy;
import com.jsL.codeNcut.buy.domain.BuyDocument;
import com.jsL.codeNcut.buy.repository.BuyRepository;
import com.jsL.codeNcut.buy.repository.BuySearchRepository;

@Component
public class ElasticsearchInitializer implements CommandLineRunner {

    private final BuyRepository buyRepository;
    private final BuySearchRepository buySearchRepository;

    public ElasticsearchInitializer(BuyRepository buyRepository, BuySearchRepository buySearchRepository) {
        this.buyRepository = buyRepository;
        this.buySearchRepository = buySearchRepository;
    }

    @Override
    public void run(String... args) {
        List<Buy> buys = buyRepository.findAll();
        List<BuyDocument>documentsResult = new ArrayList<>();
        for(Buy buy:buys) {
        	BuyDocument documents = BuyDocument.builder()
        			.id(buy.getId())
                    .model(buy.getModel())
                    .description(buy.getDescription())
                    .build();
        	documentsResult.add(documents);
        }

        if (buySearchRepository.count() == 0) {
            buySearchRepository.saveAll(documentsResult);
            System.out.println("Elasticsearch에 초기 데이터 세팅 완료");
        } else {
            System.out.println("이미 Elasticsearch에 데이터가 있습니다.");
        }
    }
}