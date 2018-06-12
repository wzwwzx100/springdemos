package com.test.web;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.hadoop.mapper.DedupMap;
import com.test.hadoop.reducer.DedupReduce;
import com.test.service.CityService;
import com.test.service.StudentService;

@RestController
public class HadoopController {

	private static final Logger logger = LogManager.getLogger(UploadDownloadController.class);

	@Autowired
	private Configuration configuration;

	@Autowired
	private StudentService studentService;

	@Autowired
	private CityService cityService;

	/**
	 * 
	 * @Title: dedup
	 * @Description: 数据去重
	 * @param: @param dst 输入路径
	 * @param: @param dstOut 输出路径，必须是不存在的，空文件夹也不行。
	 * @param: @throws IOException
	 * @param: @throws ClassNotFoundException
	 * @param: @throws InterruptedException
	 * @return: void
	 * @throws
	 * @author zekym
	 * @Date 2017年12月5日 上午9:56:30
	 */
	@RequestMapping(value = "/dedup", method = { RequestMethod.POST })
	public void dedup(String dst, String dstOut) throws IOException, ClassNotFoundException, InterruptedException {

		logger.info("job name ======= dedupJob");
		Job job = Job.getInstance(configuration, "dedupJob");
		job.setJarByClass(HadoopController.class);
		// 设置Map,Combine和Reduce处理类
		job.setMapperClass(DedupMap.class);
		job.setCombinerClass(DedupReduce.class);
		job.setReducerClass(DedupReduce.class);

		// 设置输出类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		logger.info("dst:: " + dst);
		logger.info("dstOut:: " + dstOut);
		// 设置输出目录
		FileInputFormat.addInputPath(job, new Path(dst));
		FileOutputFormat.setOutputPath(job, new Path(dstOut));

		boolean result = job.waitForCompletion(true);
		logger.info("result:: " + result);
	}

	@RequestMapping(value = "/addStudent", method = { RequestMethod.POST })
	public void addStudent(String sourcePath) throws Exception {
		studentService.addStudent(sourcePath);
	}

	@RequestMapping(value = "/findAllCities", method = { RequestMethod.GET })
	public void findAllCities(String destPath) throws IOException {
		cityService.findAllCities(destPath);
	}

}
