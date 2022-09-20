package main.process;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import movie.process.Movie;
import movie.process.ProductionCompany;
import movie.process.SearchMovies;

import java.io.IOException;
import java.util.List;

public class YearPane {
    public TextField textField;
    public SearchPageController controller;
    public ProductionCompany productionCompany;
    public void setPane(SearchPageController controller, ProductionCompany productionCompany) {
        this.productionCompany = productionCompany;
        this.controller = controller;
    }
    public void onSearch(ActionEvent actionEvent) throws IOException {
        String year = textField.getText();
        controller.application.currentListType = 5;
        controller.application.contains = year;
        try {
            Long.parseLong(year);
            controller.show(new SearchMovies(productionCompany.getMovieList()).searchByReleaseYear(year));
        } catch (NumberFormatException e) {
            controller.application.showError("INPUT Incorrect!!", "Input integer year correctly");
            controller.show(null);
            e.printStackTrace();
        }
    }
}
