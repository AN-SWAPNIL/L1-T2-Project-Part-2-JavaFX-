package main.process;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.networking.MainApplication;
import movie.process.*;

import java.io.IOException;
import java.util.List;

public class HomePageController {
    public ImageView PrImage;
    public AnchorPane mainPane;
    public Text textField;
    public Text totalMovies;
    public Text totalProfit;
    private MainApplication application;
    public void initiate(MainApplication application) throws IOException {
        application.totalProfit = totalProfit;
        application.totalMovies = totalMovies;
        this.application = application;
        application.mainPane = mainPane;
        Image image = null;
        if (application.productionCompany.getLogo() == null)
            image = new Image(MainApplication.class.getResource("prcompany.png").openStream());
        PrImage.setImage(image);
        textField.setText("All Movies of " + application.productionCompany.getCompanyName());

        ProductionManage prManage = new ProductionManage(application.productionCompany.getMovieList());
        application.showMovies(mainPane, application.productionCompany.getMovieList(), 0);
        totalMovies.setText("Total Movies :     " + String.valueOf(application.productionCompany.getMovieList().size()));
        totalProfit.setText("Total Profit :     $" + prManage.totalProfit(application.productionCompany.getMovieList()));
    }

    public void allMovies(ActionEvent actionEvent) throws IOException {
        application.currentListType = 0;
        textField.setText("All Movies of " + application.productionCompany.getCompanyName());
        application.showMovies(mainPane, application.productionCompany.MovieList, 0);
    }

    public void topMovies(ActionEvent actionEvent) throws IOException {
        application.currentListType = 1;
        textField.setText("Top Movies of " + application.productionCompany.getCompanyName());
        application.showMovies(mainPane, new SearchMovies(application.productionCompany.getMovieList()).searchByProfit(), 0);
    }

    public void transferMovies(ActionEvent actionEvent) {
        application.TransferPage();
    }

    public void onLogOut(ActionEvent actionEvent) throws IOException {
        application.logout(1);
    }

    public void editDatabase(ActionEvent actionEvent) {
        application.EditPage();
    }

    public void searchMovies(ActionEvent actionEvent) throws IOException {
        application.SearchPage();
    }

}