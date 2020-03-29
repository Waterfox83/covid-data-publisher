package com.jda.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//CSOFF: HideUtilityClassConstructor
@SpringBootApplication
public class StarterApplication {

  public static void main(String[] args) {
    SpringApplication.run(StarterApplication.class, args);
  }

  public static final class Profiles {
    public static final String TEST = "test";
  }

}
//CSON: HideUtilityClassConstructor
