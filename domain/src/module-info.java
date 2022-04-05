module com.darkyver.domain {
    requires transitive java.sql;
    exports com.darkyver.domain.entity;
    exports com.darkyver.domain.exceptions;
    exports com.darkyver.domain.port;


    opens com.darkyver.domain.entity to google.cloud.firestore;
}