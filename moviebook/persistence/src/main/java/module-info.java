module moviebook.persistence {
    requires transitive com.fasterxml.jackson.databind;

    requires transitive moviebook.core;

    exports persistence;
}
