package movie.process;

import java.util.ArrayList;
import java.util.List;

public class ProductionManage {
    List<Movie> Movie_List;
    public ProductionManage(List<Movie> movies)
    {
        Movie_List = movies;
    }
    public String mostRecent(List<Movie> prMovies)
    {
        long last=0;
        for(Movie m: prMovies)
        {
            if(Long.parseLong(m.getYear_of_Release())>last) last = Long.parseLong(m.getYear_of_Release());
        }
        return String.valueOf(last);
    }
    public List<Movie> mostRecentMovies(String year)
    {
        List<Movie> Movies = new ArrayList<>();
        for(Movie m : Movie_List)
        {
            if(m.getYear_of_Release().equals(year)) Movies.add(m);
        }
        return Movies;
    }
    public String maxRevenue(List<Movie> prMovies)
    {
        long max=0;
        for(Movie m: prMovies)
        {
            if(Long.parseLong(m.getRevenue())>max) max = Long.parseLong(m.getRevenue());
        }
        return String.valueOf(max);
    }
    public List<Movie> maxRevenueMovies(String revenue)
    {
        List<Movie> Movies = new ArrayList<>();
        for(Movie m : Movie_List)
        {
            if(m.getRevenue().equals(revenue)) Movies.add(m);
        }
        return Movies;
    }
    public String totalProfit(List<Movie> prMovies)
    {
        long profit=0;
        for(Movie m: prMovies)
        {
            profit += Long.parseLong(m.getRevenue()) - Long.parseLong(m.getBudget());
        }
        return String.valueOf(profit);
    }
    public List<String> listOfCompany()
    {
        List<String> Companies = new ArrayList();
        for(Movie m: Movie_List) {
            if(!Companies.contains(m.getProduction_Company())) Companies.add(m.getProduction_Company());
        }
        return Companies;
    }
}
