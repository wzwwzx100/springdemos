package com.test.service;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.lib.IdentityReducer;
import org.apache.hadoop.mapred.lib.db.DBConfiguration;
import org.apache.hadoop.mapred.lib.db.DBInputFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.test.hadoop.db.CityRecord;
import com.test.hadoop.mapper.CityMapper;

@Service
public class CityService {
	
	private static final Logger logger = LogManager.getLogger(CityService.class);

	@Autowired
	private Configuration configuration;
	
	@Value("${mysql.driver}")
	private String driver;
	
	@Value("${mysql.url}")
	private String url;
	
	@Value("${mysql.username}")
	private String username;
	
	@Value("${mysql.password}")
	private String password;
	
	@Value("${hdfs.host}")
	private String hdfsHost;

	public void findAllCities(String destPath) throws IOException {
		logger.info("CityService.findAllCities start...");
		JobConf conf = new JobConf(CityService.class);
		conf.setOutputKeyClass(LongWritable.class);
		conf.setOutputValueClass(Text.class);
		conf.setInputFormat(DBInputFormat.class);
		
		logger.info("destPath == " + hdfsHost + destPath);
		Path path = new Path(hdfsHost + destPath);
		FileOutputFormat.setOutputPath(conf, path);
		logger.info("driver == " + driver);
		logger.info("username == " + username);
		logger.info("url == " + url);
		logger.info("password == " + password);
		DBConfiguration.configureDB(conf, driver, url, username, password);
		String[] fields = { "id", "code", "name" };
		DBInputFormat.setInput(conf, CityRecord.class, "city", null, "id", fields);
		conf.setMapperClass(CityMapper.class);
		conf.setReducerClass(IdentityReducer.class);
		JobClient.runJob(conf);
	}

}
