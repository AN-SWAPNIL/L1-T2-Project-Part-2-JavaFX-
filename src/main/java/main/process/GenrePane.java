package main.process;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import movie.process.Movie;
import movie.process.ProductionCompany;
import movie.process.SearchMovies;

import java.io.IOException;
import java.util.List;

public class GenrePane {
    public TextField textField;
    public SearchPageController controller;
    public ProductionCompany productionCompany;
    public void setPane(SearchPageController controller, ProductionCompany productionCompany) {
        this.productionCompany = productionCompany;
        this.controller = controller;
    }
    public void onSearch(ActionEvent actionEvent) throws IOException {
        String genre = textField.getText();
        controller.application.currentListType = 7;
        controller.application.contains = genre;
        if(genre.equals("")) {
            controller.application.showWarning("INPUT Incorrect!!", "TextField is Empty");
            controller.show(null);
        }
        else {
            controller.show(new SearchMovies(productionCompany.getMovieList()).searchByGenres(genre));
        }
    }
}
