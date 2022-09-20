package main.process;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Pair;
import main.networking.MainApplication;
import movie.process.ProductionManage;

import java.io.IOException;

public class EditPageController {
    public ImageView PrImage;
    public Text textField;
    public Text totalMovies;
    public Text totalProfit;
    public AnchorPane TempPane;
    public Label prName;
    private MainApplication application;

    public void editMovies(ActionEvent actionEvent) throws IOException {
        application.currentListType = 0;
        application.totalMovies = totalMovies;
        application.totalProfit = totalProfit;
        textField.setText("Edit Movies of " + application.productionCompany.getCompanyName());
        application.showMovies(TempPane, application.productionCompany.getMovieList(), 1);
        ProductionManage prManage = new ProductionManage(application.productionCompany.getMovieList());
        totalMovies.setText("Total Movies :     " + application.productionCompany.getMovieList().size());
        totalProfit.setText("Total Profit :     $" + prManage.totalProfit(application.productionCompany.getMovieList()));
    }

    public void addMovies(ActionEvent actionEvent) throws IOException {
        textField.setText("Add Movies to " + application.productionCompany.getCompanyName());
        totalMovies.setText("Must Fill \"*\" signed textbox...");
        totalProfit.setText("");
        FXMLLoader loader = new FXMLLoader(application.getClass().getResource("AddMoviePane.fxml"));
        Pane panes = loader.load();
        AddMovieController pane = loader.getController();
        pane.initiate(application, this);
        TempPane.getChildren().removeAll();
        TempPane.getChildren().setAll(panes);
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        application.HomePage();
    }

    public void editCompany(ActionEvent actionEvent) throws IOException {
        textField.setText("Edit Data of " + application.productionCompany.getCompanyName());
        totalMovies.setText("Must Fill \"*\" signed textbox...");
        totalProfit.setText("");
        FXMLLoader loader = new FXMLLoader(application.getClass().getResource("EditComPane.fxml"));
        Pane panes = loader.load();
        EditCompanyController pane = loader.getController();
        pane.initiate(application, this);
        TempPane.getChildren().removeAll();
        TempPane.getChildren().setAll(panes);
    }

    public void initiate(MainApplication application) throws IOException {
        this.application = application;
        application.editPageController = this;
        prName.setText(application.productionCompany.getCompanyName());
        Image image = null;
        if(application.productionCompany.getLogo()==null||application.productionCompany.getLogo().equals("")) {
            image = new Image(MainApplication.class.getResource("prcompany.png").openStream());
        }
        PrImage.setImage(image);
        textField.setText("Add Movies to " + application.productionCompany.getCompanyName());
        totalMovies.setText("Must Fill \"*\" signed textbox...");
        totalProfit.setText("");
        try {
            FXMLLoader loader = new FXMLLoader(application.getClass().getResource("AddMoviePane.fxml"));
            Pane panes = loader.load();
            AddMovieController pane = loader.getController();
            pane.initiate(application, this);
            TempPane.getChildren().removeAll();
            TempPane.getChildren().setAll(panes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
