package main.process;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.networking.MainApplication;
import movie.process.ProductionManage;

import java.io.IOException;

public class TransferPageController {
    public ImageView PrImage;
    public AnchorPane tempPane;
    public Text textField;
    public Text totalMovies;
    public Text totalProfit;
    public MenuButton prCompanyBox;
    public Text CompanyName;
    private MainApplication application;

    public void onConfirm(ActionEvent actionEvent) {
        if(application.Companies.contains(prCompanyBox.getText())) {
            application.toCompany = prCompanyBox.getText();
            textField.setText("Transfer Movie to " + application.toCompany);
        }
        else {
            application.showWarning("SELECTION Warning!!", "Select a Production Company");
            textField.setText("No Company has been selected");
        }
        prCompanyBox.setText("Company Name");
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        application.toCompany = null;
        application.HomePage();
    }

    public void initiate(MainApplication application) throws IOException {
        application.totalProfit = totalProfit;
        application.totalMovies = totalMovies;
        application.currentListType = 0;
        this.application = application;
        application.transferPageController = this;
        Image image = null;
        if(application.productionCompany.getLogo()==null||application.productionCompany.getLogo().equals("")) {
            image = new Image(MainApplication.class.getResource("prcompany.png").openStream());
        }
        PrImage.setImage(image);
        for (var any : application.Companies){
            if(any.equals(application.productionCompany.getCompanyName())) continue;
            var item = new MenuItem(any);
            item.setOnAction(event -> prCompanyBox.setText(any));
            prCompanyBox.getItems().add(item);
        }
        CompanyName.setText(application.productionCompany.getCompanyName());
        textField.setText("No Company has been selected");
        ProductionManage prManage = new ProductionManage(application.productionCompany.getMovieList());
        totalMovies.setText("Total Movies :     " + application.productionCompany.getMovieList().size());
        totalProfit.setText("Total Profit :     $" + prManage.totalProfit(application.productionCompany.getMovieList()));
        application.showMovies(tempPane, application.productionCompany.getMovieList(), 2);
    }

    public void onReset(ActionEvent actionEvent) {
        application.toCompany = null;
        prCompanyBox.setText("Company Name");
        textField.setText("No Company has been selected");
    }
}
