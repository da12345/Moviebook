# Release 3

For release 3, an extensive ammount of work has been put into the different modules. We have implemented funcitonality for retreiving movie info from themoviedb.org, using a library called themoviedbapi. We have updated some of the main saved fields of our movies, and updated our persistence file in line with this. We have also implemented REST api, adding support for remote persistence. For our 4 modules, we have continued testing the code, with decent to complete coverage most places.

## core

- For our Movie objects, we have removed info such as genre as this gets loaded from the web. Rating has been moved into our new class "Review", where another attribude is "comment", which gives the user the option of adding a review to a movie they have added to the database.
- MovieSearch and MovieInformation are the main two classes added to support themoviedbapi library that we have integrated into the project. MovieSearch allows the user to have a list of movies returned based on a search query/string. The class also allows the user to get the ID of one of these movies returned, which is important for persistence as the ID for the database is unique for each movie.
- The MovieInformation class is a final class, which uses a movieDB object which is found using the ID, and has four fields that are taken from TMDBs database. Considering the terms of receiving an API key from TMDB, none of the information from the database is stored locally, except for the movie ID and movie title.
- MovieLibrary has been updated to account for all of these core changes, and adds different methods of adding and removing movies. We have also updated our sort function with an ENUM to more easily allow for different sorting types.

## persistence

- Persistence has in simple terms been updated to also allow saving of the user Review. As mentioned, the unique movie ID is also saved to file.

## restapi

- A new package called restapi has been created to act as an end-point for remote storage.
- This allows the DataAccess class to do GET, PUT, POST, and DELETE calls to a specific URL, in order to receive or send data related to the movie library.
- The restapi uses its own instance of MoviePersistence to store the information locally.

## ui

- Our ui has undergone larger changes to account for changes elsewhere in the project. The interface has been updated to show information loaded from TMDB, such as a movie poster, movie genres and release date of the movie. The UI also has input for changing movie review, sorting the movies, or deleting a movie.
- The controller class is split into three parts. We have the Abstract controller which controls most of the function of the controller, and then we have one local controller class and one remote controller class, each of which extends from the abstract controller. These two either connects to their respective classes in ui.utils, which si the LibraryLocalDataAccess or LibraryRemoteDataAccess, which has the main function of using local storage, or remote storage using rest

## Testing
- For the core and persistence classes, we have reached 100% code coverage for our testing according to jacoco. The controller also has a decent code coverage, however multiple of our tests in controller has issues which we were not prepared to face for this release. The same can be said for the tests we wrote for restAPI, which is more documented in the respective [readme](moviebook/restapi/README.md) file found. 

## Documentation and project structure
- We have updated many of the readmes in the project repository, and also added multiple new ones to describe the different parts of the project in more detail. We have also restructured the docs folder to allow for easier navigation
- In docs/diagrams, we have added all the different diagrams for the project which we found to be necessary. Our architecture diagram has been updated from the previous release, and class diagrams for core and ui has been added. In addition we have two sequence diagram, relating to our user story. The diagrams are shown and described in docs/diagrams/readme.md 

## Workflow
- For this part in the project, we have become more concerned with using the different functions of gitlab. 
- We have more consequently started checking each others merge requests out, and approving them before merging to master.
- On our issues page, we have started using the comments section in greater detail, to give updates on the issues, suggestions for solutions, questions about further implementation etc.
- Although not perfect, we have in greater degree started relating commits to our issues, which simplifies seeing the purpose of the commit and what the changes in code are. 

## Other 
- We have changed gitpod.yml to create an easier step by step guide to launch our app. Where before you had to clean install yourself, run spring boot yourself, and then open the app, we have changed it so that it automatically runs a clean install, and then boots spring-boot for running the app on the rest server. 
