package core;

/**
 * Represents a movie.
 */
public class Movie {
  private String title;
  private String movieId;
  private Review review;

  /**
   * Constructor for Movie-object.
   */

  public Movie(String title) throws Exception {
    setTitle(title);
  }

  public Movie(String title, String movieId) throws Exception {
    setTitle(title);
    setMovieId(movieId);
  }

  /**
   * Method for setting movie title.
   *
   * @param title title of movie. Empty string or null not allowed
   */
  public void setTitle(String title) {
    if (title != null && !title.equals("")) {
      this.title = title;
    } else {
      throw new IllegalArgumentException();
    }
  }

  public void setReview(Review review) {
    this.review = review;
  }

  public void setMovieId(String movieId) {
    this.movieId = movieId;
  }

  public String getTitle() {
    return title;
  }

  public Review getReview() {
    return review;
  }

  public String getMovieId() {
    return movieId;
  }
}
