package com.breadbin.moviedb_trumps.model.api;

import com.breadbin.moviedb_trumps.model.ActorResults;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface ActorsService {

  @GET("/3/person/popular")
  Observable<ActorResults> getActorResults(
      @Query("api_key") String apiKey);
}
