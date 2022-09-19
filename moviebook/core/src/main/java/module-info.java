module moviebook.core {
    requires transitive com.fasterxml.jackson.databind;

    requires transitive themoviedbapi;

    exports core;
}