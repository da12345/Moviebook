package core;

/**
 * Represents a review.
 */

public class Review {
  private String comment;
  private int rating;
  private int reviewId;

  /**
   * Constructor for Review-object.
   */
  public Review(String comment, int rating) {
    setComment(comment);
    setRating(rating);
  }

  private void setComment(String comment) {
    if (comment != null && !comment.equals("")) {
      this.comment = comment;
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Function to check the validity and set the rating of a review-object. It is
   * public in order to facilitate changing of rating from the ui.
   */
  public void setRating(int rating) {
    if (rating <= 10 && rating > 0) {
      this.rating = rating;
    } else {
      throw new IllegalArgumentException();
    }
  }

  public void setReviewId(int reviewId) {
    this.reviewId = reviewId;
  }

  public String getComment() {
    return comment;
  }

  public int getRating() {
    return rating;
  }

  public int getReviewId() {
    return reviewId;
  }

}
