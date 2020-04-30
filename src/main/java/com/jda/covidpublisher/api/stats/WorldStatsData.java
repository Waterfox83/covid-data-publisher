package com.jda.covidpublisher.api.stats;

import lombok.Data;

@Data
public class WorldStatsData {
  private String total_cases;
  private String total_deaths;
  private String total_recovered;
  private String new_cases;
  private String new_deaths;
  private String statistic_taken_at;
}
