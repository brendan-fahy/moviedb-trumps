package com.breadbin.moviedb_trumps.model.api;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface ConfigurationService {

  @GET("/3/configuration")
  Observable<Configuration> getConfiguration(@Query("api_key") String apiKey);
}
