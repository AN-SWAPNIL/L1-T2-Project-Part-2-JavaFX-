package main.process;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.networking.MainApplication;

import java.io.IOException;
import java.util.ArrayList;

public class LoginPageController {
    public ImageView image;
    @FXML
    private PasswordField Password;
    @FXML
    private MenuButton Company_Name;
    @FXML
    private MainApplication application;
    public void initiate(MainApplication application) throws IOException {
        this.application = application;
        for (var any : application.Companies){
            var item = new MenuItem(any);
            item.setOnAction(event -> Company_Name.setText(any));
            Company_Name.getItems().add(item);
        }
    }

    public void onResetButton(ActionEvent event) {
        Company_Name.setText("Company Name");
        Password.setText("");
    }

    public void onLoginButton(ActionEvent actionEvent) throws IOException {
        String name = Company_Name.getText();
        String password = Password.getText();
        if(application.Companies.contains(name)) {
            application.connectToServer(name, password);
        }
        else application.showWarning("LOGIN Unsuccessful!!", "Select a Production Company");
    }
}
