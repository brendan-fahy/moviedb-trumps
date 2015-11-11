package com.breadbin.moviedb_trumps;

import android.content.Context;

import com.breadbin.moviedb_trumps.model.Genre;
import com.breadbin.moviedb_trumps.views.ActorViewModel;

import java.util.List;
import java.util.Map;

public interface ActorsView {

  void showActors(List<ActorViewModel> actors);

  void updateTitle(String title);

  void updateTitle(int stringResId);

  void showFight(ActorViewModel first, ActorViewModel second, Map<Integer, Genre> commonGenres);

  void showWinner(int winnerIndex, double firstScore, double secondScore);

  Context getContext();
}
