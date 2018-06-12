package com.test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.test.util.HttpCommon;

public class HadoopTest {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		String dst = "/usr/dedup_in";
		String dstOut = "/usr/dedup_out/" + System.currentTimeMillis(); 
		formparams.add(new BasicNameValuePair("dst", dst));
		formparams.add(new BasicNameValuePair("dstOut", dstOut));
		HttpEntity httpEntity = new UrlEncodedFormEntity(formparams, "utf-8");
		HttpCommon.post("http://192.168.56.200:8080/dedup", httpEntity);
	}

}
