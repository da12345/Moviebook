package core;

import core.MovieLibrary.SortType;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieLibraryTest {

  MovieLibrary library;
  Movie movie1;
  Movie movie2;

  @BeforeEach
  public void createLibrary() throws Exception {
    library = new MovieLibrary();
    movie1 = new Movie("test movie 1", "5");
    movie2 = new Movie("test movie 2", "4");
  }

  @Test
  public void testConstructor() {
    List<Movie> movies = new ArrayList<>();
    movies.add(movie1);
    movies.add(movie2);
    library = new MovieLibrary(movies);
  }

  @Test
  public void testAdd() throws Exception {
    library.addMovie(movie1);
    Assertions.assertEquals(1, library.getMovies().size());
    library.addMovie(movie2);
    Assertions.assertEquals(2, library.getMovies().size());
    Assertions.assertThrows(Exception.class, () -> {
      library.addMovie(movie1);
    }); // tries to add duplicate
    Assertions.assertEquals(2, library.getMovies().size());
  }

  @Test
  public void testRemove() throws Exception {
    library.addMovie(movie1);
    Assertions.assertEquals(1, library.getMovies().size());
    library.deleteMovieByIndex(0);
    Assertions.assertEquals(0, library.getMovies().size());
  }

  @Test
  public void testClear() throws Exception {
    library.addMovie(movie1);
    library.addMovie(movie2);
    library.clear();
    Assertions.assertEquals(0, library.getMovies().size());
  }

  @Test
  public void testSetMovieId() throws Exception {
    movie1.setMovieId("1");
    assertEquals("1", movie1.getMovieId());
  }

  @Test
  public void testGetMovieTitles() throws Exception {
    library.addMovie(movie1);
    library.addMovie(movie2);
    List<String> expectedtitles = new ArrayList<String>();
    expectedtitles.add("test movie 1");
    expectedtitles.add("test movie 2");
    for (int i = 0; i < expectedtitles.size(); i++) {
      Assertions.assertEquals(expectedtitles.get(i), library.getMovieTitles().get(i));
    }
  }

  @Test
  public void testdeleteMovieByTitle() throws Exception {
    library.addMovie(movie1);
    library.addMovie(movie2);

    library.deleteMovieByTitle("test movie 1");

    assertEquals(library.getMoviesSize(), 1);
    assertEquals(library.getMovieTitles().get(0), "test movie 2");

    library.deleteMovieByTitle("nonexisting title");

    assertEquals(library.getMoviesSize(), 1);
    assertEquals(library.getMovieTitles().get(0), "test movie 2");
  }

  @Test
  public void testGetMovieByIndex() throws Exception {
    library.addMovie(movie1);
    library.addMovie(movie2);

    assertEquals(library.getMovieByIndex(1).getTitle(), "test movie 2");
  }

  @Test
  public void testAddOrDeleteById() throws Exception {
    movie1.setMovieId("1111");
    movie2.setMovieId("2222");
    library.addMovie(movie1);
    library.addMovie(movie2);
    
    Review review = new Review("comment", 5);
    library.addReviewById("1111", review);
    assertEquals(review, library.getMovieByIndex(0).getReview()); 
    

    library.deleteMovieById("1111");
    library.addReviewById("213121", review);
    assertNotEquals(review, library.getMovieByIndex(0).getReview());

    assertEquals(1, library.getMoviesSize());
    assertEquals("2222", library.getMovieByIndex(0).getMovieId());

    library.deleteMovieById("12313213132121");
    assertEquals(1, library.getMoviesSize());
    
  }

  @Test
  public void testSort() throws Exception {
    Movie movie3 = new Movie("test movie 3");
    library.addMovie(movie1);
    library.addMovie(movie2);
    library.addMovie(movie3);
    movie1.setMovieId("1");
    movie2.setMovieId("2");
    movie2.setMovieId("3");

    Review review1 = new Review("comment1", 5);
    Review review3 = new Review("comment3", 3);
    library.addReviewById("comment1", review1);
    library.addReviewById("comment3", review3);

    library = library.getSorted(library, false, SortType.TITLE);
    assertEquals("test movie 1", library.getMovieByIndex(0).getTitle());
    library = library.getSorted(library, true, SortType.TITLE);
    assertEquals("test movie 3", library.getMovieByIndex(0).getTitle());
    library = library.getSorted(library, false, SortType.RATING);
    assertEquals("test movie 1", library.getMovieByIndex(0).getTitle());
    library = library.getSorted(library, true, SortType.RATING);
    assertEquals("test movie 3", library.getMovieByIndex(0).getTitle());
  }
}
