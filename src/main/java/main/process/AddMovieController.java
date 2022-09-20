package main.process;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.networking.MainApplication;
import movie.process.Movie;
import movie.process.ProductionCompany;
import movie.process.SearchMovies;

import java.io.IOException;

public class AddMovieController {
    private MainApplication application;
    public Pane addMovie;
    public TextField Title;
    public TextField Year;
    public TextField Genre1;
    public TextField Genre2;
    public TextField Genre3;
    public TextField Time;
    public TextField Budget;
    public TextField Revenue;
    public TextField ImageLink;
    public TextField TrailerLink;
    private EditPageController controller;

    public void onAddButton(ActionEvent actionEvent) throws IOException {
        SearchMovies search = new SearchMovies(application.productionCompany.getMovieList());
        if(Title.getText().equals("")||Year.getText().equals("")||Genre1.getText().equals("")||Time.getText().equals("")||Budget.getText().equals("")||Revenue.getText().equals("")){
            application.showError("ADDITION Error!!", "Fill the \"*\" signed textField");
            System.out.println("Error, Input is not correct");
        } else if(new SearchMovies(application.productionCompany.MovieList).searchByTitle(Title.getText())!=null) {
            application.showError("ADDITION Error!!", "The Company already has the movie");
            System.out.println("Movie of same Title");
        } else {
            try {
                if (search.searchByTitle(Title.getText()) == null) {
                    Movie movie = new Movie();
                    movie.setTitle(Title.getText());
                    movie.setProduction_Company(application.productionCompany.getCompanyName());
                    Long.parseLong(Year.getText());
                    movie.setYear_of_Release(Year.getText());
                    movie.setGenre1(Genre1.getText());
                    movie.setGenre2(Genre2.getText());
                    movie.setGenre3(Genre3.getText());
                    Long.parseLong(Time.getText());
                    movie.setRunning_Time(Time.getText());
                    Long.parseLong(Budget.getText());
                    movie.setBudget(Budget.getText());
                    Long.parseLong(Revenue.getText());
                    movie.setRevenue(Revenue.getText());
                    movie.setProfit();
                    movie.setImageLink(ImageLink.getText());
                    movie.setTrailerLink(TrailerLink.getText());
                    application.productionCompany.MovieList.add(movie);
                    application.socketWrapper.write(application.productionCompany);
                    reset();
                }
            } catch (Exception e) {
                application.showError("ADDITION Error!!", "Time, Year, Budget, Revenue must be integer");
                e.printStackTrace();
            }
        }
    }

    public void reset() throws IOException {
        addMovie.getChildren().clear();
        controller.initiate(application);
    }
    public void onResetButton(ActionEvent actionEvent) throws IOException {
        reset();
    }

    public void initiate(MainApplication application, EditPageController controller) {
        this.application = application;
        this.controller = controller;
    }
}
