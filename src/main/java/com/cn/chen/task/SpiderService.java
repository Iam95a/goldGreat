package com.cn.chen.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.stereotype.Service;

@Service
public class SpiderService {
	public static HttpClient httpClient = new HttpClient();

	public static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();

	static {
		httpClient.getHostConfiguration().setProxy("127.0.0.1", 8087);
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0");
		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		httpClient.getParams().setParameter("http.protocol.single-cookie-header", true);

	}

	public static String downLoadPage(String path) throws HttpException, IOException {
		GetMethod getMethod = new GetMethod(path);
		getMethod.setRequestHeader("Cookie",
				"__cfduid=dba5c09b9ce74a612d64846e8b11bf9501472271546; PHPSESSID=qq0r74nbje5i5n7ek20i18uf95; CNZZDATA950900=cnzz_eid%3D102120659-1473227584-%26ntime%3D1473227584");
		int statusCode = httpClient.executeMethod(getMethod);
		if (statusCode == 200) {

			String string = getMethod.getResponseBodyAsString();
			System.out.println(string);
			String pattern = "htm_data/[0-9]{2}/[0-9]{4}/[0-9]{7}.html";
			Pattern r = Pattern.compile(pattern);
			Matcher matcher = r.matcher(string);
			Set<String> mySet1 = new HashSet<String>();
			while (matcher.find()) {
				String str = matcher.group();
				mySet1.add("http://www.t66y.com/" + str);
			}
			for (String string2 : mySet1) {
				String string3 = downLoadPage(string2);
				downLoadImage(string3);
			}
			// System.out.println(string);
			return string;
		}
		getMethod.releaseConnection();
		return null;

	}

	public void executeDownImage(){
		try {
			downLoadPage("http://www.t66y.com/thread0806.php?fid=16&page=6");

			for (String string2 : queue) {
				System.out.println(string2);
				downImageByUrl(string2);
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void downLoadImage(String string) {
		try {
			if (string != null) {
				String pattern = "http:\\/\\/\\.?.*?\\.[jp][np][eg](g)?";
				Pattern r = Pattern.compile(pattern);
				Matcher matcher = r.matcher(string);
				Set<String> mySet = new HashSet<String>();
				while (matcher.find()) {
					String str = matcher.group();
					if (str.startsWith("http://www.vii")) {
						continue;
					}
					mySet.add(str);
				}
				for (String string2 : mySet) {
					queue.add(string2);
				}
			} else {

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void downImageByUrl(String url) {
		GetMethod getMethod = null;
		try {
			getMethod = new GetMethod(url);
			System.out.println(url);

			getMethod.setRequestHeader("Cookie",
					"__cfduid=d5122264b19f6ab58cf781bde734eba2d1472946064; PHPSESSID=27dnrueg5nmubvhsqafigjm1l5; CNZZDATA950900=cnzz_eid%3D516341094-1472941384-%26ntime%3D1472941384");
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode == 200) {
				String filename = UUID.randomUUID().toString().replace("-", "");
				int dot = url.lastIndexOf(".");
				String extName = url.substring(dot);
				File storeFile = new File("e:\\1024\\" + filename + extName);
				String string = getMethod.getResponseBodyAsString();
				FileOutputStream output = new FileOutputStream(storeFile);
				// 得到网络资源的字节数组,并写入文件
				output.write(getMethod.getResponseBody());
				output.close();
			}
			getMethod.releaseConnection();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void concurDownImage() {
		Runnable runnable = new Runnable() {
			public void run() {
				while (queue.size() > 0) {
					String url = queue.poll();
					System.out.println(url);
					downImageByUrl(url);
				}
			}
		};
		Thread thread = new Thread(runnable);
		Thread thread1 = new Thread(runnable);
		Thread thread2 = new Thread(runnable);
		Thread thread3 = new Thread(runnable);
		thread.start();
		thread1.start();
		thread2.start();
		thread3.start();
	}
}
