package com.breadbin.moviedb_trumps.model;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface GenreService {

  @GET("/3/genre/movie/list")
  Observable<Genres> getGenres(@Query("api_key") String apiKey);
}
