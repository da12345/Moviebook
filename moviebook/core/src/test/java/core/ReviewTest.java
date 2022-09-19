package core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;


public class ReviewTest {
    Review review1;
    Review review2;

    @BeforeEach
    public void setup() throws Exception {
        review1 = new Review("bad", 7);
        review2 = new Review("review text", 3);
    }
    
    @Test
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            review1 = new Review("", 5);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            review1 = new Review("review", 52);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            review1 = new Review(null, 5);
        });
        
    }

    @Test
    public void testSetRating() {
        assertThrows(IllegalArgumentException.class, () -> {
            review1.setRating(-1);
        });
        review2.setRating(2);
        assertEquals(2, review2.getRating());
        assertThrows(IllegalArgumentException.class, () -> {
            review1.setRating(11);
        });
        assertEquals(2, review2.getRating());
        assertEquals(7 , review1.getRating());
    }

    @Test
    public void testSetMovieID() {
        review1.setReviewId(5);
        assertEquals(5, review1.getReviewId());
    }
}
