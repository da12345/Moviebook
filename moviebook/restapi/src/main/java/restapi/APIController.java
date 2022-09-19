package restapi;

import core.Movie;
import core.Review;
import core.MovieLibrary;
import persistence.MoviePersistence;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class APIController {

	private MovieLibrary library = new MovieLibrary();
	private MoviePersistence fileSupport = new MoviePersistence();

	public APIController() {
		// Loads movieLibrary from local file
		try {
			MovieLibrary l = fileSupport.loadFromFile();
			library = l;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Returns movieLibrary when GET is called on /movieLibrary
	@GetMapping("/movieLibrary")
	public MovieLibrary getMovieLibrary() {
		System.out.println("Sending movieLibrary...");
		return library;
	}

	// Receives movieName and movieId and creates a new Movie, then adds it to the
	// local MovieLibrary. Then saves to file.
	@PostMapping("/movie")
	public Boolean addMovieToLibrary(@RequestBody String jsonMovie) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(jsonMovie);
			String movieName = node.get("movieName").asText();
			String movieId = node.get("movieId").asText();

			int movieIndex = getIndexFromLibraryWithMovieId(movieId);

			// Only adds movie if library not already contains movie with same id
			if (movieIndex == (-1)) {
				Movie movie = new Movie(movieName, movieId);
				System.out.println("Adding movie: " + movie.getTitle());
				library.addMovie(movie);
				handleSave(library);
				return true;
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	// Receives movieName and movieId and creates a new Movie, then adds it to the
	// local MovieLibrary. Then saves to file.
	@PutMapping("/movie")
	public Boolean editReviewForMovie(@RequestBody String jsonReview) {

		try {

			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(jsonReview);
			String movieId = node.get("movieId").asText();
			String comment = node.get("comment").asText();
			String rating = (node.get("rating").asText());

			Review review = new Review(comment, Integer.parseInt(rating));

			int movieIndex = getIndexFromLibraryWithMovieId(movieId);

			// Only adds review if library contains movie with movieId
			if (movieIndex != (-1)) {

				System.out.println("Adding review with rating: " + rating + " and comment: '" + comment
						+ "' to the movie: " + library.getMovieByIndex(movieIndex).getTitle());
				library.addReviewById(movieId, review);
				handleSave(library);
				return true;
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return false;

	}

	// Deletes movie from movieLibrary based on parameter in URL. Ex:
	// URL: /movie?movieId=12312 will delete movie with movieId = 12312
	// If library does not contain a movie with the movieId, it returns false.
	@DeleteMapping("/movie")
	public boolean deleteMovieById(@RequestParam(value = "movieId", defaultValue = "none") String movieId) {
		try {

			int movieIndex = getIndexFromLibraryWithMovieId(movieId);

			// Only deletes movie if library contains movie with movieId
			if (movieIndex != (-1)) {
				System.out.println("Deleting' movie: " + library.getMovieByIndex(movieIndex).getTitle());
				library.deleteMovieByIndex(movieIndex);
				handleSave(library);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	private int getIndexFromLibraryWithMovieId(String movieId) throws Exception {
		for (int index = 0; index < library.getMoviesSize(); index++) {
			String id = library.getMovieByIndex(index).getMovieId();
			if (movieId.equals(id)) {
				return index;
			}
		}
		return -1;
	}

	private void handleSave(MovieLibrary library) throws Exception {
		fileSupport.saveToFile(library);
	}
}