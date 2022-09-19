# Diagrams

Here are all of our diagrams created during the project, describing different parts of the project. This includes showing relations between classes, relations between packages and importantly a sequence diagram based on the user story of the application.

# Architecture

Our package diagram depicts all the different packages within our 4 main modules, and the most important project dependencies.

![Architechture img](moviebook/docs/diagrams/architecture.png)

# Classes

We ended up with two different diagrams, with one for ui and one for core. Persistence and restapi have a simple class structure, and there are not any big relations to understand in these two modules. 

## Core classes

![Core classes img](moviebook/docs/diagrams/classes_core.png)

## Ui classes

![Ui classes img](moviebook/docs/diagrams/classes_ui.png)


# Sequence diagram

We have two different sequence diagrams as the structure is a little bit different going from the remote app to the local app.

## Diagram for adding a movie using remote storage

![localsequencediagram img](moviebook/docs/diagrams/LocalAddSequenceDiagram.png)

## Diagram for adding a movie using local storage

![remotesequencediagram img](moviebook/docs/diagrams/RemoteAddSequenceDiagram.png)
