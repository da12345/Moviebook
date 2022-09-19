# Restapi

### What is restapi?

- Restapi acts as an end-point for remote the storage.
- This allows a dataaccess class to do GET, PUT, POST, and DELETE calls to a specific URL, in order to receive or send data related to the movie library.
- The restapi uses its own instance of MoviePersistence to store the information locally.

## Classes and their uses

- APIApplication starts the spring boot server.
- APIController handles all incoming fetch calls and the remote storage.
