# UI

### What is UI?

- Restapi acts as an end-point for remote the storage.
- It allows a data access class to do GET, PUT, POST, and DELETE calls to a specific URL, in order to receive or send data related to the movie library.
- The restapi uses its own instance of MoviePersistence to store the information locally.
- Based on the choice of local or remote controller, either a local or remote data access class is used for storage.

## Classes and their uses

- App.java starts the application.
- AbstractAppController.java handles all UI elements, user input, navigation, sorting, and communicates with the data access class.
- LocalAppController.java extends AbstractAppController and sets the data access class to local.
- RemoteAppController.java extends AbstractAppController and sets the data access class to remote.
- utils/Librarydata access.java is an interface for the local and remote data access classes, to ensure all functions are implemented.
- utils/LibraryLocaldataAccess.java uses the MoviePersistence class to store the movie library information locally.
- utils/LibraryRemotedataAccess.java communicates with the restapi server with GET, PUT, POST, and DELETE fetch calls.
