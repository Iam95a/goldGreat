package com.cn.chen.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;


public class WeatherUtil {

	public static String getWeather(String httpUrl) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		try {
			URL url = new URL(httpUrl);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Gson gson=new Gson();
		Map<String, Object> map=gson.fromJson(result, Map.class);
		List<Object> list=(List)map.get("HeWeather data service 3.0");
		
		Map<String, Map<String,Object>> map2=(Map)list.get(0);
		List<Map<String, Object>> list2=(List<Map<String,Object>>)map2.get("daily_forecast");
		result="";
		for (Map<String, Object> map3 : list2) {
			String date=(String)map3.get("date");
			Map<String, Object> map4=(Map)map3.get("cond");
			Map<String, Object> map5=(Map)map3.get("tmp");
			String day=(String)map4.get("txt_d");
			String night=(String)map4.get("txt_n");
			
			String max=(String)map5.get("max");
			String min=(String)map5.get("min");
			result+="{"+date+":"+"白天："+day+" "+"夜晚:"+night+" "+"温度"+min+"-"+max+"}";
		}
		return result;
	}
}
