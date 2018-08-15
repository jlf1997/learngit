package com.cimr.token;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cimr.security.jwt.setting")
public class JwtSettings {
    /**
     * {@link JwtToken} will expire after this time.
     */
    private Integer tokenExpirationTime;

    /**
     * Token issuer.
     */
    private String tokenIssuer;
    
    
    /**
     * 使用 rsa公钥
     */
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
    

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
    
    
    
//    yunyij: 
//    	  security:
//    	    jwt:
//    	      member:
//    	        tokenExpirationTime: 10080 # Number of minutes
//    	        refreshTokenExpTime: 43200 # Minutes
//    	        tokenIssuer: http://yunyij.com/member
//    	        tokenSigningKey:  xm8Ew6Hy5RaFK4EEnCIDAQgUS 
}
