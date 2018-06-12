package com.test.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 参考：http://www.mamicode.com/info-detail-1719318.html
 * 
 * @ClassName: HdfsUtil.java
 * @Description: 上传文件到hdfs
 * @author 尹顺林
 * @version V1.0
 * @Date 2017年11月24日 下午3:52:53
 */
public class HdfsUtil {

	private static final Logger logger = LogManager.getLogger(HdfsUtil.class);

	private Configuration conf;

	public HdfsUtil(Configuration conf) {
		this.conf = conf;
	}

	public void uploadFile(String... args) throws IOException {

		GenericOptionsParser optionsParser = new GenericOptionsParser(conf, args);

		String[] remainingArgs = optionsParser.getRemainingArgs();
		if (remainingArgs.length < 2) {
			System.err.println("Usage: upload <source> <dest>");
			System.exit(2);
		}

		Path source = new Path(args[0]);
		Path dest = new Path(args[1]);

		logger.info("source::::: " + source.toUri().getPath());
		logger.info("dest:::: " + dest.toUri().getPath());
		FileSystem fs = FileSystem.get(conf);

		fs.copyFromLocalFile(true, false, source, dest);
	}

	public void downloadFile(HttpServletResponse res, String sourcePath) throws IOException {

		res.reset();
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/x-download");

		res.setHeader("Accept-Ranges", "bytes");
		
		String fileName = sourcePath.substring(sourcePath.lastIndexOf("/") + 1);
		
		logger.info("fileName: " + fileName);

		// set headers for the response
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", fileName);
		logger.info("headerValue: " + headerValue);
		res.setHeader(headerKey, headerValue);

		// Copy the stream to the response's output stream.
		try (FileSystem inFs = FileSystem.get(URI.create(sourcePath), conf);
		        FSDataInputStream is = inFs.open(new Path(sourcePath));
		        OutputStream os = res.getOutputStream();) {

			byte[] buffer = new byte[1024 * 10];
			int len = 0;
			long fileSize = 0;
			while ((len = is.read(buffer, 0, buffer.length)) != -1) {
				os.write(buffer, 0, len);
				fileSize += len;
			}
			res.setHeader("Content-Length", String.valueOf(fileSize));
			res.flushBuffer();
		} catch (IOException e) {
			logger.error(ThrowableUtil.getErrorInfoFromThrowable(e));
		}
	}

}
