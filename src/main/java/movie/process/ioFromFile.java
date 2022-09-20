package movie.process;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class ioFromFile {
    public static List<Movie> inputMovies(String INPUT_FILE_NAME) throws Exception
    {
        BufferedReader getData = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        List<Movie> Array_of_Data = new ArrayList();
        while (true) {
            String line = getData.readLine();
            if (line == null) break;
            String[] Each_Movie_Data = line.split("<>");
            Movie Movie_ob = new Movie(Each_Movie_Data);
            Array_of_Data.add(Movie_ob);
        }
        getData.close();
        return Array_of_Data;
    }
    public static void inputPassword(String INPUT_FILE_NAME, HashMap<String, ProductionCompany> prCompanies) throws IOException {
        BufferedReader getData = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        while (true) {
            String line = getData.readLine();
            if (line == null) break;
            String[] Each = line.split("<>");
            prCompanies.get(Each[0]).setPassword(Each[1]);
        }
        getData.close();
    }
    public static void outputMovies(List<Movie> movies, String OUTPUT_FILE_NAME) throws Exception
    {
        BufferedWriter setData = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        for(Movie m: movies)
        {
            setData.write(m.getTitle()+"<>");
            setData.write(String.valueOf(m.getYear_of_Release())+"<>");
            setData.write(m.getGenre1()+"<>");
            setData.write(m.getGenre2()+"<>");
            setData.write(m.getGenre3()+"<>");
            setData.write(String.valueOf(m.getRunning_Time())+"<>");
            setData.write(m.getProduction_Company()+"<>");
            setData.write(String.valueOf(m.getBudget())+"<>");
            setData.write(String.valueOf(m.getRevenue()));
            setData.write(System.lineSeparator());
        }
        setData.close();
    }
    public static void outputPassword(List<String> prCompanies, HashMap<String, ProductionCompany> Passwords, String OUTPUT_FILE_NAME) throws IOException {
        BufferedWriter setData = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        for(String m: prCompanies)
        {
            setData.write(m+"<>");
            setData.write(String.valueOf(Passwords.get(m).getPassword()));
            setData.write(System.lineSeparator());
        }
        setData.close();
    }
    public static List<Movie> MapToList(List<String> prCompany, HashMap<String, ProductionCompany> prMovies)
    {
        List<Movie> Movies = new ArrayList<>();
        for(String prC : prCompany) Movies.addAll(prMovies.get(prC).getMovieList());
        return Movies;
    }
    public static HashMap<String, ProductionCompany> ListToMap(List<String> prNames, List<Movie> Movies)
    {
        HashMap<String, ProductionCompany> prMovies = new HashMap<>();
        for(String company : prNames) {
            List<Movie> movies = new ArrayList<>();
            ProductionCompany prCompany = new ProductionCompany(company, movies);
            prMovies.put(company, prCompany);
        }
        for(Movie m : Movies) prMovies.get(m.getProduction_Company()).getMovieList().add(m);
        return prMovies;
    }

    public static void inputLink(String INPUT_FILE_NAME, List<Movie> allMovieList, int type) throws IOException {
        BufferedReader getData = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        int i=0;
        while (true) {
            String line = getData.readLine();
            if (line == null) break;
            if(type==0) allMovieList.get(i++).setImageLink(line);
            else if(type==1) {
                allMovieList.get(i++).setTrailerLink(line);
            }
        }
        getData.close();
    }
    public static void outputLink(String OUTPUT_FILE_NAME, List<Movie> Movies, int type) throws IOException {
        BufferedWriter setData = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        for(Movie m: Movies)
        {
            if(type==0) setData.write(m.getImageLink());
            else if(type==1) {
                setData.write(m.getTrailerLink());
            }
            setData.write(System.lineSeparator());
        }
        setData.close();
    }
}
