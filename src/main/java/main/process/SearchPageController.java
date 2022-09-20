package main.process;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import main.networking.MainApplication;
import movie.process.Movie;
import movie.process.ProductionManage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchPageController {
    public Pane PaneTitle;
    public StackPane TempPane;
    public AnchorPane searchPane;
    public MainApplication application;
    public Text prName;

    public void onBack(ActionEvent actionEvent) throws IOException {
        application.HomePage();
    }

    public void show(List<Movie> Movies) throws IOException {
        if(Movies==null) {
            Movies=new ArrayList<>();
        }
        application.showMovies(searchPane, Movies, 0);
    }

    public void onSearchTitle(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(application.getClass().getResource("TitlePane.fxml"));
        Parent panes = loader.load();
        TitlePane pane = loader.getController();
        pane.setPane(this, application.productionCompany);
        TempPane.getChildren().removeAll();
        TempPane.getChildren().setAll(panes);
        show(null);

    }

    public void onSearchYear(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(application.getClass().getResource("YearPane.fxml"));
        Parent panes = loader.load();
        YearPane pane = loader.getController();
        pane.setPane(this, application.productionCompany);
        TempPane.getChildren().removeAll();
        TempPane.getChildren().setAll(panes);
        show(null);

    }

    public void onSearchGenre(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(application.getClass().getResource("GenrePane.fxml"));
        Parent panes = loader.load();
        GenrePane pane = loader.getController();
        pane.setPane(this, application.productionCompany);
        TempPane.getChildren().removeAll();
        TempPane.getChildren().setAll(panes);
        show(null);

    }

    public void onSearchTime(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(application.getClass().getResource("TimePane.fxml"));
        Parent panes = loader.load();
        TimePane pane = loader.getController();
        pane.setPane(this, application.productionCompany);
        TempPane.getChildren().removeAll();
        TempPane.getChildren().setAll(panes);
        show(null);

    }

    public void onMostRecent(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(application.getClass().getResource("TempPane.fxml"));
        Parent panes = loader.load();
        TempPane pane = loader.getController();
        pane.setPane(this, application.productionCompany, false);
        TempPane.getChildren().removeAll();
        TempPane.getChildren().setAll(panes);
    }

    public void onMaxRevenue(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(application.getClass().getResource("TempPane.fxml"));
        Parent panes = loader.load();
        TempPane pane = loader.getController();
        pane.setPane(this, application.productionCompany, true);
        TempPane.getChildren().removeAll();
        TempPane.getChildren().setAll(panes);
    }

    public void initiate(MainApplication application) throws IOException {
        prName.setText(application.productionCompany.getCompanyName());
        System.out.println(application.getClass().getResource("TitlePane.fxml"));
        this.application = application;
        try {
            FXMLLoader loader = new FXMLLoader(application.getClass().getResource("TitlePane.fxml"));
            Pane panes = loader.load();
            TitlePane pane = loader.getController();
            pane.setPane(this, application.productionCompany);
            TempPane.getChildren().removeAll();
            TempPane.getChildren().setAll(panes);
            show(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
