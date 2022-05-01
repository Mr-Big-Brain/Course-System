module com.example.hybernatekontrolinis {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires mysql.connector.java;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires javax.persistence;

    requires spring.web;
    requires spring.beans;
    requires spring.context;
    requires com.google.gson;
    //requires java.persistence;

    //requires gson;
    //requires spring.core;

    opens com.example.hybernatekontrolinis to javafx.fxml;
    exports com.example.hybernatekontrolinis;
    opens DataStructures to javafx.fxml,org.hibernate.orm.core,java.persistence;
    exports DataStructures;
    opens com.example.hybernatekontrolinis.HibernateControllers to javafx.fxml,org.hibernate.orm.core,java.persistence;
    exports com.example.hybernatekontrolinis.HibernateControllers;
    opens com.example.hybernatekontrolinis.JavaFxControllers to javafx.fxml,org.hibernate.orm.core,java.persistence;
    exports com.example.hybernatekontrolinis.JavaFxControllers;

    opens com.example.hybernatekontrolinis.WebControllers to javafx.fxml,org.hibernate.orm.core,java.persistence;
    exports com.example.hybernatekontrolinis.WebControllers;

}