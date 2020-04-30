package com.jda.covidpublisher.api.stats;

import java.util.ArrayList;

public class CountryStatsData {
  private String get;
  private Parameters parameters;
  private ArrayList<Object> errors = new ArrayList<>();
  private float results;
  private ArrayList<Response> response = new ArrayList<>();

  public CountryStatsData(String get) {
    this.get = get;
  }

  public String getGet() {
    return get;
  }

  public Parameters getParameters() {
    return parameters;
  }

  public float getResults() {
    return results;
  }

  public ArrayList<Response> getResponse() {
    return response;
  }

  public ArrayList<Object> getErrors() {
    return errors;
  }

  public void setGet(String get) {
    this.get = get;
  }

  public void setParameters(Parameters parametersObject) {
    this.parameters = parametersObject;
  }

  public void setResults(float results) {
    this.results = results;
  }

  public void setResponse(ArrayList<Response> response) {
    this.response = response;
  }

  public void setErrors(ArrayList<Object> errors) {
    this.errors = errors;
  }

  @Override
  public String toString() {
    return "CountryStatsData{" +
        "get='" + get + '\'' +
        ", ParametersObject=" + parameters +
        ", errors=" + errors +
        ", results=" + results +
        ", response=" + response +
        '}';
  }
}

class Response {
  private String country;
  Cases cases;
  Deaths deaths;
  Tests tests;
  private String day;
  private String time;

  public String getCountry() {
    return country;
  }

  public Cases getCases() {
    return cases;
  }

  public Deaths getDeaths() {
    return deaths;
  }

  public Tests getTests() {
    return tests;
  }

  public String getDay() {
    return day;
  }

  public String getTime() {
    return time;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setCases(Cases casesObject) {
    this.cases = casesObject;
  }

  public void setDeaths(Deaths deathsObject) {
    this.deaths = deathsObject;
  }

  public void setTests(Tests testsObject) {
    this.tests = testsObject;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public void setTime(String time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return "Response{" +
        "country='" + country + '\'' +
        ", CasesObject=" + cases +
        ", DeathsObject=" + deaths +
        ", TestsObject=" + tests +
        ", day='" + day + '\'' +
        ", time='" + time + '\'' +
        '}';
  }
}

class Cases {
  //  private String new = null;
  private float active;
  private float critical;
  private float recovered;
  private float total;

  //  public String getNew() {
  //    return new;
  //  }

  public float getActive() {
    return active;
  }

  public float getCritical() {
    return critical;
  }

  public float getRecovered() {
    return recovered;
  }

  public float getTotal() {
    return total;
  }

  //  public void setNew(String new) {
  //    this.new = new;
  //  }

  public void setActive(float active) {
    this.active = active;
  }

  public void setCritical(float critical) {
    this.critical = critical;
  }

  public void setRecovered(float recovered) {
    this.recovered = recovered;
  }

  public void setTotal(float total) {
    this.total = total;
  }

  @Override
  public String toString() {
    return "Cases{" +
        "active=" + active +
        ", critical=" + critical +
        ", recovered=" + recovered +
        ", total=" + total +
        '}';
  }
}

class Tests {
  private float total;

  public float getTotal() {
    return total;
  }

  public void setTotal(float total) {
    this.total = total;
  }

  @Override
  public String toString() {
    return "Tests{" +
        "total=" + total +
        '}';
  }
}

class Deaths {
  //  private String new = null;
  private float total;

  public float getTotal() {
    return total;
  }

  public void setTotal(float total) {
    this.total = total;
  }

  @Override
  public String toString() {
    return "Deaths{" +
        "total=" + total +
        '}';
  }
}

class Parameters {
  private String country;

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public String toString() {
    return "Parameters{" +
        "country='" + country + '\'' +
        '}';
  }
}