package main.process;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import main.networking.MainApplication;

public class EditCompanyController {
    public TextField ImageLink;
    public PasswordField oldPassword;
    public PasswordField newPassword;
    public MainApplication application;
    public EditPageController controller;

    public void onImConfirm(ActionEvent actionEvent) {
        if(!ImageLink.getText().equals("")) {
            application.productionCompany.setLogo(ImageLink.getText());
            application.socketWrapper.write(application.productionCompany);
            controller.PrImage.setImage(new Image(ImageLink.getText()));
        } else {
            application.showWarning("INPUT Error!!", "TextField is Empty");
        }
        ImageLink.setText("");
    }

    public void onImReset(ActionEvent actionEvent) {
        ImageLink.setText("");
    }

    public void onPassConfirm(ActionEvent actionEvent) {
        if(application.productionCompany.getPassword().equals(oldPassword.getText())) {
            if(newPassword.getText().length()>=4) {
                application.productionCompany.setPassword(newPassword.getText());
                application.socketWrapper.write(application.productionCompany);
            } else {
                application.showError("PASSWORD Error!!", "New Password must contains at least 4 characters");
            }
        } else {
            application.showError("PASSWORD Error!!", "Old password did not match");
        }
        oldPassword.setText("");
        newPassword.setText("");
    }

    public void onPassReset(ActionEvent actionEvent) {
        oldPassword.setText("");
        newPassword.setText("");
    }

    public void initiate(MainApplication application, EditPageController controller) {
        this.application = application;
        this.controller = controller;
    }
}
