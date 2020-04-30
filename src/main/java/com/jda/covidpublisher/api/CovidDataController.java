package com.jda.covidpublisher.api;

import com.jda.covidpublisher.api.stats.CountryStatsData;
import com.jda.covidpublisher.api.stats.WorldStatsData;
import com.jda.covidpublisher.api.stats.WorldStatsTranslator;
import com.jda.covidpublisher.kafka.producer.ProducerAgent;
import com.jda.covidpublisher.kafka.producer.WorldStatsMessage;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping(Routes.LATEST_VERSION)
public class CovidDataController {

  private final String HEADER_RAPIDAPI_HOST = "x-rapidapi-host";
  private final String HEADER_RAPIDAPI_KEY = "x-rapidapi-key";

  @Value("${coronavirus.worldStatisticsApi}")
  private String coronavirusWorldStatisticsApiUrl;
  private final ProducerAgent producerAgent;

  @Value("${coronavirus.rapidApiHost}")
  private String coronavirusRapidApiHost;
  @Value("${covid19.statisticsApi}")
  private String coronavirusCountryStatisticsApiUrl;

  @Value("${rapidApiKey}")
  private String rapidApiKey;
  @Value("${covid19.rapidApiHost}")
  private String covid19RapidApiHost;
  @Value("${kafka.world-stats-topic}")
  private String worldStatsTopic;
  @Value("${kafka.country-stats-topic}")
  private String countryStatsTopic;

  @Autowired
  public CovidDataController(ProducerAgent producerAgent) {
    this.producerAgent = producerAgent;
  }

  @GetMapping(Routes.WORLD_STAT)
  public WorldStatsData getWorldStats() throws IOException {
    CloseableHttpClient httpclient = HttpClients.createDefault();

    HttpGet httpGet = new HttpGet(coronavirusWorldStatisticsApiUrl);
    httpGet.addHeader(HEADER_RAPIDAPI_HOST, coronavirusRapidApiHost);
    httpGet.addHeader(HEADER_RAPIDAPI_KEY, rapidApiKey);

    CloseableHttpResponse response = httpclient.execute(httpGet);

    WorldStatsData worldStatsData = null;
    try {
      HttpEntity entity = response.getEntity();
      String responseValue = EntityUtils.toString(entity);
      worldStatsData = (new Gson()).fromJson(responseValue, WorldStatsData.class);
      log.info(worldStatsData.toString());
      log.debug(worldStatsData.toString());

      // Asynchronously publishing to Kafka
      log.info("Calling send world stats data message");
      sendWorldStatsMessage(worldStatsData);
    } finally {
      response.close();
    }
    log.info("Returning data");
    return worldStatsData;
  }

  @Async
  public CompletableFuture<Boolean> sendWorldStatsMessage(WorldStatsData worldStatsData) {
    WorldStatsMessage message = null;
    try {
      message = WorldStatsTranslator.toKafkaMessageFormat(worldStatsData);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    Boolean status = producerAgent.sendWorldStatsMessage(worldStatsTopic, message);
    return CompletableFuture.completedFuture(status);
  }

  @GetMapping(Routes.COUNTRY_STATS)
  public CountryStatsData getCountryStats(@RequestParam("country") String countryName) throws IOException {
    CloseableHttpClient httpclient = HttpClients.createDefault();

    String parametrizedUrl = coronavirusCountryStatisticsApiUrl + "?country=" + countryName;
    HttpGet httpGet = new HttpGet(parametrizedUrl);
    httpGet.addHeader(HEADER_RAPIDAPI_HOST, covid19RapidApiHost);
    httpGet.addHeader(HEADER_RAPIDAPI_KEY, rapidApiKey);

    CloseableHttpResponse response = httpclient.execute(httpGet);

    CountryStatsData countryStatsData = null;
    try {
      HttpEntity entity = response.getEntity();
      String responseValue = EntityUtils.toString(entity);
      countryStatsData = (new Gson()).fromJson(responseValue, CountryStatsData.class);
      log.info(countryStatsData.toString());
      log.debug(countryStatsData.toString());

      log.info("Calling send country stats data message");
      sendCountryStatsMessage(countryStatsData);
    } finally {
      response.close();
    }

    log.info("Returning data");
    return countryStatsData;
  }

  @Async("threadPoolTaskExecutor")
  public CompletableFuture<Boolean> sendCountryStatsMessage(CountryStatsData countryStatsData) {
    Boolean status = producerAgent.sendCountryStatsMessage(countryStatsTopic, countryStatsData);
    return CompletableFuture.completedFuture(status);
  }
}
