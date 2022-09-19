package ui;

import core.Movie;
import core.Review;
import core.MovieInformation;
import core.MovieLibrary;
import core.MovieLibrary.SortType;
import core.MovieSearch;
import info.movito.themoviedbapi.model.MovieDb;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ui.utils.LibraryDataAccess;

/**
 * Controller class.
 */
public abstract class AbstractAppController {

  @FXML
  private GridPane mainGrid;

  @FXML
  private Pane addMoviePane;

  @FXML
  private Pane movieInfoPane;

  @FXML
  private ListView<String> listView;

  @FXML
  private Button addMovieButton;

  @FXML
  private Button newMovieButton;

  @FXML
  private Button editReviewButton;

  @FXML
  private Button deleteMovieButton;

  @FXML
  private Label sortLabel;

  @FXML
  private Button sortButtonAlpha;

  @FXML
  private Button sortButtonNum;

  @FXML
  private TextField inputName;

  @FXML
  private TextField inputRating;

  @FXML
  private TextField inputComment;

  @FXML
  private Label errorMessage;

  @FXML
  private ListView<String> searchBox;

  @FXML
  private Label searchBoxErrorText;

  @FXML
  private ImageView moviePoster;

  @FXML
  private Label movieTitle;

  @FXML
  private Label movieGenres;

  @FXML
  private Label movieReleaseDate;

  @FXML
  private TextField inputMovieId;

  @FXML
  private Label labelRating;

  @FXML
  private Label labelComment;
  private Boolean showMovieInfoPane = false;
  private Boolean isTesting = false;

  private MovieLibrary library = new MovieLibrary();
  private MovieSearch onlineDatabase = new MovieSearch();
  private String selectedMovieTitleSearch;
  private String selectedMovieIdSearch;
  private Movie selectedMovie;
  protected LibraryDataAccess dataAccess;
  private Boolean isReviewEditable = false;
  // Static grid pane width info
  private static final double movieInfoWidth = 460;
  private static final double movieAddWidth = 460;
  List<MovieDb> searchResultMovies;

  @FXML
  protected void initialize() {

    addMovieButton.setStyle("-fx-background-color: #00964D; ");
    deleteMovieButton.setStyle("-fx-background-color: #D30023; ");
    editReviewButton.setStyle("-fx-background-color: #142DBC; ");
    moviePoster.setStyle("-fx-background-color: #142DBC; ");
    setUpStorage();
    resetUi();
    errorMessage.setTextFill(Color.RED);
    inputMovieId.setEditable(false);
    newMovieButton.setDisable(true);
    addMovieButton.setDisable(true);
    try {
      updateMovieLibrary();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Initializes storing and reading from file. //
   */
  protected abstract void setUpStorage();

  private void updateMovieLibrary() {
    this.library = dataAccess.getMovieLibrary();
    updateListView();
  }

  private void updateListView() {
    listView.getItems().setAll(this.library.getMovieTitles());
  }

  public MovieLibrary getLibrary() {
    List<Movie> newlist = new ArrayList<Movie>();
    for (Movie m : library.getMovies()) {
      newlist.add(m);
    }
    MovieLibrary newlibrary = new MovieLibrary();
    for (int i = 0; i < newlist.size(); i++) {
      try {
        newlibrary.addMovie(newlist.get(i));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return newlibrary;
  }

  /**
   * Handles adding of a movie when the add button is clicked.
   */
  @FXML
  private void onClickAddMovie() {
    if (onlineDatabase.searchResult(inputName.getText()).size() > 0) {
      addMovieButton.setDisable(false);
      try {
        Movie movie = new Movie(selectedMovieTitleSearch, selectedMovieIdSearch);
        Boolean movieWasAdded = dataAccess.addMovie(movie);
        if (movieWasAdded == false) {
          errorMessage.setText("Movie has already been added!");
        } else {
          swapPanes();
          updateMovieLibrary();
          errorMessage.setText("");
          addMovieButton.setDisable(true);
          sortButtonAlpha.setText("A-Z");
          sortButtonNum.setText("1-10");
        }

        listView.getSelectionModel().selectLast();
      } catch (Exception e) {
        errorMessage.setText("Input error!");
      }
    }
    updateMovieInfo();
  }

  int index;
  Movie movietobechanged;

  /**
   * Allows for editing of the movie rating when button pressed.
   */
  @FXML
  private void handleEditReview() throws Exception {

    if (editReviewButton.getText().equals("Edit review")) {
      String oldRating = labelRating.getText().split("/")[0];
      String oldComment = labelComment.getText();
      if (oldRating.equals("N") && oldComment.equals("No review added.")) {
        oldRating = "";
        oldComment = "";
      }
      inputRating.setText(oldRating);
      inputComment.setText(oldComment);
      toggleReviewEditable();
      inputRating.requestFocus();
      inputRating.setPromptText(oldRating);

    } else {

      String comment = inputComment.getText();
      String rating = inputRating.getText();
      if (isRatingValid(rating)) {
        if (!comment.equals("")) {
          errorMessage.setText("");
          Review review = new Review(comment, Integer.parseInt(rating));
          String movieId = selectedMovie.getMovieId();

          toggleReviewEditable();

          dataAccess.addReviewToMovie(movieId, review);
          updateMovieLibrary();
          labelRating.setText(review.getRating() + "/10");
          labelComment.setText(review.getComment() + "");
          inputRating.setText("");
          inputComment.setText("");

          listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex());
        } else {
          errorMessage.setText("Comment cannot be empty.");
        }
      } else {
        inputRating.requestFocus();
        errorMessage.setText("Rating must be between 1-10.");
      }
    }
  }

  private Boolean isRatingValid(String rating) {
    try {
      int inputRating = Integer.parseInt(rating);
      if (inputRating > 0 && inputRating <= 10) {
        return true;
      }
    } catch (Exception e) {
    }
    return false;
  }

  private void toggleReviewEditable() {
    toggleReviewEditable(!isReviewEditable);
  }

  private void toggleReviewEditable(Boolean visible) {
    if (editReviewButton.getText().equals("Edit review")) {
      editReviewButton.setText("Update");
    } else {
      editReviewButton.setText("Edit review");
    }

    inputRating.setVisible(visible);
    inputComment.setVisible(visible);

    labelRating.setVisible(!visible);
    labelComment.setVisible(!visible);
    isReviewEditable = !isReviewEditable;

  }

  /**
   * Deletes selected movie.
   */
  @FXML
  private void handleDeleteMovie() {
    String movieId = selectedMovie.getMovieId();
    dataAccess.deleteMovie(movieId);
    if (!isTesting) {
      updateMovieLibrary();
    }

    resetUi();
    sortButtonAlpha.setFocusTraversable(false);
    // sortButtonNum.setFocusTraversable(false); // DENNE LAGER EN ERROR
    listView.getSelectionModel().clearSelection();

    swapPanes();
  }

  /**
   * Sets UI for new movie.
   */
  @FXML
  private void onClickNewMovie() {
    swapPanes();
    resetUi();
    clearSearchBox();
    inputName.requestFocus();
    listView.getSelectionModel().clearSelection();
  }

  /**
   * Updates UI when list or list item clicked.
   */
  @FXML
  private void onClickListView() {

    errorMessage.setText("");
    if (showMovieInfoPane == false) {
      swapPanes();
    }

    addMovieButton.setDisable(true);
    if (!addMoviePane.isVisible()) {
      newMovieButton.setDisable(false);
    }
    if (isReviewEditable == true) {
      toggleReviewEditable();
    }
    if (library.getMoviesSize() > 0 && listView.getSelectionModel().getSelectedIndex() >= 0) {

      try {
        updateMovieInfo();
      } catch (Exception e) {
        errorMessage.setText("Error getting movie info");
      }
    }
  }

  /**
   * Sets the info on the movieInfo pane such as user rating, and more info from
   * the database. The method uses the TMDB movie ID. Every movie in the database
   * has a unique ID which is just an integer of some length.
   * 
   */
  private void updateMovieInfo() {

    Movie selectedMovie = library.getMovieByIndex(listView.getSelectionModel().getSelectedIndex());
    this.selectedMovie = selectedMovie;
    Review review = selectedMovie.getReview();
    if (review == null) {
      labelRating.setText("N/A");
      labelComment.setText("No review added.");
    } else {
      labelRating.setText(review.getRating() + "/10");
      labelComment.setText(review.getComment() + "");
    }

    MovieInformation movieIMDB = new MovieInformation(onlineDatabase.getMovieById(selectedMovie.getMovieId()));
    movieTitle.setText(movieIMDB.getMovieTitle());
    movieGenres.setText(movieIMDB.getMovieGenres());
    movieReleaseDate.setText(movieIMDB.getMovieRelease());
    String posterUrl = movieIMDB.getMovieImagePath();
    Image posterImage = new Image("https://image.tmdb.org/t/p/original/" + posterUrl);
    moviePoster.setImage(posterImage);
  }

  /**
   * Sorts list alphabetically by title.
   */
  @FXML
  private void handleTitleSort() {
    try {
      if (sortButtonAlpha.getText().equals("A-Z")) {
        this.library = library.getSorted(library, false, SortType.TITLE);
        sortButtonAlpha.setText("Z-A");
      } else {
        sortButtonAlpha.setText("A-Z");
        this.library = library.getSorted(library, true, SortType.TITLE);
      }
    } catch (Exception e) {
      errorMessage.setText(e.getMessage());
    }
    updateListView();
  }

  /**
   * Sorts list numerically by rating.
   */
  @FXML
  private void handleRatingSort() {
    try {
      if (sortButtonNum.getText().equals("1-10")) {
        this.library = library.getSorted(library, false, SortType.RATING);
        sortButtonNum.setText("10-1");
      } else {
        sortButtonNum.setText("1-10");
        this.library = library.getSorted(library, true, SortType.RATING);
      }
    } catch (Exception e) {
      errorMessage.setText(e.getMessage());
    }
    updateListView();
  }

  /**
   * Returns copy of the current library. Used for testing in AppTest.
   */

  private void resetUi() {
    addMovieButton.setDisable(true);
    inputName.clear();
    // inputRating.clear();
    inputMovieId.clear();
    clearSearchBox();
    inputRating.setVisible(false);
    inputComment.setVisible(false);
    // setFieldsEditable(true);
    errorMessage.setText("");
    inputMovieId.clear();
  }

  /**
   * This is activated when a user clicks on a movie name in the search box.
   * Updates the inputName to the name from the TMDB database, and loads the
   * database ID, which is used to get info about the film.
   */
  @FXML
  private void selectSearchResult() {
    addMovieButton.setDisable(false);
    try {
      int selectedIndex = searchBox.getSelectionModel().getSelectedIndex();
      String movieTitle = searchResultMovies.get(selectedIndex).getTitle();
      String movieId = String.valueOf(searchResultMovies.get(selectedIndex).getId());

      selectedMovieTitleSearch = movieTitle;
      selectedMovieIdSearch = movieId;
      inputMovieId.setText(movieId + "");
      searchBoxErrorText.setText(movieTitle + " was loaded from Tmdb");
      // inputName.setText(movieTitle);

    } catch (Exception e) {
      searchBoxErrorText.setText("No movie selected");
    }
  }

  /**
   * Retreives a new list of searches from TMDB based on user input in the
   * inputName field. Updates the searchBox listview, where the user can see the
   * titles of the movies resulted from the search query.
   */
  @FXML
  private void updateSearchBox() {
    clearSearchBox();
    if (!inputName.getText().equals("")) {
      try {
        searchResultMovies = onlineDatabase.searchResult(inputName.getText());
        for (int i = 0; i < searchResultMovies.size(); i++) {
          String movieName = searchResultMovies.get(i).getTitle();
          String movieYear = searchResultMovies.get(i).getReleaseDate().substring(0, 4);
          String movieText = movieName + " (" + movieYear + ")";
          searchBox.getItems().add(movieText);
        }
      } catch (Exception e) {
        if (searchBox.getItems().size() == 0) {
          searchBoxErrorText.setText("No movies found!");
        }
      }
    }
  }

  /**
   * Empties the search box containing results, and also clears the label under
   * the search box.
   */
  private void clearSearchBox() {
    searchBox.getItems().clear();
    searchBoxErrorText.setText("");
  }

  /**
   * Toggle function to either show the pane with movie info or the pane for
   * searching for new movies.
   */
  private void swapPanes() {
    movieInfoPane.setVisible(!showMovieInfoPane);
    addMoviePane.setVisible(showMovieInfoPane);
    if (showMovieInfoPane) {
      mainGrid.getColumnConstraints().get(1).setMaxWidth(movieAddWidth);
      mainGrid.getColumnConstraints().get(2).setMaxWidth(0);
      newMovieButton.setDisable(true);
    } else {
      mainGrid.getColumnConstraints().get(1).setMaxWidth(0);
      mainGrid.getColumnConstraints().get(2).setMaxWidth(movieInfoWidth);
      newMovieButton.setDisable(false);
    }
    showMovieInfoPane = !showMovieInfoPane;
  }

  /**
   * Used for testing purposes.
   */
  public void addMovieToMovieLibrary(Movie movie) throws Exception {
    this.library.addMovie(movie);
    updateListView();
  }

  /**
   * Used for testing purposes.
   */
  public void deleteMovieFromMovieLibrary(Movie movie) throws Exception {
    this.library.deleteMovieByTitle(movie.getTitle());
    updateListView();
  }

  /**
   * Used for testing purposes.
   */
  public Boolean getShowMovieInfoPane() {
    return this.showMovieInfoPane;
  }

  /**
   * Used for testing purposes.
   */
  public void setIsTesting(Boolean isTesting) {
    this.isTesting = isTesting;
  }

}