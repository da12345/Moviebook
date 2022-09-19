package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Holder for many movies.
 */
public class MovieLibrary {
  private List<Movie> movies = new ArrayList<>();

  public enum SortType {
    TITLE,
    RATING
  }

  /**
   * Instantiates an empty MovieLibrary object.
   */
  public MovieLibrary() {
  }

  /**
   * Constructor that recreates a MovieLibrary given a list of Movie objects.
   *
   * @param movies simple movie object from Movie.java.
   */
  public MovieLibrary(List<Movie> movies) {
    for (Movie m : movies) {
      this.movies.add(m);
    }
  }

  /**
   * Adds a movie to the MovieLibrary class, given a Movie object.
   *
   * @param movie simple movie object from Movie.java.
   */
  public void addMovie(Movie movie) throws Exception {
    for (Movie m : movies) {
      if (m.getTitle().equals(movie.getTitle())) {
        throw new IllegalArgumentException();
      }
    }
    movies.add(movie);
  }

  /**
   * Removes a movie based on its position in library.
   *
   * @param index This equals the index in the library.
   */
  public void deleteMovieByIndex(int index) {
    movies.remove(index);
  }

  /**
   * Deletes a movie from the MovieLibrary class, given the name of the movie.
   *
   * @param title this should equal the title attribute of a movie object.
   */
  public void deleteMovieByTitle(String title) {
    for (int i = 0; i < getMoviesSize(); i++) {
      if (title.equals(movies.get(i).getTitle())) {
        movies.remove(i);
        return;
      }
    }
  }

  /**
   * Method to delete movie in library, considering 
      there is a movie in the library with the given ID.
   *
   * @param movieId equals movieID string from a movieobject.
   */
  public void deleteMovieById(String movieId) {
    for (int i = 0; i < getMoviesSize(); i++) {
      if (movieId.equals(movies.get(i).getMovieId())) {
        movies.remove(i);
        return;
      }
    }
  }

  /**
   * Method to add a review to a movie with a certain ID in the library.
   *
   * @param movieId equals movieID string from a movieobject.
   * @param review instance of a Review object
   */
  public void addReviewById(String movieId, Review review) {
    for (int i = 0; i < getMoviesSize(); i++) {
      if (movieId.equals(movies.get(i).getMovieId())) {
        movies.get(i).setReview(review);
        return;
      }
    }
  }

  public int getMoviesSize() {
    return movies.size();
  }

  public List<Movie> getMovies() {
    return new ArrayList<Movie>(movies);
  }

  /**
   * Returns a list of the titles of all the movies in the library.
   *
   * @return title of every movie object in the library.
   */
  public List<String> getMovieTitles() {
    List<String> titles = new ArrayList<String>();
    for (Movie m : movies) {
      titles.add(m.getTitle());
    }
    return titles;
  }

  /**
   * Method for sorting library alphabetically by movie title.
   *
   * @param unsorted Takes the unsorted MovieLibrary as its only parameter.
   * @return Returns a sorted MovieLibrary, sorted alphabetically by title
   *         increasing.
   */
  public MovieLibrary getSorted(MovieLibrary unsorted, boolean sortmethod, SortType sorttype) throws Exception {
    // In this method, for-loops are used instead of for-each loops in order to easier access the index of "sorted".
    if (sorttype == SortType.TITLE && sortmethod == false) {
      List<Movie> sorted = new ArrayList<Movie>(unsorted.getMovies());
      Collections.sort(sorted, new SortTitle());
      this.clear();
      for (int i = 0; i < sorted.size(); i++) {
        this.addMovie(sorted.get(i));
      }
      return this;
    } else if (sorttype == SortType.TITLE && sortmethod == true) {
      List<Movie> sorted = new ArrayList<Movie>(unsorted.getMovies());
      Collections.reverse(sorted);
      this.clear();
      for (int i = 0; i < sorted.size(); i++) {
        this.addMovie(sorted.get(i));
      }
      return this;
    } else if (sorttype == SortType.RATING && sortmethod == false) {
      List<Movie> sorted = new ArrayList<Movie>(unsorted.getMovies());
      Collections.sort(sorted, new SortRating());
      this.clear();
      for (int i = 0; i < sorted.size(); i++) {
        this.addMovie(sorted.get(i));
      }
      return this;
    } else if (sorttype == SortType.RATING && sortmethod == true) {
      List<Movie> sorted = new ArrayList<Movie>(unsorted.getMovies());
      Collections.reverse(sorted);
      this.clear();
      for (int i = 0; i < sorted.size(); i++) {
        this.addMovie(sorted.get(i));
      }
      return this;
    } else {
      throw new IllegalArgumentException("Could not sort list!");
    } 
  }

  public Movie getMovieByIndex(int n) {
    return movies.get(n);
  }

  /**
   * Deletes all movies from the library.
   */
  public void clear() {
    movies.clear();
  }

  /**
   * Class implementing compare function so that movie titles can be compared, and
   * used to sort movies.
   */
  private static class SortTitle implements Comparator<Object>, Serializable {
    @Override
    public int compare(Object o1, Object o2) {
      Movie m1 = (Movie) o1;
      Movie m2 = (Movie) o2;
      String s1 = m1.getTitle();
      String s2 = m2.getTitle();
      return s1.toLowerCase().compareTo(s2.toLowerCase());
    }
  }

  /**
   * Class implementing compare function so that movie ratings can be compared, and
   * used to sort movies.
   */
  private static class SortRating implements Comparator<Object>, Serializable {
    @Override
    public int compare(Object o1, Object o2) {
      Movie m1 = (Movie) o1;
      Movie m2 = (Movie) o2;
      if (m1.getReview() == null) {
        return -1;
      } else if (m2.getReview() == null) {
        return 1;
      }
      Integer s1 = m1.getReview().getRating();
      Integer s2 = m2.getReview().getRating();
      return s1.compareTo(s2);
    }
  }
}
