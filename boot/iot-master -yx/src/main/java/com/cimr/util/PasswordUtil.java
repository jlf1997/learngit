package com.cimr.util;

import com.xiaoleilu.hutool.crypto.digest.DigestUtil;

public class PasswordUtil {
	public static String encrypt(String username, String password) {
		password = DigestUtil.md5Hex(username.toLowerCase() + "#" + password);
		return password;
	}
}
