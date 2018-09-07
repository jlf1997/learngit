package com.cimr.api.code.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
@ConditionalOnExpression("${iot.code.sender-type}==1")
public class RabbitConfig {
	@Autowired
    private MyRabbitProperties rabbitConstants;
	
	
	@Autowired
	private CodeProperties codeProperties;

    @Bean
    public ConnectionFactory connectionFactory() {
    	if(codeProperties.getSenderType()==0) {
    		return null;
    	}
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitConstants.getHost(),rabbitConstants.getPort());
        connectionFactory.setUsername(rabbitConstants.getUsername());
        connectionFactory.setVirtualHost(rabbitConstants.getVirtualHost());
        connectionFactory.setPassword(rabbitConstants.getPassword());
//        * 如果要进行消息回调，则这里必须要设置为true
        connectionFactory.setPublisherConfirms(rabbitConstants.getPublisherConfirms());
        return connectionFactory;
    }

    /**
     * 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }
    
//    @Bean
//    public TopicExchange defaultExchange() {
//    	return new TopicExchange(MyRabbitProperties.EXCHANGE);
//    }
    
//    @Bean
//    public DirectExchange defaultExchange() {
//        return new DirectExchange(MyRabbitProperties.EXCHANGE);
//    }
//    
    @Bean
    public Queue queue() {
        return new Queue("hello", true);
    }
    
    @Bean
    public Queue queue1() {
        return new Queue("DATA_PUBLISH", true);
    }
//    
//    @Bean
//    public Binding binding0a() {
//      return BindingBuilder.bind(queue()).to(defaultExchange()).with(MyRabbitProperties.ROUTINGKEY);
//    }
}
