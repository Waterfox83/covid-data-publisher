/*
package com.jda.starter.api.comment;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jda.starter.BaseTestCase;
import com.jda.starter.api.Routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public class CommentControllerIntegrationTest extends BaseTestCase {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void createComment() throws Exception {
    CommentContract commentContract = new CommentContract("id", "label", "sample");
    mockMvc
        .perform(post(Routes.LATEST_VERSION + Routes.COMMENTS)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(commentContract)))
        .andExpect(status().isCreated());
  }

  @Test
  public void readComments() throws Exception {
    mockMvc.perform(get(Routes.LATEST_VERSION + Routes.COMMENTS))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void givenInvalidComment_ThrowsBadRequest() throws Exception {
    CommentContract commentContract = new CommentContract("id", "label", "");
    mockMvc
        .perform(post(Routes.LATEST_VERSION + Routes.COMMENTS)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(commentContract)))
        .andExpect(status().isBadRequest());
  }
}
*/
