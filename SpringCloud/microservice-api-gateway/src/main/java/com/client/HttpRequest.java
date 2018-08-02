package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequest extends Thread {
	public   String num = "";
	public HttpRequest(String num)
	{
		this.num = num;
	}
	/*public static void main(String[] args) {
		
		 * //发送 GET 请求 String
		 * s=HttpRequest.sendGet("http://localhost:8080/Home/RequestString",
		 * "key=123&v=456"); System.out.println(s);
		 

		// 发送 POST 请求
		for ( int i = 0; i <= 20; i++) {
			HttpRequest s = new HttpRequest(""+i);
			try {
				Thread.sleep(500);
				System.out.println("第"+i+"个连接");
			} catch (Exception e) {
				// TODO: handle exception
			}
			s.start();
		}

	}*/

	@Override
	public void run() {
		String content = "id:"+num+";";//{\"template_id\":\"_G0CaEggZLQpImoMzmCk4zV09R_ivpY_imUxZabrqfU\",\"touser\":\"oZitPs1oCKzeRdi0jTH7JDlpBh00\",\"url\":\"http://xms.51tys.com/new?WX_MSG_ID=83df50b2-1e7d-45e8-92ad-7a4a173fbf05&sign=gVzdRPmAnFcOS0BoTbC1ZbDL4ZgxEdDCK5GGFL2WwsbRdXfWAbSn/Z6bGEptTYSrU3fFv lS8jGiKsaD6smew4e7qUb xlyPW28RkA1Cig1kg/l3g2pZkHwjJ25h9gJP3euSlCXVzhA1k0uIYdx/Os1ATLIQXQJNfFxd4vTw0g=\",\"topcolor\":\"#000000\",\"data\":{\"keyword1\":{\"color\":\"#000000\",\"value\":\"201711290127\"},\"keyword2\":{\"color\":\"#000000\",\"value\":\"提醒\"},\"remark\":{\"color\":\"#000000\",\"value\":\"生产计划201711290127需要配比审核，请登录智能物料系统进行配比审核!\"},\"first\":{\"color\":\"#ff0000\",\"value\":\"配比审核提醒信息\"}}}";
		//String sr = HttpRequest.sendGet("http://175.6.65.239:8011/user/findUserList","stype=1");
		String sr = HttpRequest.sendGet("http://127.0.0.1:8050/user/user/findUserList","accessToken=123&stype=1");
		/*String sr1 = HttpRequest.sendPost("http://127.0.0.1:10061/Erp",
		"username=fengyun&type=st&token=3344");*/
		System.out.println("第"+num+"个连接，返回："+sr);
		//System.out.println(sr1);
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection
					.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}