module moviebook.restapi {

    requires transitive moviebook.core;
    requires moviebook.persistence;

    requires spring.web;
    requires spring.beans;
    requires spring.boot;
    requires spring.context;
    requires spring.boot.autoconfigure;

    exports restapi;
}