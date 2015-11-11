package com.breadbin.moviedb_trumps.model;

import android.support.annotation.VisibleForTesting;

import com.breadbin.moviedb_trumps.model.api.Configuration;
import com.breadbin.moviedb_trumps.model.api.RestClient;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class DataSource {

  private RestClient restClient;
  private Configuration config;
  private Map<Integer, Genre> genres;

  private static DataSource instance;

  private DataSource() {
    this.restClient = RestClient.getInstance();
  }

  @VisibleForTesting
  DataSource(RestClient restClient) {
    this.restClient = restClient;
  }

  public static DataSource getInstance() {
    if (instance == null) {
      instance = new DataSource();
    }
    return instance;
  }

  public Observable<ActorResults> getActors() {
    return restClient.getActors();
  }

  public Observable<Configuration> getConfiguration() {
    Observable<Configuration> memoryConfig = Observable.create(new Observable.OnSubscribe<Configuration>() {
      @Override
      public void call(Subscriber<? super Configuration> subscriber) {
          subscriber.onNext(config);
          subscriber.onCompleted();
      }
    });
    Observable<Configuration> networkConfig = restClient.getConfig()
        .doOnNext(new Action1<Configuration>() {
          @Override
          public void call(Configuration configuration) {
            config = configuration;
          }
        });

    return Observable
        .concat(memoryConfig, networkConfig)
        .first(new Func1<Configuration, Boolean>() {
          @Override
          public Boolean call(Configuration configuration) {
            return configuration != null;
          }
        });
  }

  public Observable<Map<Integer, Genre>> getGenres() {
    Observable<Map<Integer, Genre>> memoryGenres = Observable
        .create(new Observable.OnSubscribe<Map<Integer, Genre>>() {
          @Override
          public void call(Subscriber<? super Map<Integer, Genre>> subscriber) {
              subscriber.onNext(genres);
              subscriber.onCompleted();
          }
        });
    Observable<Map<Integer, Genre>> networkGenres = restClient.getGenres()
        .doOnNext(new Action1<Map<Integer, Genre>>() {
          @Override
          public void call(Map<Integer, Genre> integerGenreMap) {
            genres = integerGenreMap;
          }
        });

    return Observable
        .concat(memoryGenres, networkGenres)
        .first(new Func1<Map<Integer, Genre>, Boolean>() {
          @Override
          public Boolean call(Map<Integer, Genre> integerStringMap) {
            return integerStringMap != null;
          }
        });
  }
}
