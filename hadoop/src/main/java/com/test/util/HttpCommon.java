package com.test.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpCommon {
	
	private static final Logger logger = LogManager.getLogger(HttpCommon.class);

	private final static int TIME_OUT = 15000;

	private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIME_OUT)
	        .setConnectTimeout(TIME_OUT).setConnectionRequestTimeout(TIME_OUT).build();

	public static String get(String url) throws Exception {
		HttpGet get = new HttpGet(url);
		get.setConfig(requestConfig);

		CloseableHttpClient client = HttpClientBuilder.create().build();

		HttpResponse response = client.execute(get);

		StringBuilder sb = new StringBuilder();

		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();

			InputStream in = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} else {
			sb.append("error");
		}
		return sb.toString();
	}

	public static String post(String url, HttpEntity reqEntity) {
		long start = System.currentTimeMillis();
		String message = null;
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);
			post.setEntity(reqEntity);
			post.setConfig(requestConfig);
			HttpResponse response = client.execute(post);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity resEntity = response.getEntity();
				message = EntityUtils.toString(resEntity, "utf-8");
			} else {
				logger.error("url: " + url + ", status: " + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			logger.error(ThrowableUtil.getErrorInfoFromThrowable(e));
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000 + "秒");
		return message;
	}

}
