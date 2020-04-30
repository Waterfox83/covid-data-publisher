package com.jda.covidpublisher;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, properties = {"spring.kafka.bootstrap-servers=${spring.embedded"
    + ".kafka.brokers}", "spring.kafka.properties.security.protocol=PLAINTEXT"})
@DirtiesContext
@ActiveProfiles(value = Application.Profiles.TEST)
public abstract class BaseTestCase {

  public static final String TOPIC_SENDER = "kafka-test-topic-sender";
  public static final String TOPIC_RECEIVER = "kafka-test-topic-receiver";

  @ClassRule
  public static final EmbeddedKafkaRule EMBEDDED_KAFKA_RULE = new EmbeddedKafkaRule(1, true,
      TOPIC_SENDER, TOPIC_RECEIVER);
}
