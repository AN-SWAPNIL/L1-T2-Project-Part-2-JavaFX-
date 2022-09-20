package main.process;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import movie.process.Movie;
import movie.process.ProductionCompany;
import movie.process.SearchMovies;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class TitlePane {
    public TextField textField;
    public SearchPageController controller;
    public ProductionCompany productionCompany;

    public void setPane(SearchPageController controller, ProductionCompany productionCompany) {
        this.productionCompany = productionCompany;
        this.controller = controller;
    }
    public void onSearch(ActionEvent actionEvent) throws IOException {
        String title = textField.getText();
        controller.application.currentListType = 4;
        controller.application.contains = title;
        if(title.equals("")) {
            controller.application.showWarning("INPUT Incorrect!!", "TextField is Empty");
            controller.show(null);
        }
        else {
            controller.show(new SearchMovies(controller.application.productionCompany.MovieList).searchByTitles(title));
        }
    }
}
