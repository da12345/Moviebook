# UI Module

The UI module is the module responsible for the frontend of moviebook.

## Information

The UI module is a central module of the project, as it communicates with all others. The User interface of moviebook allows the user to input searches for movies, adding movies, and saving their collection of movies to file. The UI also allows the user to delete movies, sort movies, add a review to a movie they've added.

The UI module also decides whether to use storage through restapi and Spring Boot, or storing the file locally on the end users computer. This is decided in App.fxml.

# Tests

- We have disabled most tests due to unstable conditions.
- Most tests work only sometimes, despite no other factors changing.
- This is likely due to async problems, as we are using a web-based API for receiving the movie information.
