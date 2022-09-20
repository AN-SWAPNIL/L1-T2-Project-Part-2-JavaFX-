package main.process;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.networking.MainApplication;
import movie.process.Movie;
import movie.process.ProductionManage;

import java.io.IOException;
import java.util.List;

public class ListViewController {
    public ListView<VBox> listView;
    private MainApplication application;

    public void setMain(MainApplication application) {
        this.application = application;
    }

    public void initiate(AnchorPane pane, List<Movie> movies, int pageType) throws IOException {
        application.listView = this;
        application.currentType = pageType;
        application.currentPane = pane;
        for(Movie m : movies){
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("MiniMovie.fxml"));
            loader.load();
            MiniMovieController miniMovieController = loader.getController();
            miniMovieController.setMain(application);
            listView.getItems().add(miniMovieController.initiate(m, pageType));
        }
    }
}
