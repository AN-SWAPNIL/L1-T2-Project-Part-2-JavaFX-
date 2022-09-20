package main.process;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.networking.MainApplication;
import movie.process.Movie;
import movie.process.ProductionManage;

import java.io.IOException;

public class EditMovieController {
    public TextField ImageLink;
    public TextField TrailerLink;
    public Label titleLabel;
    private Movie movie;
    private MainApplication application;
    private Stage stage;

    public void setMovie() {
        for(int i=0; i<application.productionCompany.MovieList.size(); i++) {
            if(application.productionCompany.MovieList.get(i).getTitle().equals(movie.getTitle())) {
                application.productionCompany.MovieList.add(i, movie);
                application.socketWrapper.write(application.productionCompany);
                break;
            }
        }
    }
    public void onConfirm(ActionEvent actionEvent) {
        String str1 = ImageLink.getText();
        String str2 = TrailerLink.getText();
        if(!(str1.equals("")||str2.equals(""))) {
            movie.setImageLink(str1);
            movie.setTrailerLink(str2);
            setMovie();
        } else if(!str1.equals("")) {
            movie.setImageLink(str1);
            setMovie();
        } else if (!str2.equals("")) {
            movie.setTrailerLink(str2);
            setMovie();
        }
        else application.showWarning("INPUT Error!!", "TextField is Empty");
        ImageLink.setText("");
        TrailerLink.setText("");
    }

    public void initiate(Movie movie, MainApplication application, Stage stage) {
        this.application = application;
        this.movie = movie;
        this.stage = stage;
        titleLabel.setText("# Edit Movie.. " + movie.getTitle());
    }

    public void onDelete(ActionEvent actionEvent) throws IOException {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("DELETE Confirmation!!");
        a.setContentText("Are you Sure to delete the movie?");
        if(a.showAndWait().get()== ButtonType.OK) {
            System.out.println("Deleting movie "+ movie.getTitle());
            for(int i=0;i<application.productionCompany.MovieList.size();i++) {
                if(application.productionCompany.MovieList.get(i).getTitle().equalsIgnoreCase(movie.getTitle())) {
                    application.productionCompany.MovieList.remove(i);
                    break;
                }
            }
            application.socketWrapper.write(application.productionCompany);
            application.showMovies(application.currentPane, application.productionCompany.getMovieList(), application.currentType);
            application.totalMovies.setText("Total Movies :     " + application.productionCompany.MovieList.size());
            application.totalProfit.setText("Total Profit :     $" + new ProductionManage(application.productionCompany.MovieList).totalProfit(application.productionCompany.MovieList));
            stage.close();
        }
    }
}
