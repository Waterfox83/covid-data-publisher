package com.jda.covidpublisher.api.stats;

import static com.jda.covidpublisher.api.stats.WorldStatsTranslator.*;

import com.jda.covidpublisher.kafka.producer.WorldStatsMessage;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class WorldStatsTranslatorTest {

  @Test
  public void test_toKafkaMessageFormat() throws ParseException {
    WorldStatsData worldStatsData = new WorldStatsData();
    worldStatsData.setNew_cases("1,515");
    worldStatsData.setNew_deaths("81");
    worldStatsData.setTotal_recovered("547,679");
    worldStatsData.setTotal_deaths("145,551");
    worldStatsData.setTotal_cases("2,182,823");
    worldStatsData.setStatistic_taken_at("2020-04-17 04:33:09");

    WorldStatsMessage.MessageBuilder builder = WorldStatsMessage.MessageBuilder.aMessage();
    WorldStatsMessage expectedMessage = builder
            .withNewCases(1515)
            .withNewDeaths(81)
            .withStatisticDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-04-17 04:33:09"))
            .withTotalCases(2182823)
            .withTotalDeaths(145551)
            .withTotalRecovered(547679)
        .build();

    WorldStatsMessage receivedMessage = toKafkaMessageFormat(worldStatsData);
    assert expectedMessage.toString().equals(receivedMessage.toString());
  }

}
