package com.jda.covidpublisher.azure.eventhubs.springkafka.producer;

import static org.junit.Assert.assertEquals;

import com.jda.covidpublisher.BaseTestCase;
import com.jda.covidpublisher.kafka.producer.WorldStatsMessage;
import com.jda.covidpublisher.kafka.producer.ProducerAgent;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ProducerAgentIntegrationTest extends BaseTestCase {

  @Autowired
  private ProducerAgent producerAgent;

  private KafkaMessageListenerContainer<String, String> container;

  private BlockingQueue<ConsumerRecord<String, String>> records;

  @Before
  public void setUp() throws Exception {
    Map<String, Object> consumerProperties =
        KafkaTestUtils.consumerProps("default", "false", EMBEDDED_KAFKA_RULE.getEmbeddedKafka());

    DefaultKafkaConsumerFactory<String, String> consumerFactory =
        new DefaultKafkaConsumerFactory<String, String>(consumerProperties);

    ContainerProperties containerProperties = new ContainerProperties(TOPIC_SENDER);

    // create a Kafka MessageListenerContainer
    container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
    records = new LinkedBlockingQueue<>();

    container.setupMessageListener(new MessageListener<String, String>() {
      @Override
      public void onMessage(
          ConsumerRecord<String, String> record) {
        log.debug("test-listener received message='{}'", record.toString());
        records.add(record);
      }
    });

    container.start();

    ContainerTestUtils.waitForAssignment(container, EMBEDDED_KAFKA_RULE.getEmbeddedKafka().getPartitionsPerTopic());

  }

  @After
  public void tearDown() {
    container.stop();
  }

  //CSOFF: MagicNumber
  @Test
  public void testSendWorldStatsMessage() throws InterruptedException {
    producerAgent.sendWorldStatsMessage(TOPIC_SENDER, new WorldStatsMessage(100));

    ConsumerRecord<String, String> received = records.poll(10, TimeUnit.SECONDS);
    Gson gson = new Gson();
    WorldStatsMessage msg = gson.fromJson(received.value(), WorldStatsMessage.class);
    assertEquals(msg.getNewCases(), 100);
  }
  //CSON: MagicNumber
}
