package movie.process;

import java.util.List;

public class showMovies {
    public static void printMovie(Movie m)
    {
        System.out.print("Title: "+ m.getTitle() + "    Year of Release: " + m.getYear_of_Release() + "    Genre: " + m.getGenre1());
        if(!m.getGenre2().equalsIgnoreCase("")) System.out.print(", " + m.getGenre2());
        if(!m.getGenre3().equalsIgnoreCase("")) System.out.print(", " + m.getGenre3());
        System.out.println("\nRunning Time: " + m.getRunning_Time() + " minutes" + "    Production Company : " +
                m.getProduction_Company() + "    Budget: " + m.getBudget() + "    Revenue: " + m.getRevenue() + "\n");
    }
    public static void printMovies(List<Movie> movies)
    {
        for (Movie m: movies) printMovie(m);
    }
    public static void prMoviesYear(String year, List<Movie> movies)
    {
        for(Movie m: movies)
        {
            if(year.equals(m.getYear_of_Release())) printMovie(m);
        }
    }
    public static void prMoviesRevenue(String revenue, List<Movie> movies)
    {
        for(Movie m: movies)
        {
            if(revenue.equals(m.getRevenue())) printMovie(m);
        }
    }
    public static void noPrCompany()
    {
        System.out.println("  No such production company with this name.");
    }
    public static void prMovieShow(List<String> Companies, List<Movie> Movie_List)
    {
        SearchMovies search = new SearchMovies(Movie_List);
        for(String Comp: Companies) System.out.println("Company Name: " + Comp + ", Number of Movies: " + search.searchByProduction(Comp).size());
    }
}
