package core;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import java.util.List;

/**
 * Base class where the API key is stored, and used to access the TMDB database,
 * to use search functions, or get information on a movie based on the movie ID.
 */
public class MovieSearch {

  /**
   * Getting an API key is free on themoviedb.org, and mainly requires that the
   * key is not used for commercial purposes. For this use case, it should be
   * fine.
   */
  private static final String KEY = "2aa55db19cb4d7b159b0d1e5b4055fbb";
  private TmdbApi api;

  public MovieSearch() {
    this.api = new TmdbApi(getKey());
  }

  public String getKey() {
    return KEY;
  }

  public TmdbApi getApi() {
    return api;
  }

  /**
   * Method will use the user input to find a list of movies from the database.
   *
   * @param search Search is equivalent of typing in a google search bar. This is
   *               essentially user input for movie name in the app.
   * @return movies is a list of movies that is returned from the database based
   *         on the search query.
   */
  public List<MovieDb> searchResult(String search) {
    TmdbSearch result = new TmdbSearch(getApi());
    List<MovieDb> movies = result.searchMovie(search, 0, null, true, 1).getResults();
    return movies;
  }

  /**
   * Method for getting movieID based on movie.
   *
   * @param movie object containing information about movie from TMDB database.
   * @return returns the ID of the movie. For persistence sake, the ID is used to
   *         retreive the movieDb object for the next runtime.
   */
  public String getMovieId(MovieDb movie) {
    return String.valueOf(movie.getId());
  }

  /**
   * Method for getting a MovieDb object if you have the movie ID.
   *
   * @param movieId databaseID - which is always the same and unique on TMDB.
   * @return returns movieDB object, contataing all info about film.
   */
  public MovieDb getMovieById(String movieId) {
    TmdbMovies movies = new TmdbMovies(getApi());
    return movies.getMovie(Integer.parseInt(movieId), "en");
  }
}
