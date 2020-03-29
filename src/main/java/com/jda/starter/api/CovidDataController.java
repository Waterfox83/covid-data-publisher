package com.jda.starter.api;

import com.jda.starter.api.stats.WorldStat;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(Routes.LATEST_VERSION)
public class CovidDataController {

  private String HEADER_RAPIDAPI_HOST = "x-rapidapi-host";
  private String HEADER_RAPIDAPI_KEY = "x-rapidapi-key";

  @Value("${coronavirus.worldStatisticsApi}")
  private String coronavirusWorldStatisticsApiUrl;

  @Value("${coronavirus.rapidApiHost}")
  private String coronavirusRapidApiHost;

  @Value("${rapidApiKey}")
  private String rapidApiKey;


  @GetMapping(Routes.WORLD_STAT)
  public WorldStat getWorldStats() throws IOException {
    CloseableHttpClient httpclient = HttpClients.createDefault();

    HttpGet httpGet = new HttpGet(coronavirusWorldStatisticsApiUrl);
    httpGet.addHeader(HEADER_RAPIDAPI_HOST, coronavirusRapidApiHost);
    httpGet.addHeader(HEADER_RAPIDAPI_KEY, rapidApiKey);

    CloseableHttpResponse response = httpclient.execute(httpGet);

    WorldStat worldStat = null;
    try {
      HttpEntity entity = response.getEntity();
      String responseValue = EntityUtils.toString(entity);
      worldStat = (new Gson()).fromJson(responseValue, WorldStat.class);
      log.info(worldStat.toString());

    } finally {
      response.close();
    }
    return worldStat;
  }
}
