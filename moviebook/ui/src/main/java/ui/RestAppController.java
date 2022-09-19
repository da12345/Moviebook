package ui;

import java.net.URI;
import java.net.URISyntaxException;
import ui.utils.LibraryRemoteDataAccess;

public class RestAppController extends AbstractAppController {

  /**
   * Make URI for endpoint.
   *
   * @return a valid URI for the endpoint
   */
  private URI uriSetup() {
    URI endPointURI = null;
    try {
      endPointURI = new URI("http://localhost:8080");
    } catch (URISyntaxException e) {
      System.out.println(e.getMessage());
    }
    return endPointURI;
  }

  /**
   * Sets up remote storage to server via REST calls.
   */
  @Override
  protected void setUpStorage() {
    dataAccess = new LibraryRemoteDataAccess(uriSetup());
  }
}