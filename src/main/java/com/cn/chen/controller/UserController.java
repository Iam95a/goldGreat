package com.cn.chen.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cn.chen.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.chen.service.UserService;
import com.cn.chen.service.WeatherService;
import com.cn.chen.webxml.mobile.MobileCodeWSSoap;


@Controller
@RequestMapping("/chen")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private MobileCodeWSSoap mobileCodeWSSoap;
	
	@Autowired
	private WeatherService weatherService;

	@RequestMapping("/getUser")
	@ResponseBody
	@Log
	public Map<String, Object> getUser() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", userService.getUser());
		return map;
	}

	/**
	 * 根据手机号获取其所在地址，调用webxml的webservice
	 * @param request
	 * @param response
	 * @param mobile
	 * @return
	 */
	@RequestMapping("/getMobile")
	@ResponseBody
	public Map<String, Object> getMobile(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="mobile",required=true)String mobile) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("mobile", mobileCodeWSSoap.getMobileCodeInfo(mobile, ""));
		return map;
	}
	
	@RequestMapping("/getMobileAll")
	@ResponseBody
	public Map<String, Object> getMobileAll(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("mobile", mobileCodeWSSoap.getDatabaseInfo());
		return map;
	}
	@RequestMapping("/getWeather")
	@ResponseBody
	public Map<String, Object> getWeather()
	{
		Map<String, Object> map=new HashMap<String, Object>();
		String weather=weatherService.getWeather("");
		System.out.println(weather);
		map.put("Weather",weatherService.getWeather("") );
		return map;
	}
	
	
	

}
