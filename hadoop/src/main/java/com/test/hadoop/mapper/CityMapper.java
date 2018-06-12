package com.test.hadoop.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.test.hadoop.db.CityRecord;

public class CityMapper extends MapReduceBase implements Mapper<LongWritable, CityRecord, LongWritable, Text> {

	private static final Logger logger = LogManager.getLogger(CityMapper.class);
	
	@Override
	public void map(LongWritable key, CityRecord value, OutputCollector<LongWritable, Text> collector, Reporter reporter)
	        throws IOException {
		String val = value.toString();
		logger.info("record::: " + val);
		collector.collect(new LongWritable(value.getId()), new Text(val));
	}

}
