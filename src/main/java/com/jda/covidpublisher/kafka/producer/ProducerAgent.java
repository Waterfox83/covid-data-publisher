package com.jda.covidpublisher.kafka.producer;

import com.jda.covidpublisher.api.stats.CountryStatsData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProducerAgent {

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  public ProducerAgent(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public Boolean sendWorldStatsMessage(String topic, WorldStatsMessage message) {
    log.info(String.format("Producing message --> %s on topic %s", message, topic));
    ObjectMapper mapper = new ObjectMapper();
    try {
      this.kafkaTemplate.send(topic, mapper.writeValueAsString(message));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }

  public Boolean sendCountryStatsMessage(String topic, CountryStatsData message) {
    log.info(String.format("Producing message --> %s on topic %s", message, topic));
    ObjectMapper mapper = new ObjectMapper();
    try {
      this.kafkaTemplate.send(topic, mapper.writeValueAsString(message));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }
}