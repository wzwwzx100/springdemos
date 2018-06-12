package com.test.config;

import org.apache.hadoop.conf.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.test.util.HdfsUtil;

@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(HdfsProperties.class)
public class HdfsConfig {

	@Autowired
	private HdfsProperties hdfsProperties;

	@Bean
	public HdfsUtil hdfsUtil() {
		return new HdfsUtil(configuration());
	}

	@Bean
	public Configuration configuration() {
		Configuration conf = new Configuration();
		conf.set(hdfsProperties.getDefaultfs(), hdfsProperties.getHost());
		return conf;
	}
	
}
