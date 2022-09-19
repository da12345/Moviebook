package persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import core.Movie;
import core.MovieLibrary;
import core.Review;

public class MoviePersistenceTest {

    Movie movie1;
    Movie movie2;
    Movie movie3;
    MovieLibrary library;

    private static MoviePersistence fileSupport = new MoviePersistence();

    private void handleSaveToFile(MovieLibrary library) throws Exception {
        fileSupport.saveToFile(library);
    }

    private MovieLibrary handleLoadFromFile() throws Exception {
        return fileSupport.loadFromFile();
    }

    private boolean checkLibrariesAlike(MovieLibrary library1, MovieLibrary library2) {

        if (library1.getMoviesSize() == library2.getMoviesSize()) {
            for (int i = 0; i < library1.getMoviesSize(); i++) {
                Movie m1 = library1.getMovieByIndex(i);
                Movie m2 = library2.getMovieByIndex(i);
                if (!m1.getTitle().equals(m2.getTitle()) || !m1.getMovieId().equals(m2.getMovieId())) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Test
    public void testSaveToFile() throws Exception {
        MovieLibrary backup = null;
        try {
            backup = handleLoadFromFile();
        } catch (FileNotFoundException e) {
        }
        movie1 = new Movie("movie 1", "111");
        movie2 = new Movie("movie 2", "111");
        movie3 = new Movie("movie 3", "111");
        library = new MovieLibrary();
        Review review = new Review("comment", 5);
        movie2.setReview(review);
        library.addMovie(movie1);
        library.addMovie(movie2);

        handleSaveToFile(library);

        assertEquals(library.getMovieByIndex(0).getTitle(), handleLoadFromFile().getMovieByIndex(0).getTitle());

        MovieLibrary library2 = handleLoadFromFile();
        Assertions.assertTrue(checkLibrariesAlike(library2, library));
        assertEquals(library2.getMovieByIndex(1).getReview().getComment(),
                library.getMovieByIndex(1).getReview().getComment());
        
        
        if (backup != null) {
           handleSaveToFile(backup);
        } else {
            handleSaveToFile(new MovieLibrary());
        }
    }

}
