package core;

import org.junit.jupiter.api.Test;

import info.movito.themoviedbapi.model.MovieDb;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieSearchTest {

  /**
   * This test is dependent on the fact that the online database will return the
   * expected movie, as long as no new movies including "dune" in their name is
   * released, this test method should work...
   */
  @Test
  public void testSearchResult() {
    MovieSearch test = new MovieSearch();
    String movieTitle = test.searchResult("dune").get(0).getTitle();
    assertEquals(movieTitle, "Dune");
  }

  @Test
  public void testId() {
    MovieSearch test = new MovieSearch();
    MovieDb moviedb = test.searchResult("end").get(0);
    String id = test.getMovieId(moviedb);
    assertEquals("107985", id);

    moviedb = test.getMovieById("107985");
    assertEquals("The World's End", moviedb.getTitle());
  }
}
