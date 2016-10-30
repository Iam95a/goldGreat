package com.cn.chen.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.google.gson.Gson;

public class HttpUtil {
	public static void main(String[] args) {
		String httpUrl = "http://api.map.baidu.com/geocoder/v2/?ak=E4805d16520de693a3fe707cdc962045&callback=renderReverse&location="
				+ "39.983424,116.322987" + "&output=json&pois=1";
		String string=queryaddr("39.983424","116.322987");
		System.out.println(string);
		//System.out.println(queryaddr("39.983424","116.322987"));
	}
	public static String queryaddr(String lat,String lon){
		String httpUrl = "http://api.map.baidu.com/geocoder/v2/?ak=tAWOoKWUU7XB3ct3r7rnoQQ4&callback=renderReverse&location="
				+lat+ ","+lon+ "&output=json&pois=1";
		String string= request(httpUrl);
		String string2=string.substring(29,string.length()-3);
		//System.out.println(string2);
		Gson gson=new Gson();
		Map<String, Object> map=gson.fromJson(string2, Map.class);
		Map<String, Object> map2=(Map)map.get("result");
		Map<String, Object> map3=(Map)map2.get("addressComponent");
		String city=(String) map3.get("city");
		if(city.endsWith("市")){
			city=city.substring(0,city.length()-1);
		}
		//System.out.println(city);
		return city;
	}

	public static String request(String httpUrl) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", "您自己的apikey");
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
		return result;
	}
}
