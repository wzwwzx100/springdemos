package com.test.hadoop.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class StudentMapper extends Mapper<LongWritable, Text, LongWritable, Text>{
    @Override
    protected void map(LongWritable key, Text value,Context context) throws IOException, InterruptedException {
        context.write(key, value);
    }
}
