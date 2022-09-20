package main.process;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.networking.MainApplication;
import movie.process.Movie;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URISyntaxException;

public class MovieDetailsController {
    public Text titleLabel;
    public Label yearLabel;
    public Label genreLabel;
    public Label productionLabel;
    public Label timeLabel;
    public Label budgetLabel;
    public Label revenueLabel;
    public ImageView image;
    public WebView trailer;
    public Button button;
    public Button button2;
    private MainApplication application;
    private Stage stage;
    private Movie movie;

    public void setController(MainApplication application, Stage newStage) {
        this.application = application;
        this.stage = newStage;
    }

    public void initiate(Movie movie) throws IOException {
        this.movie = movie;
        titleLabel.setText(movie.getTitle());
        yearLabel.setText(movie.getYear_of_Release());
        genreLabel.setText(movie.getGenre1() + ", " + movie.getGenre2() + ", " + movie.getGenre3());
        productionLabel.setText(movie.getProduction_Company());
        timeLabel.setText(movie.getRunning_Time());
        budgetLabel.setText(movie.getBudget());
        revenueLabel.setText(movie.getRevenue());
    }

    public void onWatch(ActionEvent actionEvent) {
        button.setVisible(false);
        if(movie.getTrailerLink()==null||movie.getTrailerLink().equals("")) {
            trailer.getEngine().load("https://www.youtube.com/embed/utntGgcsZWI");
        }
        else {
            trailer.getEngine().load(movie.getTrailerLink());
        }
        stage.setOnCloseRequest(event -> {
            trailer.getEngine().load(null);
            stage.close();
        });
    }

    public void onLoad(ActionEvent actionEvent) throws IOException {
        button2.setVisible(false);
        Image im = null;
        if(movie.getImageLink()==null||movie.getImageLink().equals("")){
            System.out.println("Img nai");
            im = new Image(MainApplication.class.getResource("movieposter.jpg").openStream());
        }
        else im = new Image(movie.getImageLink());
        image.setImage(im);
    }
}