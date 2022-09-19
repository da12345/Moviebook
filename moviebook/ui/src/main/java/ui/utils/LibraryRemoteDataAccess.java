package ui.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import core.Movie;
import core.Review;
import core.MovieLibrary;
import persistence.MoviePersistence;

import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import com.fasterxml.jackson.databind.JsonNode;

public class LibraryRemoteDataAccess implements LibraryDataAccess {

  private URI endPointURI;

  public LibraryRemoteDataAccess(URI endPointURI) {
    this.endPointURI = endPointURI;
  }

  /**
   * Gets MovieLibrary. Sends http get request to remote server
   *
   * @return the MovieLibrary
   */
  @Override
  public MovieLibrary getMovieLibrary() {
    MovieLibrary movies = new MovieLibrary();
    try {
      MoviePersistence persistence = new MoviePersistence();
      final HttpRequest req = HttpRequest.newBuilder(URI.create(endPointURI + "/movieLibrary"))
          .header("Accept", "application/json").GET().build();
      final HttpResponse<String> res = HttpClient.newBuilder().build().send(req, HttpResponse.BodyHandlers.ofString());

      ObjectMapper mapper = new ObjectMapper();
      JsonNode node = mapper.readTree(res.body());
      JsonNode libraryNode = node.get("movies");
      try {

        movies = persistence.deserializeLibrary(libraryNode);
      } catch (Exception e) {
        System.out.println("yes");
      }
      return movies;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Add movie to MovieLibrary. Sends http post request to remote server
   *
   * @param movie Movie to add
   */
  @Override
  public boolean addMovie(Movie movie) throws Exception {
    boolean successfullyAdded = false;

    String jsonString = "{\"movieName\": \"" + movie.getTitle() + "\", \"movieId\": \"" + movie.getMovieId() + "\"}";

    try {
      final HttpRequest req = HttpRequest.newBuilder(URI.create(endPointURI + "/movie"))
          .header("Accept", "application/json").header("Content-Type", "application/json")
          .POST(BodyPublishers.ofString(jsonString)).build();
      final HttpResponse<String> res = HttpClient.newBuilder().build().send(req, HttpResponse.BodyHandlers.ofString());
      if (res.body().equals("true")) {
        successfullyAdded = true;
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Movie: " + movie.getTitle() + " already added!");
    }
    return successfullyAdded;
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
    boolean successfullyAdded = false;

    String jsonString = "{\"movieId\": \"" + movieId + "\", \"comment\": \"" + review.getComment()
        + "\", \"rating\": \"" + review.getRating() + "\"}";

    try {
      final HttpRequest req = HttpRequest.newBuilder(URI.create(endPointURI + "/movie"))
          .header("Accept", "application/json").header("Content-Type", "application/json")
          .PUT(BodyPublishers.ofString(jsonString)).build();
      final HttpResponse<String> res = HttpClient.newBuilder().build().send(req, HttpResponse.BodyHandlers.ofString());
      if (res.body().equals("true")) {
        successfullyAdded = true;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return successfullyAdded;
  }

  /**
   * Deletes movie based on given id. Sends http delete request to remote server.
   *
   * @param id movie id
   */
  @Override
  public boolean deleteMovie(String id) {
    Boolean succesfullyRemoved = false;

    try {
      final HttpRequest req = HttpRequest.newBuilder(URI.create(endPointURI + "/movie?movieId=" + id))
          .header("Accept", "application/json").DELETE().build();
      final HttpResponse<String> res = HttpClient.newBuilder().build().send(req, HttpResponse.BodyHandlers.ofString());

      if (res.body().equals("true")) {
        succesfullyRemoved = true;
      }

    } catch (Exception e) {

      System.out.println(e.getMessage());
    }
    return succesfullyRemoved;
  }
}
