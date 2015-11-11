package com.breadbin.moviedb_trumps;

import android.util.Log;

import com.breadbin.moviedb_trumps.leaderboards.LeaderboardActivity;
import com.breadbin.moviedb_trumps.model.Actor;
import com.breadbin.moviedb_trumps.model.ActorResults;
import com.breadbin.moviedb_trumps.model.DataSource;
import com.breadbin.moviedb_trumps.model.Genre;
import com.breadbin.moviedb_trumps.model.LeaderboardEntry;
import com.breadbin.moviedb_trumps.model.Movie;
import com.breadbin.moviedb_trumps.model.api.Configuration;
import com.breadbin.moviedb_trumps.views.ActorViewModel;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

public class ActorsPresenter {

  private static final int NO_ACTOR_SELECTED = -1;
  private ActorsView view;
  private DataSource dataSource;
  private ActorViewModelConverter viewModelConverter;
  private Firebase firebase;

  private List<Actor> allActors;
  private int firstActor = NO_ACTOR_SELECTED;
  private int secondActor = NO_ACTOR_SELECTED;

  public ActorsPresenter(ActorsView view) {
    this.view = view;
    this.dataSource = DataSource.getInstance();

    Firebase.setAndroidContext(view.getContext());

    firebase = new Firebase(LeaderboardActivity.FIREBASE_URL);
  }

  public void onStart() {
    requestActors();
  }

  private void requestActors() {
    Observable<Configuration> configuration = dataSource.getConfiguration();
    Observable<Map<Integer, Genre>> genres = dataSource.getGenres();
    final Observable<ActorResults> actors = dataSource.getActors();

    Observable.zip(configuration, genres, actors,
        new Func3<Configuration, Map<Integer, Genre>, ActorResults, List<ActorViewModel>>() {
          @Override
          public List<ActorViewModel> call(Configuration configuration, Map<Integer, Genre> genreMap, ActorResults actorResults) {
            allActors = actorResults.getActors();
            if (viewModelConverter == null) {
              viewModelConverter = new ActorViewModelConverter(configuration, genreMap);
            }
            return new ActorViewModelConverter(configuration, genreMap)
                .convertToViewModels(allActors);
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<List<ActorViewModel>>() {
          @Override
          public void call(List<ActorViewModel> actorViewModels) {
            view.showActors(actorViewModels);
          }
        });
  }

  private static final String VERSUS_TITLE_FORMAT = "%s vs...";

  public void selectActor(int position) {
    if (firstActor == NO_ACTOR_SELECTED) {
      firstActor = position;
      view.updateTitle(String.format(VERSUS_TITLE_FORMAT, allActors.get(position).getName()));
    } else {
      secondActor = position;
      startFight();
    }
  }

  private void startFight() {
    final Set<Integer> commonGenres = getCommonGenres();
    dataSource.getGenres()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Map<Integer, Genre>>() {
          @Override
          public void call(Map<Integer, Genre> allGenres) {
            Map<Integer, Genre> commonGenresMap = new HashMap<>();
            for (Integer genreId : commonGenres) {
              commonGenresMap.put(genreId, allGenres.get(genreId));
            }
            view.showFight(
                viewModelConverter.convertToViewModel(allActors.get(firstActor)),
                viewModelConverter.convertToViewModel(allActors.get(secondActor)),
                commonGenresMap
            );
          }
        });
  }

  private Set<Integer> getCommonGenres() {
    Set<Integer> firstGenres = getGenreIdsForActor(allActors.get(firstActor));
    Set<Integer> secondGenres = getGenreIdsForActor(allActors.get(secondActor));
    Set<Integer> commonGenres = new HashSet<>();
    for (Integer genreId : firstGenres) {
      if (secondGenres.contains(genreId)) {
        commonGenres.add(genreId);
      }
    }
    Log.d("ActorsPresenter", "There are " + commonGenres.size() + " common genres.");
    return commonGenres;
  }

  private Set<Integer> getGenreIdsForActor(Actor actor) {
    List<Movie> movies = actor.getMovies();
    Set<Integer> genreIds = new HashSet<>();
    for (int i = 0; i < movies.size(); i++) {
      for (int genreId : movies.get(i).getGenreIds()) {
        genreIds.add(genreId);
      }
    }
    return genreIds;
  }

  public void unSelectActor(int position) {
    if (firstActor == position) {
      firstActor = NO_ACTOR_SELECTED;
      view.updateTitle(R.string.select_actors);
    } else {
      secondActor = NO_ACTOR_SELECTED;
    }
  }

  public void onCancelFight() {
    firstActor = NO_ACTOR_SELECTED;
    secondActor = NO_ACTOR_SELECTED;
  }

  public void onGenreClick(Genre genre) {
    // Get first fighter score for genre
    double firstScore = getScoreForGenre(firstActor, genre.getId());

    // Get second fight score for genre
    double secondScore = getScoreForGenre(secondActor, genre.getId());

    // Show winner
    int winner = firstScore > secondScore ? firstActor : secondActor;
    view.showWinner(winner, firstScore, secondScore);

    int loser = firstScore < secondScore ? firstActor: secondActor;
    updateWinner(allActors.get(winner));
    updateLoser(allActors.get(loser));
  }

  private void updateWinner(final Actor actor) {
    final Firebase firebase = new Firebase(LeaderboardActivity.FIREBASE_URL + "/" + actor.getId());
    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d("ActorsPresenter", "onDataChange!");
        LeaderboardEntry entry;
        if (dataSnapshot.exists()) {
          entry = dataSnapshot.getValue(LeaderboardEntry.class);
        } else {
          entry = new LeaderboardEntry();
          entry.setActorId(actor.getId());
          entry.setActorName(actor.getName());
        }
        entry.setWins(entry.getWins() + 1);
        firebase.setValue(entry);
      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {
      }
    });
  }

  private void updateLoser(final Actor actor) {
    final Firebase firebase = new Firebase(LeaderboardActivity.FIREBASE_URL + "/" + actor.getId());
    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        LeaderboardEntry entry;
        if (dataSnapshot.exists()) {
          entry = dataSnapshot.getValue(LeaderboardEntry.class);
        } else {
          entry = new LeaderboardEntry();
          entry.setActorId(actor.getId());
          entry.setActorName(actor.getName());
        }
        entry.setLosses(entry.getLosses() + 1);
        firebase.setValue(entry);
      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {
      }
    });
  }

  private double getScoreForGenre(int actorIndex, int genreId) {
    List<Movie> moviesForActor = allActors.get(actorIndex).getMovies();
    int genreCount = 0;
    double genreScore = 0.0;
    for (int i = 0; i < moviesForActor.size(); i++) {
      for (int movieGenre : moviesForActor.get(i).getGenreIds()) {
        if (movieGenre == genreId) {
          genreScore += moviesForActor.get(i).getVoteAverage();
          genreCount++;
        }
      }
    }
    return genreScore / genreCount;
  }
}
