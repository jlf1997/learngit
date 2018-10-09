package com.cimr.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/***
 * @author pxh
 * @date 2018/1/19 14:48
 * @TODO todo
 */
public class SmsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsUtil.class);

    public static String sendSms(String destUrl, String requestBody) throws IOException {
        StringBuffer requestBodyBuf = new StringBuffer(requestBody);
        StringBuffer responseBody = new StringBuffer();
        request("GET", destUrl, new HashMap(), requestBodyBuf, new HashMap(), responseBody);

        return responseBody.toString();
    }

    // 用于发送HTTP请求
    public static void request(String method, String destUrl,
                               Map requestHeaderMap, StringBuffer requestBody,
                               Map responseHeaderMap, StringBuffer responseBody)
            throws IOException {

        LOGGER.info("/**************** " + method + ": " + destUrl
                + " ******/");
        LOGGER.info(method + ": " + destUrl);
        // 建立连接
        URL url = new URL(destUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        // 添加请求头
        setRequestProperty(conn, requestHeaderMap);

        // 发送请求
        String reqStr = requestBody.toString();
        byte bufb[] = reqStr.getBytes();
        DataOutputStream buffout = new DataOutputStream(conn.getOutputStream());
        buffout.write(bufb);
        buffout.flush();
        LOGGER.info(reqStr);

        // 读取响应
        DataInputStream dis = new DataInputStream(conn.getInputStream());
        byte[] buffer = new byte[1024];
        int length = -1;
        String respBodyStr = "";
        while ((length = dis.read(buffer)) != -1) {
            respBodyStr += new String(buffer, 0, length);
        }
        getHeaderMap(conn, responseHeaderMap);

        // 返回响应体
        responseBody.append(respBodyStr);
    }

    @SuppressWarnings("rawtypes")
    private static void setRequestProperty(HttpURLConnection conn, Map headerMap) {
        for (Iterator it = headerMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            conn.setRequestProperty(key, value);
            LOGGER.info(key + ": " + value);
        }
    }

    @SuppressWarnings("unchecked")
    private static void getHeaderMap(HttpURLConnection conn, Map headerMap) {
        for (Iterator it = conn.getHeaderFields().entrySet().iterator(); it
                .hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            headerMap.put(key, value);
            LOGGER.info(key + ": " + value);
        }
    }
}
