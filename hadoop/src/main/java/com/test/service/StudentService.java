package com.test.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.test.hadoop.mapper.StudentMapper;
import com.test.hadoop.reducer.StudentReducer;

@Service
public class StudentService extends Configured {

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

	public int addStudent(String sourcePath) throws Exception {
		DBConfiguration.configureDB(configuration, driver, url, username, password);

		// 新建一个任务
		Job job = Job.getInstance(configuration, "StudentService");
		// 设置主类
		job.setJarByClass(StudentService.class);

		// 输入路径
		FileInputFormat.addInputPath(job, new Path(sourcePath));

		// Mapper
		job.setMapperClass(StudentMapper.class);
		// Reducer
		job.setReducerClass(StudentReducer.class);

		// mapper输出格式
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(Text.class);

		// 输入格式，默认就是TextInputFormat
		// job.setInputFormatClass(TextInputFormat.class);
		// 输出格式
		job.setOutputFormatClass(DBOutputFormat.class);

		// 输出到哪些表、字段
		DBOutputFormat.setOutput(job, "student", "name", "age");

		// 添加mysql数据库jar
		// job.addArchiveToClassPath(new
		// Path("hdfs://ljc:9000/lib/mysql/mysql-connector-java-5.1.31.jar"));
		// DistributedCache.addFileToClassPath(new
		// Path("hdfs://ljc:9000/lib/mysql/mysql-connector-java-5.1.31.jar"),
		// conf);
		// 提交任务
		return job.waitForCompletion(true) ? 0 : 1;
	}
}
