package com.jsL.codeNcut.redis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/redis")
public class RedisController {

	@GetMapping("/redisTest-view")
	public String redisView() {
		return "redis/redisTest";
	}
	
}
