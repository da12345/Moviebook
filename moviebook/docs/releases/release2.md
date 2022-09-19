# Release 2
For release two, we have in a greater degree focused on project modularization, and implementing ways of checking code quality.

## Modularization
* The project setup has been changed to support different modules. 
* So far we are using two modules; 'core and 'ui'.

## Architecture 
* We have continued on the core logic classes of the app. 
* The user interface has been updated in line with the core changes.
* Persistence has been changed to use json via the Jackson library. 

## Code quality
* Spotbugs and CheckStyle has been implemented to check for code quality
* Jacoco has been implemented to check for percentage of code covered by tests. 
* Tests has now been implemented in all modules

# Specific changes
* Movie has new attributes - genre and rating.
* MovieLibrary should now be able to remove movies from the database.
* Persistence has been moved to MoviePersistence, which is placed in the 'json' package in the 'core' module.
* MovieDatabase is decrepit.
* The UI controller has been updated to implement everything in line with core.

## Work habits
* We mainly use issues on Gitlab to keep track of tasks and things to do 
* The issues are categorized into different "releases", where a release is a milestone in gitlab. 
* Every milestone matches the group deliverable, and therefore also has the same deadline
* We assign issues to different group members using the Assignemenets function in gitlab
* We split up work into different branches, to then merge it with the master branch. We haven't been using pair programming, mostly because we haven't found good times and places for holding meetings in person. Instead we have been having more meetings over teams, which has worked for the group members. 

## Workflow 
* We haven't consciously been using scrum-methods. Instead we have been focused on having the Issues page updated in line with our current work, and assigning the tasks to the right person at the right time. We've had enough overview with this, as Milestone shows date, and Assignements shows group member. The different tasks were finished nicely using this method. 
