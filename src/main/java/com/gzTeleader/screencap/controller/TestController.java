package com.gzTeleader.screencap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzTeleader.screencap.common.Result;



@Controller
//@RequestMapping("/Test")
public class TestController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@ResponseBody
	@RequestMapping(value = "/test")
	public Result<Object> test() {
		Result<Object> result = new Result<Object>();
		result.setData("success");
		return result;
	}
	
	@RequestMapping(value = {"/","/index"})
	public String index(Model model) {
		logger.trace("index");
		return "index";
	}
	
	@RequestMapping(value = "/screencap")
	public String screencap(Model model) {
		logger.trace("screencap");
		return "screencap";
	}

}
