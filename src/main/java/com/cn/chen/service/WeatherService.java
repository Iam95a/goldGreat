package com.cn.chen.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.PUT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.chen.dao.CitycodeMapper;
import com.cn.chen.domain.Citycode;
import com.cn.chen.util.WeatherUtil;

/**
 * 调用和风天气的api
 * 
 * @author Administrator
 *
 */
@Service
public class WeatherService {

	@Autowired
	private CitycodeMapper citycodeMapper;

	public String getWeather(String cityId) {
		//cityId = "CN101010100";
		String httpUrl = "https://api.heweather.com/x3/weather?cityid=" + cityId
				+ "&key=de5bad8872da40f59dad83c9cfd30c0b";
		return WeatherUtil.getWeather(httpUrl);
	}
	
	public String getCityIdByCityName(String cityName)
	{
		Citycode citycode=citycodeMapper.selectByCityName(cityName);
		return citycode.getCityid();
		
	}

//	public String getCityCode() {
//		String httpUrl = "https://api.heweather.com/x3/citylist?search=allchina&key=de5bad8872da40f59dad83c9cfd30c0b";
//		String citycode = WeatherUtil.getWeather(httpUrl);
//		String pattern = "\\[.*\\]";
//		// 创建 Pattern 对象
//		Pattern r = Pattern.compile(pattern);
//		List<String> list = new ArrayList<String>();
//		// 现在创建 matcher 对象
//		Matcher m = r.matcher(citycode);
//		if (m.find()) {
//			citycode = m.group(0);
//			JSONArray jsonArray = JSONArray.parseArray(citycode);
//			List<Citycode> list2 = new ArrayList<Citycode>();
//			for (Object object : jsonArray) {
//				JSONObject jsonObject = (JSONObject) object;
//
//				Citycode cityentity = new Citycode();
//				cityentity.setCity((String) jsonObject.get("city"));
//				cityentity.setCityid((String) jsonObject.get("id"));
//				cityentity.setCnty((String) jsonObject.get("cnty"));
//				cityentity.setLat((String) jsonObject.get("lat"));
//				cityentity.setLon((String) jsonObject.get("lon"));
//				cityentity.setProv((String) jsonObject.get("prov"));
//
//				list2.add(cityentity);
//				// String
//			}
//			for (Citycode citycode2 : list2) {
//				citycodeMapper.insertSelective(citycode2);
//			}
//
//		} else {
//			System.out.println("NO MATCH");
//		}
//
//		return citycode;
//	}
}
