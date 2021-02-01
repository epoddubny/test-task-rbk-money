package com.github.epoddubny.testtaskrbkmoney;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
@ActiveProfiles("test")
@EmbeddedKafka
public abstract class BaseSpringBootTest {
}
