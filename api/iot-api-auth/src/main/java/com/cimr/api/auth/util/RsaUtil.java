package com.cimr.api.auth.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * rsa工具类
 * @author Administrator
 *
 */
public class RsaUtil {

	/**
	 * 通过字符串生成私钥
	 * @param privateKeyData
	 * @return
	 */
	public static PrivateKey getPrivateKey(String privateKeyData){
		PrivateKey privateKey = null;
		try {
			byte[] decodeKey = Base64.getDecoder().decode(privateKeyData); //将字符串Base64解码
			PKCS8EncodedKeySpec x509= new PKCS8EncodedKeySpec(decodeKey);//创建x509证书封装类
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");//指定RSA
			privateKey = keyFactory.generatePrivate(x509);//生成私钥
			} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			}
		return privateKey;
		}

		 

		/**
		* 通过字符串生成公钥
		*/
		public static PublicKey getPublicKey(String publicKeyData){
			PublicKey publicKey = null;
			try {
				byte[] decodeKey = Base64.getDecoder().decode(publicKeyData);
				X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodeKey);
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				publicKey = keyFactory.generatePublic(x509);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}
			return publicKey;
		}
}
