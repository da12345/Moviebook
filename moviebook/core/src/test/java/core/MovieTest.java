package core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;

public class MovieTest {

  @Test
  public void testConstructor() throws Exception {
    Movie movie1 = new Movie("aa", "1");
    Assertions.assertEquals("aa", movie1.getTitle());
    Movie movie2 = new Movie("aaaa");
    Assertions.assertEquals("aaaa", movie2.getTitle());
    testValidTitle(null);
    testValidTitle("");
  }

  private void testValidTitle(String title) throws Exception {
    assertThrows(IllegalArgumentException.class, () -> {
      new Movie(title, "1");
    });
  }

  @Test
  public void testSetReview() throws Exception {
    Movie movie1 = new Movie("aa", "2");
    Review review = new Review("bla bla bla", 2);
    movie1.setReview(review);
    assertEquals(2, movie1.getReview().getRating());
    movie1.getReview().setRating(1);
    assertEquals(1, movie1.getReview().getRating());
    assertEquals("bla bla bla", movie1.getReview().getComment());
  }
}
