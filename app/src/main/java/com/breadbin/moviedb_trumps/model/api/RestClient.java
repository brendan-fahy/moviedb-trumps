package com.breadbin.moviedb_trumps.model.api;

import com.breadbin.moviedb_trumps.model.ActorResults;
import com.breadbin.moviedb_trumps.model.Genre;
import com.breadbin.moviedb_trumps.model.GenreService;
import com.breadbin.moviedb_trumps.model.Genres;

import java.util.HashMap;
import java.util.Map;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;

public class RestClient {

  private static final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/";
  public static final String MOVIEDB_API_KEY = "7a2fdb0a7c4e001a1941d1dde66e1f87";
  public static final String API_KEY_PARAM_NAME = "api_key";

  private static RestClient instance;

  private ConfigurationService configService;
  private ActorsService actorsService;
  private GenreService genreService;

  public static RestClient getInstance() {
    if (instance == null) {
      instance = new RestClient();
    }
    return instance;
  }

  public RestClient() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(MOVIEDB_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    actorsService = retrofit.create(ActorsService.class);
    genreService = retrofit.create(GenreService.class);
    configService = retrofit.create(ConfigurationService.class);
  }

  public Observable<ActorResults> getActors() {
    return actorsService.getActorResults(MOVIEDB_API_KEY);
  }

  public Observable<Map<Integer, Genre>> getGenres() {
    return genreService.getGenres(MOVIEDB_API_KEY)
        .map(new Func1<Genres, Map<Integer, Genre>>() {
          @Override
          public Map<Integer, Genre> call(Genres genres) {
            Map<Integer, Genre> genresMap = new HashMap<>(genres.getGenres().size());
            for (Genre genre : genres.getGenres()) {
              genresMap.put(genre.getId(), genre);
            }
            return genresMap;
          }
        });
  }

  public Observable<Configuration> getConfig() {
    return configService.getConfiguration(MOVIEDB_API_KEY);
  }

}
