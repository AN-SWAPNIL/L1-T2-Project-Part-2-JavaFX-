package movie.process;

import main.process.SearchPageController;

import java.util.ArrayList;
import java.util.List;

public class SearchMovies {
    private List<Movie> Movie_List;
    public SearchMovies(List<Movie> list)
    {
        Movie_List=list;
    }
    public Movie searchByTitle(String title)
    {
        for(Movie m: Movie_List)
        {
            if(title.equalsIgnoreCase(m.getTitle())) return m;
        }
        return null;
    }
    public List<Movie> searchByTitles(String title)
    {
        title = title.toLowerCase();
        List<Movie> Movies = null;
        boolean is = false;
        for(Movie m: Movie_List)
        {
            String t = m.getTitle().toLowerCase();
            if(t.contains(title)) {
                if(!is) {
                    Movies = new ArrayList<>();
                    is = true;
                }
                Movies.add(m);
            }
        }
        return Movies;
    }
    public List<Movie> searchByReleaseYear(String year)
    {
        List<Movie> movies = null;
        boolean found = false;
        for(Movie m: Movie_List)
        {
            if(year.equals(m.getYear_of_Release()))
            {
                if(!found)
                {
                    found = true;
                    movies = new ArrayList();
                }
                movies.add(m);
            }
        }
        return movies;
    }
    public List<Movie> searchByGenre(String genre)
    {
        List<Movie> movies = null;
        boolean found = false;
        for(Movie m: Movie_List)
        {
            if(genre.equalsIgnoreCase(m.getGenre1())||genre.equalsIgnoreCase(m.getGenre2())||genre.equalsIgnoreCase(m.getGenre3()))
            {
                if(!found)
                {
                    found = true;
                    movies = new ArrayList();
                }
                movies.add(m);
            }
        }
        return movies;
    }
    public List<Movie> searchByGenres(String genre)
    {
        List<Movie> movies = null;
        boolean found = false;
        genre = genre.toLowerCase();
        for(Movie m: Movie_List)
        {
            String t1 = m.getGenre1().toLowerCase();
            String t2 = m.getGenre2().toLowerCase();
            String t3 = m.getGenre3().toLowerCase();
            if(t1.contains(genre)||t2.contains(genre)||t3.contains(genre))
            {
                if(!found)
                {
                    found = true;
                    movies = new ArrayList();
                }
                movies.add(m);
            }
        }
        return movies;
    }
    public List<Movie> searchByProduction(String pr)
    {
        List<Movie> movies = null;
        boolean found = false;
        for(Movie m: Movie_List)
        {
            if(pr.equalsIgnoreCase(m.getProduction_Company()))
            {
                if(!found)
                {
                    found = true;
                    movies = new ArrayList();
                }
                movies.add(m);
            }
        }
        return movies;
    }
    public List<Movie> searchByRunningTime(long i, long j)
    {
        List<Movie> movies = null;
        boolean found = false;
        for(Movie m: Movie_List)
        {
            long t = Long.parseLong(m.getRunning_Time());
            if(i<=t && j>=t)
            {
                if(!found)
                {
                    found = true;
                    movies = new ArrayList();
                }
                movies.add(m);
            }
        }
        return movies;
    }
    public List<Movie> searchByProfit()
    {
        int topPosition[] = new int[10];
        long topProfit[] = new long[10];
        for(int i=0; i<Movie_List.size(); i++)
        {
            long profit = Long.parseLong(Movie_List.get(i).getRevenue())- Long.parseLong(Movie_List.get(i).getBudget());
            for(int j=0; j<10; j++)
            {
                if(topProfit[j]<profit)
                {
                    for(int k=9; k>j; k--)
                    {
                        topProfit[k] = topProfit[k-1];
                        topPosition[k] = topPosition[k-1];
                    }
                    topProfit[j] = profit;
                    topPosition[j] = i;
                    break;
                }
            }
        }
        int min;
        if(Movie_List.size()<10) min = Movie_List.size();
        else min = 10;
        List<Movie> movies = new ArrayList();
        for(int i=0; i<min; i++) movies.add(Movie_List.get(topPosition[i]));
        return movies;
    }
}
