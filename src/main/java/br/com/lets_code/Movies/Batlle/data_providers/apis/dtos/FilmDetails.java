package br.com.lets_code.Movies.Batlle.data_providers.apis.dtos;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.lets_code.Movies.Batlle.core.entities.Film;

public class FilmDetails {
    @JsonProperty("Title") 
    public String title;
    @JsonProperty("Year") 
    public String year;
    @JsonProperty("Rated") 
    public String rated;
    @JsonProperty("Released") 
    public String released;
    @JsonProperty("Runtime") 
    public String runtime;
    @JsonProperty("Genre") 
    public String genre;
    @JsonProperty("Director") 
    public String director;
    @JsonProperty("Writer") 
    public String writer;
    @JsonProperty("Actors") 
    public String actors;
    @JsonProperty("Plot") 
    public String plot;
    @JsonProperty("Language") 
    public String language;
    @JsonProperty("Country") 
    public String country;
    @JsonProperty("Awards") 
    public String awards;
    @JsonProperty("Poster") 
    public String poster;
    @JsonProperty("Ratings") 
    public ArrayList<Rating> ratings;
    @JsonProperty("Metascore") 
    public String metascore;
    public String imdbRating;
    public String imdbVotes;
    public String imdbID;
    @JsonProperty("Type") 
    public String type;
    @JsonProperty("DVD") 
    public String dVD;
    @JsonProperty("BoxOffice") 
    public String boxOffice;
    @JsonProperty("Production") 
    public String production;
    @JsonProperty("Website") 
    public String website;
    @JsonProperty("Response") 
    public String response;

    public Film convertToFilm() {
        return new Film(this.imdbID, this.title, this.year, this.type, this.poster, this.imdbRating, this.imdbVotes);
    }
}