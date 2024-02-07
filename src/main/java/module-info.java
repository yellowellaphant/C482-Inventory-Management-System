module c482.c482_t1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens c482.main to javafx.fxml;
    opens model to javafx.base;
    exports c482.main;
    exports controller;
    exports model;
    opens controller to javafx.fxml;
}