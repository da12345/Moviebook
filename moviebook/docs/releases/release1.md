# Release 1

For our first release we have begun with simple logic classes to prepare a minimal viable product

### Classes

* Movie - Saves information about a single movie - so far we have only implementet title.
* MovieLibrary - As the name suggests, it keeps a list of all movies in your database as the app is running
* MovieDatabase - This saves all movies from MovieLibrary to file, and can also load movies from tile to a new instance of MovieLibrary

When it comes to testing we have made one class so far, testing MovieLibrary to achieve over 0% test coverage.
* MovieLibraryTest - Checks that movies are added correctly, and that duplicate movies are not added.
