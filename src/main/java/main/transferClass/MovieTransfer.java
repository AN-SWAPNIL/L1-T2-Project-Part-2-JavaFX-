package main.transferClass;

import movie.process.Movie;
import java.io.Serializable;

public class MovieTransfer implements Serializable {
    private String from;
    private String to;
    private Movie data;

    public MovieTransfer(String from, String to, Movie movie) {
        this.from = from;
        this.to = to;
        this.data = movie;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Movie getData() {
        return data;
    }

    public void setData(Movie t) {
        this.data = t;
    }
}