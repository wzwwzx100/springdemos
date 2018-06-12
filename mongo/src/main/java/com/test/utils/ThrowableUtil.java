package com.test.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowableUtil {

	public static String getErrorInfoFromThrowable(Throwable e) {
		try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw);) {
			e.printStackTrace(pw);
			return sw.toString().replaceAll("\r\n", "\n");
		} catch (Exception e2) {
			return "bad getErrorInfoFromException";
		}
	}

}
