package com.cimr.boot.auth.shiro.cas;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.client.Clients;
import org.pac4j.core.matching.PathMatcher;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.cimr.boot.auth.properties.BootCasProperties;
@Configuration
public class IotCasConfig {
	
	
	
	private static final Logger log = LoggerFactory.getLogger(IotCasConfig.class);

	
	@Autowired
	private BootCasProperties bootCasProperties;

	
    /**
     * 单点登出的listener
     *
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public ServletListenerRegistrationBean<?> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        bean.setListener(new SingleSignOutHttpSessionListener());
        bean.setEnabled(true);
        return bean;
    }
    
    /**
     * 通过rest接口可以获取tgt，获取service ticket，甚至可以获取CasProfile
     *
     * @return
     */
    @Bean
    protected CasRestFormClient casRestFormClient(CasConfiguration casConfiguration) {
        CasRestFormClient casRestFormClient = new CasRestFormClient();
        casRestFormClient.setConfiguration(casConfiguration);
        casRestFormClient.setName("rest");
        return casRestFormClient;
    }
 
    /**
     * casClient
     *
     * @return
     */
    @Bean
    public CasClient casClient(CasConfiguration casConfiguration) {
        CasClient casClient = new CasClient();
        casClient.setConfiguration(casConfiguration);
        casClient.setCallbackUrl(bootCasProperties.getCallbackUrl());
        casClient.setName("cas");
        log.info("============cas===============");
        return casClient;
    }
 
    /**
     * token校验相关
     *
     * @return
     */
    @Bean
    protected Clients clients(CasClient casClient, CasRestFormClient casRestFormClient) {
        //可以设置默认client
        Clients clients = new Clients();
        //token校验器，可以用HeaderClient更安全
        ParameterClient parameterClient = new ParameterClient("token", jwtAuthenticator());
        parameterClient.setSupportGetRequest(true);
        parameterClient.setName("jwt");
        //支持的client全部设置进去
        clients.setClients(casClient);
        return clients;
    }
 
//    @Bean
//    protected Config casConfig(Clients clients) {
//        Config config = new Config();
//        config.setClients(clients);
//        return config;
//    }
    
    /**
     * JWT Token 生成器，对CommonProfile生成然后每次携带token访问
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Bean
    protected JwtGenerator jwtGenerator() {
        return new JwtGenerator(new SecretSignatureConfiguration(bootCasProperties.getSalt())
        		, new SecretEncryptionConfiguration(bootCasProperties.getSalt()));
    }
 
    @Bean
    protected JwtAuthenticator jwtAuthenticator() {
        JwtAuthenticator jwtAuthenticator = new JwtAuthenticator();
        jwtAuthenticator.addSignatureConfiguration(new SecretSignatureConfiguration(bootCasProperties.getSalt()));
        jwtAuthenticator.addEncryptionConfiguration(new SecretEncryptionConfiguration(bootCasProperties.getSalt()));
        return jwtAuthenticator;
    }
 
    /**
     * cas的基本设置，包括或url等等，rest调用协议等
     *
     * @return
     */
    @Bean
    public CasConfiguration casConfiguration() {
        CasConfiguration casConfiguration = new CasConfiguration(bootCasProperties.getCasLoginUrl());
        casConfiguration.setProtocol(CasProtocol.CAS20);
        casConfiguration.setPrefixUrl(bootCasProperties.getPrefixUrl());
        return casConfiguration;
    }
 
    /**
     * 不拦截的路径
     *
     * @return
     */
    @Bean
    public PathMatcher pathMatcher() {
        PathMatcher pathMatcher = new PathMatcher();
        pathMatcher.excludePath("/html/**");
        return pathMatcher;
    }
    
    /**
     * 单点登出filter
     *
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public FilterRegistrationBean singleSignOutFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setName("singleSignOutFilter");
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setCasServerUrlPrefix(bootCasProperties.getPrefixUrl());
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        bean.setFilter(singleSignOutFilter);
        bean.addUrlPatterns("/*");
        bean.setEnabled(true);
        return bean;
    }
}
