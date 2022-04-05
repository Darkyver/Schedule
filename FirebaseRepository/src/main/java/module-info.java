module com.darkyver.firebase {
    requires com.darkyver.usecases;

    requires com.google.auth;
    requires firebase.admin;
    requires google.cloud.firestore;
    requires google.cloud.core;
    requires com.google.api.apicommon;
    requires com.google.auth.oauth2;

    exports com.darkyver.firebase.service;
    exports com.darkyver.firebase;

}