package com.cn.chen.controller;

import java.io.InputStream;
import java.io.OutputStream;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cn.chen.domain.model.CheckModel;
import com.cn.chen.service.WeatherService;
import com.cn.chen.service.WechatService;
import com.cn.chen.util.EncodeHandler;

@Controller
@RequestMapping("/weixin")
public class WeiXinController {
	@Autowired
	private WechatService wechatService;

	@Autowired
	private WeatherService weatherService;

	@RequestMapping(value = "/notify", method = RequestMethod.GET)
	public void getnotify(HttpServletResponse response, HttpServletRequest request, CheckModel checkModel)
			throws Exception {
		try {
			String echostr = checkModel.getEchostr();
			Long nonce = checkModel.getNonce();
			String signature = checkModel.getSignature();
			Long timestamp = checkModel.getTimestamp();
			String[] strings = { "1029huang", nonce + "", timestamp + "" };
			Arrays.sort(strings);
			String bigString = strings[0] + strings[1] + strings[2];
			String sha1str = EncodeHandler.encode("SHA1", bigString);
			if (sha1str.equals(signature)) {
				OutputStream os = response.getOutputStream();
				os.write(echostr.getBytes("UTF-8"));
				os.flush();
				os.close();
			}
		} catch (Exception e) {

		}

	}

	@RequestMapping(value = "/notify", method = RequestMethod.POST)
	public void getmessage(HttpServletResponse response, HttpServletRequest request, CheckModel checkModel)
			throws Exception {

		try {
			String echostr = checkModel.getEchostr();
			Long nonce = checkModel.getNonce();
			String signature = checkModel.getSignature();
			Long timestamp = checkModel.getTimestamp();

			InputStream inputStream = request.getInputStream();
			String postData = IOUtils.toString(inputStream, "UTF-8");
			System.out.println(postData);
			String cityname = wechatService.processRequest(postData);
			
			System.out.println(cityname);

			OutputStream os = response.getOutputStream();
			os.write(cityname.getBytes("UTF-8"));
			os.flush();
			os.close();

		} catch (Exception e) {

		}

	}

}
