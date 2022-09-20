package movie.process;

import java.io.Serializable;
import java.util.List;

public class ProductionCompany implements Serializable {
    private String CompanyName;
    public List<Movie> MovieList;
    private String logo;
    private String Password;
    public ProductionCompany(){}
    public ProductionCompany(String name, List<Movie> Movies){
        CompanyName = name;
        MovieList = Movies;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public List<Movie> getMovieList() { return MovieList; }

    public void setMovieList(List<Movie> movieList) {
        MovieList = movieList;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
