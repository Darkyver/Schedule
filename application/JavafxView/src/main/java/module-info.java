module com.darkyver.javafxview {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.darkyver.usecases;
    requires com.darkyver.domain;
    requires com.darkyver.firebase;
    requires config;


    opens com.darkyver.javafxview to javafx.fxml;
    exports com.darkyver.javafxview;
    exports com.darkyver.javafxview.controllersView;
    opens com.darkyver.javafxview.controllersView to javafx.fxml;
    exports com.darkyver.javafxview.controllersView.accountingView;
    opens com.darkyver.javafxview.controllersView.accountingView to javafx.fxml;
    exports com.darkyver.javafxview.controllersView.createUserView;
    opens com.darkyver.javafxview.controllersView.createUserView to javafx.fxml;
    exports com.darkyver.javafxview.controllersView.userInfo;
    opens com.darkyver.javafxview.controllersView.userInfo to javafx.fxml;
    exports com.darkyver.javafxview.controllersView.accountingView.elements;
    opens com.darkyver.javafxview.controllersView.accountingView.elements to javafx.fxml;
}