package com.cimr.token;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cimr.security.jwt.setting")
public class JwtSettings {
    /**
     * {@link JwtToken} will expire after this time. token 过期时间
     */
    private Integer tokenExpirationTime;

    /**
     * Token issuer. token 提供方
     */
    private String tokenIssuer;
    
    /**
     * Key is used to sign {@link JwtToken}.
     */
    private String tokenSigningKey;
    
    private String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMYXdq9xlE4bDNFR" + 
    		"7iYQSWzwyCkZ55NDEOqy+WYDSfKqMAQ2xuqaBujRb9DiQ/0npy05GDVS4PETLTbd" + 
    		"LDayW9bsNxZp2el4S47zdyECsdiM4EbXRtbNG8akk2D3CwvnwR0q3b4ccHszsnAG" + 
    		"1/9E5vHB6M/In8L/aiRTZWVogBZfAgMBAAECgYEAo+NTuVFh3sD4eNGtE2bUKMKJ" + 
    		"B6A7/JU3EMZkGmdMLe1p7oKXTg+C+eoU9Z/NmkvNP1gGVE+udSVlegfAhbxIx/F0" + 
    		"9upfhbUk/g5ulCKtJkJGMEkPz+Mf8jHeXYm4UbM6yNyO9u44CC0sm4R5rE5aQNPz" + 
    		"NxkamSHI83HQTKyExpECQQDxvapoVIfnvHFnaWD06gn/pdvYdG6VpMWMbmjFnk2H" + 
    		"QH8IjsEBNeb2w7UkQe6IyJoANbU5wwBB1XkjbDdOLK05AkEA0cavpx9oVQEmn9aS" + 
    		"st0QHufKXepfjZugdkD4ZoDuMpbMapz4VQdSTDd/etY8NaP1RFOvBE3oiwrTaQc2" + 
    		"7ZL4VwJBANHXeynvXr3ZRXOn9OMEmcNR/UGq6Cd8DYO2f7ozjUb/xIXwLTfYy3Gf" + 
    		"NwmPNfmEcLIH7RWD4SBEp1AJawSwIXkCQHHPfdUJ8SzNJwto+sYXsXtS/jPdyGGn" + 
    		"oXMG5L2YDaNctSLSbtiS1E+NfJhESv4/Kf4b+MQ5EIT/JpvA0kgqiJkCQAmXIpPC" + 
    		"EfafbwIWUOekXgpyKRnBZtMBPGWpEAk4FRm3Ia317MVDSnmxofDe1yHh050p+QuQ" + 
    		"8RQFnlS98DhXUOE=";
    
    private String publicKey= "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGF3avcZROGwzRUe4mEEls8Mgp" + 
    		"GeeTQxDqsvlmA0nyqjAENsbqmgbo0W/Q4kP9J6ctORg1UuDxEy023Sw2slvW7DcW" + 
    		"adnpeEuO83chArHYjOBG10bWzRvGpJNg9wsL58EdKt2+HHB7M7JwBtf/RObxwejP" + 
    		"yJ/C/2okU2VlaIAWXwIDAQAB";
    
    /**
     * {@link JwtToken} can be refreshed during this timeframe.
     */
    private Integer refreshTokenExpTime;
    
    public Integer getRefreshTokenExpTime() {
        return refreshTokenExpTime;
    }

    public void setRefreshTokenExpTime(Integer refreshTokenExpTime) {
        this.refreshTokenExpTime = refreshTokenExpTime;
    }

    public Integer getTokenExpirationTime() {
        return tokenExpirationTime;
    }
    
    public void setTokenExpirationTime(Integer tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }
    
    public String getTokenIssuer() {
        return tokenIssuer;
    }
    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }
    
    public String getTokenSigningKey() {
        return tokenSigningKey;
    }
    
    public void setTokenSigningKey(String tokenSigningKey) {
        this.tokenSigningKey = tokenSigningKey;
    }

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
    
    
    
}
