package ui.utils;

import core.Movie;
import core.Review;
import core.MovieLibrary;

public interface LibraryDataAccess {

  /**
   * Gets MovieLibrary.
   *
   * @return the MovieLibrary
   */
  MovieLibrary getMovieLibrary();

  /**
   * Add movie to movieLibrary.
   *
   * @param movie Movie movie to be added
   */
  boolean addMovie(Movie movie) throws Exception;

  /**
   * Add review to movie.
   *
   * @param Review Review review to be added
   */
  boolean addReviewToMovie(String movieId, Review review);

  /**
   * Deletes movie based on given id.
   *
   * @param id movie id
   */
  boolean deleteMovie(String id);

}
