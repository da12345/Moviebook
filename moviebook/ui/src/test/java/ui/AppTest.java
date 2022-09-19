package ui;

import java.io.InputStreamReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testfx.framework.junit5.ApplicationTest;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.Movie;
import core.MovieLibrary;
import core.Review;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * SuppressWarnings prevents ListView raw type warning. This is not considered
 * relevant for this class.
 */
@SuppressWarnings("rawtypes")
public class AppTest extends ApplicationTest {
  /**
   * Using LocalAppController to test with local storage, avoiding server startup.
   */
  private LocalAppController c;
  private ObjectMapper mapper = new ObjectMapper();
  private int numberOfTestMovies;
  MovieLibrary movies = new MovieLibrary();
  ListView listView;
  ListView searchBox;
  Label movieTitle;
  Label movieGenres;
  Label labelRating;
  Label labelComment;
  Label searchBoxErrorText;
  Label errorMessage;
  TextField inputName;
  TextField inputRating;
  TextField inputComment;
  TextField inputMovieId;
  Button newMovieButton;
  Button addMovieButton;
  Button editReviewButton;
  Button deleteMovieButton;

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("AppTest.fxml"));
    final Parent root = loader.load();
    this.c = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
    setup();
  }

  /**
   * 1) Looking up all fxml objects to be referenced by tests. 2) Adding test
   * movies to listView panel. This only temporarily adds items to the listView
   * itself for testing purposes, as it avoids using the persistence classes.
   */
  public void setup() throws Exception {
    this.listView = lookup("#listView").query();
    this.searchBox = lookup("#searchBox").query();
    this.movieTitle = lookup("#movieTitle").query();
    this.movieGenres = lookup("#movieGenres").query();
    this.labelRating = lookup("#labelRating").query();
    this.labelComment = lookup("#labelComment").query();
    this.searchBoxErrorText = lookup("#searchBoxErrorText").query();
    this.errorMessage = lookup("#errorMessage").query();
    this.inputName = lookup("#inputName").query();
    this.inputRating = lookup("#inputRating").query();
    this.inputComment = lookup("#inputComment").query();
    this.inputMovieId = lookup("#inputMovieId").query();
    this.newMovieButton = lookup("#newMovieButton").query();
    this.addMovieButton = lookup("#addMovieButton").query();
    this.editReviewButton = lookup("#editReviewButton").query();
    this.deleteMovieButton = lookup("#deleteMovieButton").query();

    JsonNode node = mapper.readTree(new InputStreamReader(getClass().getResourceAsStream("movieLibrary-test.json")));
    for (JsonNode movieNode : node) {
      numberOfTestMovies++;
      Movie movie;
      movie = new Movie(movieNode.get("movieTitle").asText(), movieNode.get("movieId").asText());
      JsonNode reviewNode = movieNode.get("review");
      if (reviewNode.size() != 0) {
        Review review = new Review(reviewNode.get("comment").asText(),
            Integer.parseInt(reviewNode.get("rating").asText()));
        movie.setReview(review);
      }
      this.movies.addMovie(movie);
    }
    addTestLibraryToController();
    c.setIsTesting(true);
  }

  private void addTestLibraryToController() throws Exception {

    for (int i = 0; i < movies.getMoviesSize(); i++) {
      c.addMovieToMovieLibrary(movies.getMovieByIndex(i));
    }
  }

  /**
   * Resets the test movies in listview
   */
  private void resetTestMovies() throws Exception {
    for (int i = 0; i < numberOfTestMovies; i++) {
      c.deleteMovieFromMovieLibrary(movies.getMovieByIndex(i));
    }
    addTestLibraryToController();
  }

  @BeforeEach
  public void reset() {
  }

  @Test
  public void testSearchResults() {

    String text = "batman";
    clickOn("#inputName").write(text);
    Assertions.assertEquals("batman", inputName.getText());
    Assertions.assertEquals(20, searchBox.getItems().size());
    Assertions.assertEquals("Batman (1989)", searchBox.getItems().get(0));
    Assertions.assertEquals("The Batman (2022)", searchBox.getItems().get(2));
    clickOn(searchBox);
    Assertions.assertEquals("The Batman was loaded from Tmdb", searchBoxErrorText.getText());
    Assertions.assertEquals("414906", inputMovieId.getText());
    Assertions.assertEquals("The Batman (2022)", searchBox.getSelectionModel().getSelectedItem());
  }

  @Test
  public void testController() {
    assertNotNull(this.c);
  }

  @Test
  public void testMovieLibrary() {
    MovieLibrary movieLibrary = this.c.getLibrary();
    assertNotNull(movieLibrary);
  }

  // @Test
  // public void testListView() throws Exception {

  // int numberOfItems = listView.getItems().size();

  // listView.scrollTo(numberOfItems);
  // Assertions.assertEquals("King Lines", listView.getItems().get(numberOfItems -
  // 5));
  // Assertions.assertEquals("test movie 3", listView.getItems().get(numberOfItems
  // - 3));
  // Assertions.assertEquals("test movie 5", listView.getItems().get(numberOfItems
  // - 1));
  // clickOn(listView);
  // Assertions.assertEquals("King Lines", movieTitle.getText());
  // Assertions.assertEquals("Adventure, Documentary", movieGenres.getText());

  // }

  @Test
  public void testEditReview() throws Exception {

    int numberOfItems = listView.getItems().size();
    System.out.println(numberOfItems);
    listView.scrollTo(numberOfItems);
    clickOn(listView);
    Assertions.assertEquals("N/A", labelRating.getText());
    Assertions.assertEquals("No review added.", labelComment.getText());
    clickOn(editReviewButton);
    clickOn(inputRating).write("10");
    clickOn(editReviewButton);
    Assertions.assertEquals("Comment cannot be empty.", errorMessage.getText());
    clickOn(inputComment).write("Good");
    clickOn(editReviewButton);
    Assertions.assertEquals("10/10", labelRating.getText());
    Assertions.assertEquals("Good", labelComment.getText());
    clickOn(editReviewButton);
    clickOn(inputRating);
    clickOn(inputRating).write("11");
    clickOn(editReviewButton);
    Assertions.assertEquals("Rating must be between 1-10.", errorMessage.getText());
    clickOn(editReviewButton);
    clickOn(inputRating);
    clickOn(inputRating).write("word");
    clickOn(editReviewButton);
    Assertions.assertEquals("Rating must be between 1-10.", errorMessage.getText());
    clickOn(inputRating);
    clickOn(inputRating).write("7");
    clickOn(editReviewButton);
    Assertions.assertEquals("7/10", labelRating.getText());
    Assertions.assertEquals("Good", labelComment.getText());

  }

  // @Test
  // public void testNewMovieButton() throws Exception {

  // clickOn(listView);
  // Assertions.assertTrue(c.getShowMovieInfoPane());
  // clickOn(newMovieButton);
  // Assertions.assertFalse(c.getShowMovieInfoPane());
  // }

  // @Test
  // public void testAddMovieButton() throws Exception {
  // Assertions.assertFalse(c.getShowMovieInfoPane());
  // clickOn("#inputName").write("batman");
  // clickOn(searchBox);
  // clickOn(addMovieButton);
  // Assertions.assertTrue(c.getShowMovieInfoPane());
  // }

  // @Test
  // public void testDeleteButton() throws Exception {

  // int numberOfItemsBeforeDelete = listView.getItems().size();
  // resetTestMovies();

  // listView.scrollTo(numberOfItemsBeforeDelete);
  // clickOn(listView);
  // Assertions.assertTrue(c.getShowMovieInfoPane());
  // int numberOfItemsAfterDelete = listView.getItems().size();
  // System.out.println(numberOfItemsBeforeDelete + " " +
  // numberOfItemsAfterDelete);
  // clickOn(deleteMovieButton);
  // Assertions.assertFalse(c.getShowMovieInfoPane());
  // Assertions.assertFalse(c.getShowMovieInfoPane());
  // int numberOfItemsAfterDelete = listView.getItems().size();
  // Assertions.assertEquals(numberOfItemsBeforeDelete - 1,
  // numberOfItemsAfterDelete);
  // resetTestMovies();
  // }

}
