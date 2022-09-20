package main.process;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import movie.process.Movie;
import movie.process.ProductionCompany;
import movie.process.SearchMovies;

import java.io.IOException;
import java.security.ProtectionDomain;
import java.util.List;

public class TimePane {
    public TextField textField1;
    public TextField textField2;
    public SearchPageController controller;
    public ProductionCompany productionCompany;
    public void setPane(SearchPageController controller, ProductionCompany productionCompany) {
        this.productionCompany = productionCompany;
        this.controller = controller;
    }
    public void onSearch(ActionEvent actionEvent) throws IOException {
        controller.application.currentListType = 6;
        try {
            long time1 = Long.parseLong(textField1.getText());
            long time2 = Long.parseLong(textField2.getText());
            if(time1<time2) {
                controller.application.contains = time1 + "," + time2;
                controller.show(new SearchMovies(productionCompany.getMovieList()).searchByRunningTime(time1, time2));
            }
            else {
                controller.application.showError("INPUT Incorrect!!", "Input lower time in first textField");
                controller.show(null);
            }
        } catch (NumberFormatException e) {
            controller.application.showError("INPUT Incorrect!!", "Input integer time correctly");
            controller.show(null);
            e.printStackTrace();
        }
    }
}
