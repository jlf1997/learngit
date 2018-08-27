package com.cimr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(resolver = ProfilesResolver.class)
public class KafkaTest {
	
	@Autowired
	private KafkaTemplate kafkaTemplate;

	
	@Test
	public void testKafka() {
		kafkaTemplate.send("testKafkaTopic","test123456");
		
	}
	
	
}
