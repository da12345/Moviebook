package persistence;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import core.Movie;
import core.MovieLibrary;
import core.Review;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class which serializes movielibrary objects, and stores them in a json
 * format.
 */
public class MoviePersistence {
  private ObjectMapper mapper;
  // File path and name. In windows the file will normally be located at
  // C:\Users\<username>
  private Path saveFilePath = Paths.get(System.getProperty("user.home"), "moviebook");

  public MoviePersistence() {
    mapper = new ObjectMapper();
  }

  /**
   * Takes a single movie and converts to a JsonNode object, used in
   * serializeMovieLibrary format: { "title" : "...", "movieId" : ...}
   */
  private JsonNode serializeMovie(Movie movie) {
    return mapper.valueToTree(movie);
  }

  /**
   * Takes a single review and converts to a JsonNode object, used in
   * serializeMovieLibrary format: { "comment" : "...", "rating" : ...}
   * 
   * private JsonNode serializeReview(Review review) { return
   * mapper.valueToTree(review); }
   */

  /**
   * Takes the list of movies and converts to an ArrayNode - the final json
   * structure ready for saving. format: [{movie 1}, {movie2}, {movie3}, {...}]
   *
   * @param movies MovieLibrary that is to be serialized.
   */
  private JsonNode serializeMovieLibrary(MovieLibrary movies) throws Exception {
    ArrayNode arrayNode = mapper.createArrayNode();
    for (Movie movie : movies.getMovies()) {
      JsonNode serializedMovie = serializeMovie(movie);
      arrayNode.add(serializedMovie);
    }
    return arrayNode;
  }

  /**
   * Using serializeMovieLibrary, saves the list of movies to user.home in the
   * file 'moviebook.json'.
   *
   * @param movies Takes a MovieLibrary as argument, as this is the serialized
   *               object.
   */
  public void saveToFile(MovieLibrary movies) throws Exception {
    JsonNode node = serializeMovieLibrary(movies);
    try (Writer writer = new OutputStreamWriter(new FileOutputStream(saveFilePath.toFile()), "UTF-8")) {
      mapper.writerWithDefaultPrettyPrinter().writeValue(writer, node);
    }
  }

  /**
   * Retrieves the file "moviebook.json" from user.home, and saves in an instance
   * of MovieLibrary.
   *
   * @return Returns a MovieLibrary object based on the info in the moviebook.json
   *         file.
   */
  public MovieLibrary loadFromFile() throws Exception {
    JsonNode libraryNode = mapper.readTree(saveFilePath.toFile());
    return deserializeLibrary(libraryNode);
  }

  /**
   * Retrieves the file "moviebook.json" from user.home, and saves in an instance
   * of MovieLibrary.
   *
   * @return Returns a MovieLibrary object based on the info in the moviebook.json
   *         file.
   */
  public MovieLibrary deserializeLibrary(JsonNode libraryNode) throws Exception {
    MovieLibrary movies = new MovieLibrary();
    for (JsonNode movieNode : libraryNode) {
      Movie movie = new Movie(movieNode.get("title").asText());
      String movieId = movieNode.get("movieId").asText();
      JsonNode reviewNode = movieNode.get("review");

      Boolean hasReview = (reviewNode.size() != 0);
      if (hasReview) {
        String rating = reviewNode.get("rating").asText();
        String comment = reviewNode.get("comment").asText();
        Review review = new Review(comment, Integer.parseInt(rating));
        movie.setReview(review);
      }
      movie.setMovieId(movieId);
      movies.addMovie(movie);
    }

    return movies;
  }
}
