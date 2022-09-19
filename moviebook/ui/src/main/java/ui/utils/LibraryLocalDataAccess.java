package ui.utils;

import core.Movie;
import core.Review;
import core.MovieLibrary;
import persistence.MoviePersistence;

public class LibraryLocalDataAccess implements LibraryDataAccess {

  private MoviePersistence persistence;
  private MovieLibrary movieLibrary;

  public LibraryLocalDataAccess() {
    persistence = new MoviePersistence();
    movieLibrary = getMovieLibrary();
  }

  /**
   * Read movieLibrary from local file and return it.
   *
   * @return MovieLibrary
   */
  @Override
  public MovieLibrary getMovieLibrary() {

    MovieLibrary ml = new MovieLibrary();
    try {
      ml = persistence.loadFromFile();

    } catch (Exception e) {
      System.out.println(e);
    }
    return ml;
  }

  /**
   * Adds movie to movieLibrary.
   *
   * @param movie to add
   * @return true
   */
  @Override
  public boolean addMovie(Movie movie) throws Exception {
    movieLibrary.addMovie(movie);
    saveToFile();
    return true;
  }

  /**
   * Adds a review to a movie.
   *
   * @param String movieId to decide movie
   * @param Review review to add to movie
   * @return true
   */
  @Override
  public boolean addReviewToMovie(String movieId, Review review) {
    movieLibrary.addReviewById(movieId, review);
    saveToFile();
    return true;
  }

  /**
   * Deletes movie with given id.
   *
   * @param movieId movie id to delete
   * @return true
   */
  @Override
  public boolean deleteMovie(String movieId) {
    movieLibrary.deleteMovieById(movieId);
    saveToFile();
    return true;
  }

  /**
   * Saves the movieLibrary to local file.
   */
  private void saveToFile() {
    try {
      persistence.saveToFile(movieLibrary);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
