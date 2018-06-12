package com.test;

import com.test.util.HttpCommon;

public class DownloadFileTest   {
	
	public static void main(String[] args) throws Exception {
//		HttpCommon.get("http://192.168.56.200:8080/downloadFromHdfs?sourcePath=/usr/upload/zabbix_agentd.conf");
//		HttpCommon.get("http://192.168.56.200:8080/downloadFromHdfs?sourcePath=/usr/dedup_out/part-r-00000");
		HttpCommon.get("http://192.168.56.200:8080/downloadFromHdfs?sourcePath=/usr/dedup_in/file1.txt");
	}
	
}
