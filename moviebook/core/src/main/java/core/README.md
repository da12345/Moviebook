# Information

### What is core?

Core is the heart of Moviebook - here lies the core logic behind movies, movie libraries, and reviews. It also contains the classes to handle the Tmdb API.

# Classes and their uses

- **Movie.java**: represents a movie with the movie information.
- **MovieInformation.java**: independent class to serve as a mid-step between the controller and the movie search class.
- **MovieLibrary.java**: a collection of movies, and handles basic adding, removing, sorting, etc of movies.
- **MovieSearch.java** gets movie information using TMDB-API.
- **Review.java** - holds the review information and is used inside the movie class.
