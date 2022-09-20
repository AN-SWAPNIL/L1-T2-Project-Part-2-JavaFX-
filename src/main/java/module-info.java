module MyJavaFX {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.web;

    opens main.process to javafx.fxml;
    exports main.process;
    exports movie.process;
    exports main.networking;
    opens main.networking to javafx.fxml;
    exports main.transferClass;
    opens main.transferClass to javafx.fxml;
}