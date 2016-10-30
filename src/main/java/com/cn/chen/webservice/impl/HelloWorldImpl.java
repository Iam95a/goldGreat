package com.cn.chen.webservice.impl;

import javax.jws.WebService;

import com.cn.chen.webservice.HelloWorld;
@WebService
public class HelloWorldImpl implements HelloWorld{

	public String helloworld(String city) {
		
		return city+"hello";
	}

}
