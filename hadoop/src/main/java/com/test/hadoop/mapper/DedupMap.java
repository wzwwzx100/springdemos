package com.test.hadoop.mapper;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DedupMap extends Mapper<Object, Text, Text, Text> {
	
	private static final Logger logger = LogManager.getLogger(DedupMap.class);
	
	private Text line;// 每行数据

	// 实现map函数

	@Override
	protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		line = value;
		logger.info("map key = " + key.toString());
		logger.info("map value = " + value.toString());
		context.write(line, new Text(""));
	}

}
