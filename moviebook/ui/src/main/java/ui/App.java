package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * App class.
 */
public class App extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    Parent parent = FXMLLoader.load(getClass().getResource("App.fxml"));
    stage.setTitle("MovieBook");
    stage.setScene(new Scene(parent));
    stage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }
}
