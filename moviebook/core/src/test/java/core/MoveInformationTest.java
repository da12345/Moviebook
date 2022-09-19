package core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoveInformationTest {

  MovieSearch ms;
  MovieInformation mi;

  @BeforeEach
  public void setup() {
    ms = new MovieSearch();
    mi = new MovieInformation(ms.getMovieById("107985"));
  }

  @Test
  public void testGetMovieImagePath() {
    assertEquals("/kpglnOBYmKn0AkkWDzGxzKHDbds.jpg", mi.getMovieImagePath());
  }

  @Test
  public void testGetMovieTitle() {
    assertEquals("The World's End", mi.getMovieTitle());
  }

  @Test
  public void testGetMovieRelease() {
    assertEquals("2013-07-18", mi.getMovieRelease());
  }

  @Test
  public void testGetMovieGenres() {
    assertEquals("Comedy, Action, Science Fiction", mi.getMovieGenres());
  }
}
