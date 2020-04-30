package com.jda.covidpublisher.api.stats;

import com.jda.covidpublisher.kafka.producer.WorldStatsMessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class WorldStatsTranslator {
  public static WorldStatsMessage toKafkaMessageFormat(WorldStatsData data) throws ParseException {
    WorldStatsMessage.MessageBuilder builder = WorldStatsMessage.MessageBuilder.aMessage();

    int newCases = Integer.parseInt(data.getNew_cases().replaceAll(",", ""));
    int newDeaths = Integer.parseInt(data.getNew_deaths().replaceAll(",", ""));
    long totalCases = Long.parseLong(data.getTotal_cases().replaceAll(",", ""));
    long totalDeaths = Long.parseLong(data.getTotal_deaths().replaceAll(",", ""));
    long totalRecovered = Long.parseLong(data.getTotal_recovered().replaceAll(",", ""));

    return builder
        .withNewCases(newCases)
        .withNewDeaths(newDeaths)
        .withTotalCases(totalCases)
        .withTotalDeaths(totalDeaths)
        .withTotalRecovered(totalRecovered)
        .withStatisticDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.getStatistic_taken_at()))
        .build();
  }
}