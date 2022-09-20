package main.process;

import javafx.scene.text.Text;
import javafx.util.Pair;
import movie.process.Movie;
import movie.process.ProductionCompany;
import movie.process.ProductionManage;
import movie.process.SearchMovies;

import java.io.IOException;
import java.util.List;

public class TempPane {
    public Text showText;
    public Text titleText;
    public Text CounterText;

    public void setPane(SearchPageController controller, ProductionCompany prCompany, boolean type) throws IOException {
        controller.application.tempPane = this;
        controller.application.CounterText = CounterText;
        ProductionManage prSearch = new ProductionManage(prCompany.MovieList);
        SearchMovies search = new SearchMovies(prCompany.MovieList);
        if(type){
            controller.application.currentListType = 2;
            String str = prSearch.maxRevenue(prCompany.MovieList);
            titleText.setText("Search Movies with Maximum Revenue");
            showText.setText("Maximum Revenue :");
            CounterText.setText("$"+str);
            controller.show(prSearch.maxRevenueMovies(str));
        }
        else {
            controller.application.currentListType = 3;
            String str = prSearch.mostRecent(prCompany.MovieList);
            titleText.setText("Search Most Recent Movies");
            showText.setText("Latest Release Year :");
            CounterText.setText(str);
            controller.show(prSearch.mostRecentMovies(str));
        }
    }
}
