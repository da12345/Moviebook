[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-908a85?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2168/gr2168)
# IT1901 - group 2168

This project was built during the autumn of 2021 for the course [IT1901](https://www.ntnu.no/studier/emner/IT1901) at Norwegian University of Science and Technology. **Moviebook** is a [JavaFX](https://openjfx.io/) application that allows the user to build up their own unique library of movies that is either stored locally or on a remote server.

In the repository there exists also configuration files for running the project in Gitpod and various files for project setup.

## Running the application in Gitpod:

1. Click on the Gitpod-tag at the top of this file to be directed to the IDE.
2. Gitpod will take some time to build everything, so wait for that to complete at first.
3. Run the app using `mvn javafx:run -f ui/pom.xml` on the available console on the right side. You can also type `cd ui` and then `mvn javafx:run`, for the same effect.

The application is Gitpod-certified and allows for running on a virtual machine using the Gitpod service. 

## Running the application for local or remote use:

As we haven't implemented profiles in our pom at this time, the `fx:controller` field in App.fxml needs to be set to either `ui.LocalAppController` or `ui.RestAppController` for running locally or on the rest server. Running on rest should be default

1. Make sure your terminal is in moviebook, and run `mvn clean install` to build everything to begin with.
2. If you are running the app on rest server, you need to either type in `mvn spring-boot:run -f restapi/pom.xml` from moviebook, or do `cd restapi` and then run it by typing `mvn spring-boot:run`
3. Run the app using `mvn javafx:run -f ui/pom.xml` from moviebook. You can also type `cd ui` and then `mvn javafx:run`, for the same effect.

# External plugins

- [Jackson library](https://github.com/FasterXML/jackson) is used for implementing persistence in the application and is based on using JSON-format.
- [Junit5](https://junit.org/junit5/) is used as a testing framework
- [Jacoco](https://www.eclemma.org/jacoco/) is used for certifying the test coverage
- [Checkstyle](https://checkstyle.sourceforge.io/) and [Spotbugs](https://spotbugs.github.io/) are to maintain good code quality- and practice
- [ThemoviedbApi](https://github.com/holgerbrandl/themoviedbapi) is used to retrieve movie information and search up movies on [TMDB](https://www.themoviedb.org/)

# Issues faced

- Approaching the end of the project, one of the goals was making a shippable version of the application. A central problem against this was another library that we used for the project. The moviedb api didn't include a module-info, which essentially means that maven creates an "unnamed" one itself. Using a library like this was in hindsight not advisable for applications that are to be released to the public. Jlink will not work using unnamed modules, and we were advised in this case to prioritize functionality as the themoviedbapi was central to moviebook. An alternative was adapting the named library to work with the modular project, however themoviedbapi had multiple dependencies with unnamed modules, which complicated matters greatly. 
