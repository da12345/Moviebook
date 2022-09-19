package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.InputStreamReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import core.Movie;
import core.MovieLibrary;
import core.Review;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AbstractAppControllerTest extends ApplicationTest {
  /**
   * Using LocalAppController to test with local storage, avoiding server startup.
   */
  private LocalAppController c;
  private static ObjectMapper mapper = new ObjectMapper();
  private MovieLibrary localLibrary = new MovieLibrary();

  Scene scene;

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("AppTest.fxml"));
    final Parent root = loader.load();
    this.c = loader.getController();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @BeforeEach
  public void setup() {
    addLibraryToController();
  }

  private void addLibraryToController() {
    try {
      JsonNode node = mapper.readTree(new InputStreamReader(getClass().getResourceAsStream("movieLibrary-test.json")));
      for (JsonNode movieNode : node) {
        Movie movie;
        movie = new Movie(movieNode.get("movieTitle").asText(), movieNode.get("movieId").asText());
        JsonNode reviewNode = movieNode.get("review");
        if (reviewNode.size() != 0) {
          Review review = new Review(reviewNode.get("comment").asText(),
              Integer.parseInt(reviewNode.get("rating").asText()));
          movie.setReview(review);
        }
        c.addMovieToMovieLibrary(movie);
        System.out.println("adding");
        localLibrary.addMovie(movie);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  // @Test
  // public void testController() {

  // MovieLibrary controllerLibrary = this.c.getLibrary();

  // assertNotNull(this.c);
  // assertNotNull(this.localLibrary);
  // assertNotNull(controllerLibrary);

  // for (int i = 0; i < controllerLibrary.getMoviesSize(); i++) {
  // assertEquals(this.localLibrary.getMovieByIndex(i).getTitle(),
  // controllerLibrary
  // .getMovieByIndex(i + controllerLibrary.getMoviesSize() -
  // controllerLibrary.getMoviesSize()).getTitle());
  // assertEquals(controllerLibrary.getMovieByIndex(i).getMovieId(),
  // controllerLibrary
  // .getMovieByIndex(i + controllerLibrary.getMoviesSize() -
  // controllerLibrary.getMoviesSize()).getMovieId());
  // }
  // }
}
