package com.test.hadoop.reducer;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DedupReduce extends Reducer<Text, Text, Text, Text> {
	
	private static final Logger logger = LogManager.getLogger(DedupReduce.class);
	
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException,
	        InterruptedException {
		logger.info("reduce key = " + key.toString());
		context.write(key, new Text(""));
	}
}
