package core;

import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.MovieDb;
import java.util.List;

/**
 * After runtime, we dont want to save anything from the database 
 * except for the movie ID, which is why this is an independent class.
 */
public final class MovieInformation {
  private String movieImagePath;
  private String movieTitle;
  private String movieRelease;
  private String movieGenres;

  /**
  * Instanciates the objecct based on the movie information from the database.
  *
  * @param movie MovieDB object which contains diverse movie-info. 
  */
  public MovieInformation(MovieDb movie) {
    this.movieImagePath = movie.getPosterPath();
    this.movieTitle = movie.getTitle();
    this.movieRelease = movie.getReleaseDate();
    List<Genre> genres = movie.getGenres();
    StringBuilder genreText = new StringBuilder();
    for (Genre genre : genres) {
      if (genre.equals(genres.get(0))) {
        genreText.append(genre.getName());
      } else {
        genreText.append(", " + genre.getName());
      }
    }
    this.movieGenres = genreText.toString();
  }

  public String getMovieImagePath() {
    return movieImagePath;
  }

  public String getMovieTitle() {
    return movieTitle;
  }

  public String getMovieRelease() {
    return movieRelease;
  }

  public String getMovieGenres() {
    return movieGenres;
  }   
}
