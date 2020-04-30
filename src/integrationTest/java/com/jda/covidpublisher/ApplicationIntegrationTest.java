package com.jda.covidpublisher;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ApplicationIntegrationTest extends BaseTestCase {

  @Autowired
  private Application application;

  @Test
  public void launch_thenApplicationIsStarted() {
    assertThat(application, is(not(nullValue())));
  }
}
