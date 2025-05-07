//package com.jsL.codeNcut.redis;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/redis")
//public class RedisRestController {
//
//    private final RedisService redisService;
//
//    public RedisRestController(RedisService redisService) {
//        this.redisService = redisService;
//    }
//
//    @PostMapping("/set")
//    public ResponseEntity<String> set(@RequestParam String key, @RequestParam String value) {
//        redisService.setValue(key, value);
//        return ResponseEntity.ok("Saved");
//    }
//    
//
//    @GetMapping("/get")
//    public ResponseEntity<String> get(@RequestParam String key) {
//        String value = redisService.getValue(key);
//        return value != null ? ResponseEntity.ok(value) : ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping("/delete")
//    public ResponseEntity<String> delete(@RequestParam String key) {
//        redisService.deleteKey(key);
//        return ResponseEntity.ok("Deleted");
//    }
//}