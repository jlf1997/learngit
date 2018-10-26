package com.cimr.master.util;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class StringUtil extends StringUtils{

	public StringUtil() {
	}
	
	
	private static final Logger log = LoggerFactory.getLogger(StringUtil.class);


	public static boolean isBlank(Object... objects) {
		Boolean result = Boolean.valueOf(false);
		for (Object object : objects) {
			if ((null == object) || ("".equals(object.toString().trim())) || ("null".equals(object.toString().trim()))) {
				result = Boolean.valueOf(true);
				break;
			}
		}
		return result.booleanValue();
	}

	public static String getRandom(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";

			if ("char".equalsIgnoreCase(charOrNum)) {
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val = val + (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val = val + String.valueOf(random.nextInt(10));
			}
		}
		return val.toLowerCase();
	}

	public static boolean isNotBlank(Object... objects) {
		return !isBlank(objects);
	}

	public static boolean isBlank(String... objects) {
		Object[] object = objects;
		return isBlank(object);
	}

	public static boolean isNotBlank(String... objects) {
		Object[] object = objects;
		return !isBlank(object);
	}

	public static boolean isBlank(String str) {
		Object object = str;
		return isBlank(new Object[] { object });
	}

	public static boolean isNotBlank(String str) {
		Object object = str;
		return !isBlank(new Object[] { object });
	}

	public static int indexOf(String baseStr, String[] strings) {
		if ((null == baseStr) || (baseStr.length() == 0) || (null == strings)) {
			return 0;
		}
		int i = 0;
		for (String string : strings) {
			boolean result = baseStr.equals(string);
			i++;
			i = result ? i : i;
		}
		return i;
	}

	public static JSONObject isJSONObject(String args) {
		JSONObject result = null;
		if (isBlank(args)) {
			return result;
		}
		try {
			return JSONObject.fromObject(args.trim());
		} catch (Exception e) {
		}
		return result;
	}

	public static JSONArray isJSONArray(Object args) {
		JSONArray result = new JSONArray();
		if (isBlank(new Object[] { args })) {
			return null;
		}
		if ((args instanceof JSONArray)) {
			JSONArray arr = (JSONArray) args;
			for (Object json : arr) {
				if ((json != null) && ((json instanceof JSONObject))) {
					result.add(json);
				} else {
					result.add(JSONObject.fromObject(json));
				}
			}
			return result;
		}
		return null;
	}

	public static String trimToEmpty(Object str) {
		return isBlank(str) ? "" : str.toString().trim();
	}

	public static String getBASE64(String str, boolean... bf) {
		if (isBlank(str))
			return null;
		String base64 = new BASE64Encoder().encode(str.getBytes());

		if (isBlank(new Object[] { bf })) {
			base64 = base64.replaceAll("=", "");
		}
		return base64;
	}

	public static String getStrByBASE64(String s) {
		if (isBlank(s))
			return "";
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
		}
		return "";
	}

	public static String mapToGet(Map<? extends Object, ? extends Object> map) {
		String result = "";
		if ((map == null) || (map.size() == 0)) {
			return result;
		}
		Set<? extends Object> keys = map.keySet();
		for (Object key : keys) {
			result = result + (String) key + "=" + (String) map.get(key) + "&";
		}

		return isBlank(result) ? result : result.substring(0, result.length() - 1);
	}

	public static Map<String, ? extends Object> getToMap(String args) {
		if (isBlank(args)) {
			return null;
		}
		args = args.trim();

		if (args.startsWith("?")) {
			args = args.substring(1, args.length());
		}
		String[] argsArray = args.split("&");

		Map<String, Object> result = new java.util.HashMap();
		for (String ag : argsArray) {
			if ((!isBlank(ag)) && (ag.indexOf("=") > 0)) {
				String[] keyValue = ag.split("=");

				String key = keyValue[0];
				String value = "";
				for (int i = 1; i < keyValue.length; i++) {
					value = value + keyValue[i] + "=";
				}
				value = value.length() > 0 ? value.substring(0, value.length() - 1) : value;
				result.put(key, value);
			}
		}

		return result;
	}

	public static String toUnicode(String str) {
		String[] as = new String[str.length()];
		String s1 = "";
		for (int i = 0; i < str.length(); i++) {
			int v = str.charAt(i);
			if ((v >= 19968) && (v <= 171941)) {
				as[i] = Integer.toHexString(str.charAt(i) & 0xFFFF);
				s1 = s1 + "\\u" + as[i];
			} else {
				s1 = s1 + str.charAt(i);
			}
		}
		return s1;
	}

	public static String merge(Object... v) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < v.length; i++) {
			sb.append(v[i]);
		}
		return sb.toString();
	}

	public static String strToUrlcode(String value) {
		try {
			return java.net.URLEncoder.encode(value, "utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error( "字符串转换为URLCode失败,value:" + value, new Object[0]);

			e.printStackTrace();
		}
		return null;
	}

	public static String urlcodeToStr(String value) {
		try {
			return URLDecoder.decode(value, "utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error("URLCode转换为字符串失败;value:" + value, new Object[0]);

			e.printStackTrace();
		}
		return null;
	}

	public static Boolean containsCN(String txt) {
		if (isBlank(txt)) {
			return Boolean.valueOf(false);
		}
		for (int i = 0; i < txt.length(); i++) {
			String bb = txt.substring(i, i + 1);

			boolean cc = Pattern.matches("[一-龥]", bb);
			if (cc)
				return Boolean.valueOf(cc);
		}
		return Boolean.valueOf(false);
	}

	public static String removeHtml(String news) {
		String s = news.replaceAll("amp;", "").replaceAll("<", "<").replaceAll(">", ">");

		Pattern pattern = Pattern.compile("<(span)?\\sstyle.*?style>|(span)?\\sstyle=.*?>", 32);
		Matcher matcher = pattern.matcher(s);
		String str = matcher.replaceAll("");

		Pattern pattern2 = Pattern.compile("(<[^>]+>)", 32);
		Matcher matcher2 = pattern2.matcher(str);
		String strhttp = matcher2.replaceAll(" ");

		String regEx = "(((http|https|ftp)(\\s)*((\\:)|：))(\\s)*(//|//)(\\s)*)?([\\sa-zA-Z0-9(\\.|．)(\\s)*\\-]+((\\:)|(:)[\\sa-zA-Z0-9(\\.|．)&%\\$\\-]+)*@(\\s)*)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*)*[\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*[\\sa-zA-Z]*)((\\s)*(\\:)|(：)(\\s)*[0-9]+)?(/(\\s)*[^/][\\sa-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*";

		Pattern p1 = Pattern.compile(regEx, 32);
		Matcher matchhttp = p1.matcher(strhttp);
		String strnew = matchhttp.replaceAll("").replaceAll("(if[\\s]*\\(|else|elseif[\\s]*\\().*?;", " ");

		Pattern patterncomma = Pattern.compile("(&[^;]+;)", 32);
		Matcher matchercomma = patterncomma.matcher(strnew);
		String strout = matchercomma.replaceAll(" ");
		String answer = strout.replaceAll("[\\pP‘’“”]", " ").replaceAll("\r", " ").replaceAll("\n", " ").replaceAll("\\s", " ").replaceAll("　", "");

		return answer;
	}

	public static List<String> array2Empty(String[] array) {
		List<String> list = new ArrayList();
		for (String string : array) {
			if (isNotBlank(string)) {
				list.add(string);
			}
		}
		return list;
	}

	public static Set<?> array2Set(Object[] array) {
		Set<Object> set = new java.util.TreeSet();
		for (Object id : array) {
			if (null != id) {
				set.add(id);
			}
		}
		return set;
	}

	public static String toString(Serializable serializable) {
		if (null == serializable) {
			return null;
		}
		try {
			return (String) serializable;
		} catch (Exception e) {
		}
		return serializable.toString();
	}

	
	/**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     * @param value
     * 指定的字符串
     * @return 字符串的长度
     */
    public static int getByteLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }
	
	
	/**
	 * 按长度分割字符串
	 * 
	 * @param content
	 * @param len
	 * @return
	 */
	public static String[] split(String content, int len) {
		if (content == null || content.equals("")) {
			return null;
		}
		int len2 = content.length();
		if (len2 <= len) {
			return new String[] { content };
		} else {
			int i = len2 / len + 1;
			System.out.println("i:" + i);
			String[] strA = new String[i];
			int j = 0;
			int begin = 0;
			int end = 0;
			while (j < i) {
				begin = j * len;
				end = (j + 1) * len;
				if (end > len2)
					end = len2;
				strA[j] = content.substring(begin, end);
				// System.out.println(strA[j]+"<br/>");
				j = j + 1;
			}
			return strA;
		}
	}
	
	public static boolean emailFormat(String email)
    {
        boolean tag = true;
        final String pattern1 = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }


    /**
     * 验证是不是EMAIL
     * @param email
     * @return
     */
	public static boolean isEmail(String email) {
		boolean retval = false;
		String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";   
	    Pattern regex = Pattern.compile(check);   
	    Matcher matcher = regex.matcher(email);   
	    retval = matcher.matches();  
		return retval;
	}
	
	// 验证汉字为true
	public static boolean isLetterorDigit(String s) {
		if (s.equals("") || s == null) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isLetterOrDigit(s.charAt(i))) {
				// if (!Character.isLetter(s.charAt(i))){
				return false;
			}
		}
		// Character.isJavaLetter()
		return true;
	}
	/**
	 * 判断某字符串是否为null，如果长度大于256，则返回256长度的子字符串，反之返回原串
	 * @param str
	 * @return
	 */
	public static String checkStr(String str){
		if(str==null){
			return "";
		}else if(str.length()>256){
			return str.substring(256);
		}else{
			return str;
		}
	}

	/**
	 * 验证是不是Int
	 * validate a int string   
	 * @param str
	 * the Integer string.
	 * @return true if the str is invalid otherwise false.
	 */
	public static boolean validateInt(String str) {
		if (str == null || str.trim().equals(""))
			return false;

		char c;
		for (int i = 0, l = str.length(); i < l; i++) {
			c = str.charAt(i);
			if (!((c >= '0') && (c <= '9')))
				return false;
		}

		return true;
	}
	/**
	 * 字节码转换成16进制字符串 内部调试使用
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	/**
	 * 字节码转换成自定义字符串 内部调试使用
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2string(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			// if (n<b.length-1) hs=hs+":";
		}
		return hs.toUpperCase();
	}

	public static byte[] string2byte(String hs) {
		byte[] b = new byte[hs.length() / 2];
		for (int i = 0; i < hs.length(); i = i + 2) {
			String sub = hs.substring(i, i + 2);
			byte bb = new Integer(Integer.parseInt(sub, 16)).byteValue();
			b[i / 2] = bb;
		}
		return b;
	}

	/**
	 * 验证字符串是否为空
	 * @param param
	 * @return
	 */
	public static boolean empty(String param) {
		return param == null || param.trim().length() < 1;
	}
	
	/**
	 * 验证字符串是否不为空
	 * @param param
	 * @return
	 */
	public static boolean valid(String param) {
		return param != null && param.trim().length() > 0;
	}

	// 验证英文字母或数据
	public static boolean isLetterOrDigit(String str) {
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		boolean value = true;
		try {
			p = Pattern.compile("[^0-9A-Za-z]");
			m = p.matcher(str);
			if (m.find()) {

				value = false;
			}
		} catch (Exception e) {
		}
		return value;
	}

	/**
     * 验证是否是小写字符	串
     */
	@SuppressWarnings("unused")
	private static boolean isLowerLetter(String str) {
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		boolean value = true;
		try {
			p = Pattern.compile("[^a-z]");
			m = p.matcher(str);
			if (m.find()) {
				value = false;
			}
		} catch (Exception e) {
		}
		return value;
	}

	public static String encode(String str, String code) {
		try {
			return URLEncoder.encode(str, code);
		} catch (Exception ex) {
			ex.fillInStackTrace();
			return "";
		}
	}

	public static String decode(String str, String code) {
		try {
			return URLDecoder.decode(str, code);
		} catch (Exception ex) {
			ex.fillInStackTrace();
			return "";
		}
	}

	public static String nvl(String param) {
		return param != null ? param.trim() : "";
	}

	public static int parseInt(String param, int d) {
		int i = d;
		try {
			i = Integer.parseInt(param);
		} catch (Exception e) {
		}
		return i;
	}

	public static int parseInt(String param) {
		return parseInt(param, 0);
	}

	public static long parseLong(String param) {
		long l = 0L;
		try {
			l = Long.parseLong(param);
		} catch (Exception e) {
		}
		return l;
	}

	public static double parseDouble(String param) {
		return parseDouble(param, 1.0);
	}

	public static double parseDouble(String param, double t) {
		double tmp = 0.0;
		try {
			tmp = Double.parseDouble(param.trim());
		} catch (Exception e) {
			tmp = t;
		}
		return tmp;
	}

	public static boolean parseBoolean(String param) {
		if (empty(param))
			return false;
		switch (param.charAt(0)) {
		case 49: // '1'
		case 84: // 'T'
		case 89: // 'Y'
		case 116: // 't'
		case 121: // 'y'
			return true;

		}
		return false;
	}

	/**
	 * public static String replace(String mainString, String oldString, String
	 * newString) { if(mainString == null) return null; int i =
	 * mainString.lastIndexOf(oldString); if(i < 0) return mainString;
	 * StringBuffer mainSb = new StringBuffer(mainString); for(; i >= 0; i =
	 * mainString.lastIndexOf(oldString, i - 1)) mainSb.replace(i, i +
	 * oldString.length(), newString);
	 * 
	 * return mainSb.toString(); }
	 * 
	 */

	public static final String[] split(String str, String delims) {
		StringTokenizer st = new StringTokenizer(str, delims);
		ArrayList list = new ArrayList();
		for (; st.hasMoreTokens(); list.add(st.nextToken()))
			;
		return (String[]) list.toArray(new String[list.size()]);
	}

	
	

	public static String substring(String str, int length) {
		if (str == null)
			return null;

		if (str.length() > length)
			return (str.substring(0, length - 2) + "...");
		else
			return str;
	}

	public static boolean validateDouble(String str) throws RuntimeException {
		if (str == null)
			// throw new RuntimeException("null input");
			return false;
		char c;
		int k = 0;
		for (int i = 0, l = str.length(); i < l; i++) {
			c = str.charAt(i);
			if (!((c >= '0') && (c <= '9')))
				if (!(i == 0 && (c == '-' || c == '+')))
					if (!(c == '.' && i < l - 1 && k < 1))
						// throw new RuntimeException("invalid number");
						return false;
					else
						k++;
		}
		return true;
	}

	public static boolean validateMobile(String str, boolean includeUnicom) {
		if (str == null || str.trim().equals(""))
			return false;
		str = str.trim();

		if (str.length() != 11 || !validateInt(str))
			return false;

		if (includeUnicom
				&& (str.startsWith("130") || str.startsWith("133") || str.startsWith("131")))
			return true;

		if (!(str.startsWith("139") || str.startsWith("138") || str.startsWith("137")
				|| str.startsWith("136") || str.startsWith("135")))
			return false;
		return true;
	}

	public static boolean validateMobile(String str) {
		return validateMobile(str, false);
	}

	/**
	 * delete file
	 * 
	 * @param fileName
	 * @return -1 exception,1 success,0 false,2 there is no one directory of the
	 *         same name in system
	 */
	public static int deleteFile(String fileName) {
		File file = null;
		int returnValue = -1;
		try {
			file = new File(fileName);
			if (file.exists())
				if (file.delete())
					returnValue = 1;
				else
					returnValue = 0;
			else
				returnValue = 2;

		} catch (Exception e) {
			System.out.println("Exception:e=" + e.getMessage());
		} finally {
			file = null;
			// return returnValue;
		}
		return returnValue;
	}

	public static String gbToIso(String s) throws UnsupportedEncodingException {
		return covertCode(s, "GB2312", "ISO8859-1");
	}

	public static String covertCode(String s, String code1, String code2)
			throws UnsupportedEncodingException {
		if (s == null)
			return null;
		else if (s.trim().equals(""))
			return "";
		else
			return new String(s.getBytes(code1), code2);
	}

	public static String transCode(String s0) throws UnsupportedEncodingException {
		if (s0 == null || s0.trim().equals(""))
			return null;
		else {
			s0 = s0.trim();
			return new String(s0.getBytes("GBK"), "ISO8859-1");
		}
	}

	public static String GBToUTF8(String s) {
		try {
			StringBuffer out = new StringBuffer("");
			byte[] bytes = s.getBytes("unicode");
			for (int i = 2; i < bytes.length - 1; i += 2) {
				out.append("\\u");
				String str = Integer.toHexString(bytes[i + 1] & 0xff);
				for (int j = str.length(); j < 2; j++) {
					out.append("0");
				}
				out.append(str);
				String str1 = Integer.toHexString(bytes[i] & 0xff);
				for (int j = str1.length(); j < 2; j++) {
					out.append("0");
				}

				out.append(str1);
			}
			return out.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static final String[] replaceAll(String[] obj, String oldString, String newString) {
		if (obj == null) {
			return null;
		}
		int length = obj.length;
		String[] returnStr = new String[length];
		String str = null;
		for (int i = 0; i < length; i++) {
			returnStr[i] = replaceAll(obj[i], oldString, newString);
		}
		return returnStr;
	}

	public static String replaceAll(String s0, String oldstr, String newstr) {
		if (s0 == null || s0.trim().equals(""))
			return null;
		StringBuffer sb = new StringBuffer(s0);
		int begin = 0;
		// int from = 0;
		begin = s0.indexOf(oldstr);
		while (begin > -1) {
			sb = sb.replace(begin, begin + oldstr.length(), newstr);
			s0 = sb.toString();
			begin = s0.indexOf(oldstr, begin + newstr.length());
		}
		return s0;
	}

	public static String toHtml(String str) {
		String html = str;
		if (str == null || str.length() == 0) {
			return str;
		}
		html = replaceAll(html, "&", "&amp;");
		html = replaceAll(html, "<", "&lt;");
		html = replaceAll(html, ">", "&gt;");
		html = replaceAll(html, "\r\n", "\n");
		html = replaceAll(html, "\n", "<br>\n");
		html = replaceAll(html, "\t", "         ");
		html = replaceAll(html, " ", "&nbsp;");
		return html;
	}

	public static final String replace(String line, String oldString, String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	public static final String replaceIgnoreCase(String line, String oldString, String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	public static final String escapeHTMLTags(String input) {
		// Check if the string is null or zero length -- if so, return
		// what was sent in.
		if (input == null || input.length() == 0) {
			return input;
		}
		// Use a StringBuffer in lieu of String concatenation -- it is
		// much more efficient this way.
		StringBuffer buf = new StringBuffer(input.length());
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * Returns a random String of numbers and letters of the specified length.
	 * The method uses the Random class that is built-in to Java which is
	 * suitable for low to medium grade security uses. This means that the
	 * output is only pseudo random, i.e., each number is mathematically
	 * generated so is not truly random.
	 * <p>
	 * 
	 * For every character in the returned String, there is an equal chance that
	 * it will be a letter or number. If a letter, there is an equal chance that
	 * it will be lower or upper case.
	 * <p>
	 * 
	 * The specified length must be at least one. If not, the method will return
	 * null.
	 * 
	 * @param length
	 *            the desired length of the random String to return.
	 * @return a random String of numbers and letters of the specified length.
	 */

	private static Random randGen = null;
	private static Object initLock = new Object();
	private static char[] numbersAndLetters = null;

	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		// Init of pseudo random number generator.
		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					// Also initialize the numbersAndLetters array
					numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
							+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
				}
			}
		}
		// Create a char buffer to put random letters and numbers in.
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	public static final String randomNum(int length) {
		if (length < 1) {
			return null;
		}
		// Init of pseudo random number generator.
		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					// Also initialize the numbersAndLetters array
					numbersAndLetters = ("0123456789").toCharArray();
				}
			}
		}
		// Create a char buffer to put random letters and numbers in.
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
		}
		return new String(randBuffer);
	}
	
	public static String getSubstring(String content, int i) {
		int varsize = 10;
		String strContent = content;
		if (strContent.length() < varsize + 1) {
			return strContent;
		} else {
			int max = (int) Math.ceil((double) strContent.length() / varsize);
			if (i < max - 1) {
				return strContent.substring(i * varsize, (i + 1) * varsize);
			} else {
				return strContent.substring(i * varsize);
			}
		}
	}

	public static String now(String pattern) {
		return dateToString(new Date(), pattern);
	}

	public static String dateToString(Date date, String pattern) {
		if (date == null) {
			return "";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
	}

	public static synchronized String getNow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date());
	}

	public static java.sql.Date stringToDate(String strDate, String pattern) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date date = simpleDateFormat.parse(strDate);
		long lngTime = date.getTime();
		return new java.sql.Date(lngTime);
	}

	public static Date stringToUtilDate(String strDate, String pattern)
			throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.parse(strDate);
	}

	public static String root(HttpServletRequest request) {
		return request.getContextPath();
	}

	public static String formatHTMLOutput(String s) {
		if (s == null)
			return null;

		if (s.trim().equals(""))
			return "";

		String formatStr;
		formatStr = replaceAll(s, " ", "&nbsp;");
		formatStr = replaceAll(formatStr, "\n", "<br />");

		return formatStr;
	}

	/*
	 * 4舍5入 @param num 要调整的数 @param x 要保留的小数位
	 */
	public static double myround(double num, int x) {
		BigDecimal b = new BigDecimal(num);
		return b.setScale(x, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/*
	 * public static String getSubstring(String content,int i){ int varsize=10;
	 * String strContent=content; if(strContent.length()<varsize+1){ return
	 * strContent; }else{ int
	 * max=(int)Math.ceil((double)strContent.length()/varsize); if(i<max-1){
	 * return strContent.substring(i*varsize,(i+1)*varsize); }else{ return
	 * strContent.substring(i*varsize); } } }
	 */


    
	/**
	 * liuqs
	 * 
	 * @param param
	 * @param d
	 * @return
	 */
	public static int parseLongInt(Long param, int d) {
		int i = d;
		try {
			i = Integer.parseInt(String.valueOf(param));
		} catch (Exception e) {
		}
		return i;
	}

	public static int parseLongInt(Long param) {
		return parseLongInt(param, 0);
	}

	
	public static String returnString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}
	
	
	/**
     * 修改敏感字符编码
     * @param value
     * @return
     */
	public static String htmlEncode(String value){
	    String re[][] = {{"<","&lt;"},
	                     {">","&gt;"},
	                     {"\"","&quot;"},
	                     {"\\′","&acute;"},
	                     {"&","&amp;"}
	                    };
	   
	    for(int i=0; i<4; i++){
	        value = value.replaceAll(re[i][0], re[i][1]);
	    }
	    return value;
	}
	/**
     * 防SQL注入
     * 
     * @param str
     * @return
     */
	public static boolean sql_inj(String str) {
		 String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
		 String inj_stra[] = inj_str.split("|");
		 for (int i=0 ; i < inj_stra.length ; i++ )
		 {
			 if (str.indexOf(inj_stra[i])>=0)
			 {
			 	return true;
			 }
		 }
		 return false;
	 }
	
	
	//地球半径,单位公里
	 private final static double EARTH_RADIUS = 6378.137 * 1000;
	      private static double rad(double d)
	      {
	         return d * Math.PI / 180.0;
	      } 
	         //计算经纬度距离
	      public static double getDistance(double lat1, double lng1, double lat2, double lng2)
	      {
	    	 
	         double radLat1 = rad(lat1);
	         double radLat2 = rad(lat2);
	         double a = radLat1 - radLat2;
	         double b = rad(lng1) - rad(lng2);
	 
	         double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
	          Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	         s = s * EARTH_RADIUS;
	         s = Math.round(s * 10000) / 10000;
	         return s;
	         
	    	  /*
	    	  double a, b, R;  
	    	  R = 6378137; // 地球半径  
	    	  lat1 = lat1 * Math.PI / 180.0;  
	    	  lat2 = lat2 * Math.PI / 180.0;  
	    	    a = lat1 - lat2;  
	    	    b = (lng1 - lng2) * Math.PI / 180.0;  
	    	  double d;  
	    	  double sa2, sb2;  
	    	    sa2 = Math.sin(a / 2.0);  
	    	    sb2 = Math.sin(b / 2.0);  
	    	    d = 2  
	    	            * R  
	    	            * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)  
	    	                    * Math.cos(lat2) * sb2 * sb2));  
	    	    return d;  
	    	    */
	      }
	      
	      public static final char UNDERLINE='_';
	      
	      public static String camelToUnderline(String param){  
	          if (param==null||"".equals(param.trim())){  
	              return "";  
	          }  
	          int len=param.length();  
	          StringBuilder sb=new StringBuilder(len);  
	          for (int i = 0; i < len; i++) {  
	              char c=param.charAt(i);  
	              if (Character.isUpperCase(c)){  
	                  sb.append(UNDERLINE);  
	                  sb.append(Character.toLowerCase(c));  
	              }else{  
	                  sb.append(c);  
	              }  
	          }  
	          return sb.toString();  
	      }
	      
	      public static String underlineToCamel(String param){  
	          if (param==null||"".equals(param.trim())){  
	              return "";  
	          }  
	          int len=param.length();  
	          StringBuilder sb=new StringBuilder(len);  
	          for (int i = 0; i < len; i++) {  
	              char c=param.charAt(i);  
	              if (c==UNDERLINE){  
	                 if (++i<len){  
	                     sb.append(Character.toUpperCase(param.charAt(i)));  
	                 }  
	              }else{  
	                  sb.append(c);  
	              }  
	          }  
	          return sb.toString();  
	      }
	      
	      
	      public static String underlineToCamel2(String param){  
	          if (param==null||"".equals(param.trim())){  
	              return "";  
	          }  
	          StringBuilder sb=new StringBuilder(param);  
	          Matcher mc= Pattern.compile("_").matcher(param);  
	          int i=0;  
	          while (mc.find()){  
	              int position=mc.end()-(i++);  
	              //String.valueOf(Character.toUpperCase(sb.charAt(position)));  
	              sb.replace(position-1,position+1,sb.substring(position,position+1).toUpperCase());  
	          }  
	          return sb.toString();  
	      }  
	
}
