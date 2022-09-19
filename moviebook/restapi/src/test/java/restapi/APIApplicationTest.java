package restapi;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import core.Movie;
import core.MovieLibrary;
import persistence.MoviePersistence;

/**
 * Tests the restapi and the server
 * 
 * @author GR2168
 */
@SpringBootTest(classes = { APIController.class, APIApplication.class })
@AutoConfigureMockMvc
public class APIApplicationTest {

  // @Autowired
  // private MockMvc mockMvc;
  // @Autowired
  // public APIApplication apiApplication;
  // private String BASE_URL = "http://localhost:8080";
  // MvcResult result;
  // MovieLibrary movies = new MovieLibrary();

  // /**
  // * Tests the server
  // */
  // // @Test
  // // void getApiApplication() throws Exception {
  // // assertNotNull(apiApplication);
  // // }

  // /**
  // * Tests that a movieLibrary is received after calling a GET function on
  // * /movieLibrary
  // */
  // @Test
  // public void testGetMovieLibrary() throws Exception {
  // this.result = this.mockMvc.perform(get(BASE_URL +
  // "/movieLibrary")).andDo(print()).andExpect(status().isOk())
  // .andExpect(content().string(containsString("movies"))).andExpect(content().string(containsString("moviesSize")))
  // .andExpect(content().string(containsString("movieTitles"))).andReturn();

  // ObjectMapper mapper = new ObjectMapper();
  // MoviePersistence persistence = new MoviePersistence();
  // JsonNode libraryNode =
  // mapper.readTree(result.getResponse().getContentAsString());
  // // this.movies = persistence.deserializeLibrary(libraryNode);
  // // System.out.println(this.movies.getMovieByIndex(0).getTitle());
  // }

  // @Test
  // public void testPostMovie() throws Exception {
  // String jsonString = "{\"movieName\": \"test movie 1\", \"movieId\":
  // \"12332\"}";
  // this.mockMvc.perform(
  // MockMvcRequestBuilders.post(BASE_URL +
  // "/movie").contentType(MediaType.APPLICATION_JSON).content(jsonString))
  // .andExpect(status().isOk());

  // // System.out.println(mockMvc.);

  // }

}