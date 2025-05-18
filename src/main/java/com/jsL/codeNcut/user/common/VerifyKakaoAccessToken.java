package com.jsL.codeNcut.user.common;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component; // 이거 중요
import org.springframework.web.client.RestTemplate;

@Component  // 빈으로 등록
public class VerifyKakaoAccessToken {

   public Long verifyKakaoAccessToken(String accessToken) {
       RestTemplate restTemplate = new RestTemplate();
       HttpHeaders headers = new HttpHeaders();
       headers.set("Authorization", "Bearer " + accessToken);
       HttpEntity<Void> entity = new HttpEntity<>(headers);

       ResponseEntity<Map> response = restTemplate.exchange(
           "https://kapi.kakao.com/v2/user/me",
           HttpMethod.GET,
           entity,
           Map.class
       );

       Map body = response.getBody();
       if (body == null || !body.containsKey("id")) {
           throw new RuntimeException("카카오 인증 실패");
       }

       return ((Number) body.get("id")).longValue();
   }
}