package com.jsL.codeNcut.akbo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/akbo")
public class AkcoController {
	
	@GetMapping("akbo-view")
	public String akboView() {
		return "/akbo/akbo";
	}

}
