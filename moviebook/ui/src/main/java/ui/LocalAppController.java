package ui;

import ui.utils.LibraryLocalDataAccess;

public class LocalAppController extends AbstractAppController {

  /**
   * Sets up local storage.
   */
  @Override
  protected void setUpStorage() {
    dataAccess = new LibraryLocalDataAccess();
  }
}
