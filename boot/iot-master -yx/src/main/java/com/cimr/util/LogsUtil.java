package com.cimr.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class LogsUtil {

	/**
	 * 获取异常链详细信息
	 * @param aThrowable
	 * @return
	 */
	public static String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }

}
