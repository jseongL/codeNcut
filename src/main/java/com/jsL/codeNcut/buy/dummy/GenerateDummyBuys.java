//package com.jsL.codeNcut.buy.dummy;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.jsL.codeNcut.buy.domain.Buy;
//import com.jsL.codeNcut.buy.domain.BuyDocument;
//import com.jsL.codeNcut.buy.repository.BuyRepository;
//import com.jsL.codeNcut.buy.repository.BuySearchRepository;
//
//import jakarta.annotation.PostConstruct;
//
//@Component
//public class GenerateDummyBuys {
//
//    @Autowired
//    private BuyRepository buyRepository;
//
//    @Autowired
//    private BuySearchRepository buySearchRepository;
//
//    @PostConstruct
//    public void generateDummyBuys() {
//        List<Buy> dummyBuys = new ArrayList<>();
//
//        for (int i = 0; i < 100; i++) {
//            Buy buy = Buy.builder()
//                .userId(4)
//                .description("테스트 설명 " + i)
//                .model("모델명 " + (i % 100))
//                .buyYear(2020 + (i % 5))
//                .price(100000 + (i * 10))
//                .imgPath("/images/test" + (i % 10) + ".jpg")
//                .status(i % 2 == 0 ? "판매중" : "판매완료")
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//            dummyBuys.add(buy);
//        }
//
//        // 1. MySQL에 저장
//        buyRepository.saveAll(dummyBuys);
//
//        // 2. Elasticsearch에 저장할 BuyDocument 리스트로 변환
//        List<BuyDocument> documents = dummyBuys.stream()
//            .map(b -> BuyDocument.builder()
//                .id(b.getId()) // 저장 후 자동 생성된 ID는 buyRepository.saveAll() 이후에 채워져 있어야 함
//                .userId(b.getUserId())
//                .model(b.getModel())
//                .description(b.getDescription())
//                .build()
//            ).collect(Collectors.toList());
//
//        // 3. Elasticsearch 저장
//        buySearchRepository.saveAll(documents);
//
//        System.out.println("100개의 Buy + BuyDocument 데이터 생성 완료");
//    }
//}