package com.breadbin.moviedb_trumps;

import android.net.Uri;

import com.breadbin.moviedb_trumps.model.Actor;
import com.breadbin.moviedb_trumps.model.Genre;
import com.breadbin.moviedb_trumps.model.api.Configuration;
import com.breadbin.moviedb_trumps.model.api.RestClient;
import com.breadbin.moviedb_trumps.views.ActorViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActorViewModelConverter {

  private static final String RATING_FORMAT = "%.1f/100";
  private static final String TITLE_SEPARATOR = ", ";

  private Configuration config;

  private Map<Integer, Genre> genres;

  private static final int MAX_KNOWN_FORS = 3;

  public ActorViewModelConverter(Configuration config, Map<Integer, Genre> genres) {
    this.config = config;
    this.genres = genres;
  }

  public List<ActorViewModel> convertToViewModels(List<Actor> actors) {
    List<ActorViewModel> viewModels = new ArrayList<>(actors.size());
    for (int i = 0; i < actors.size(); i++) {
      viewModels.add(convertToViewModel(actors.get(i)));
    }
    return viewModels;
  }

  public ActorViewModel convertToViewModel(Actor actor) {
    ActorViewModel viewModel = new ActorViewModel();
    viewModel.setName(actor.getName());
    viewModel.setImageUri(getImagePath(actor.getProfilePath()));
    viewModel.setRating(getFormattedRating(actor.getPopularity()));
    viewModel.setKnownFor(getKnownForMovies(actor));
    return viewModel;
  }

  private String getKnownForMovies(Actor actor) {
    StringBuilder knownFor = new StringBuilder();

    int knownForsCount = Math.min(MAX_KNOWN_FORS, actor.getMovies().size());

    for (int i = 0; i < knownForsCount; i++) {
      if (actor.getMovies().get(i).getTitle() == null) {
        continue;
      }
      knownFor.append(actor.getMovies().get(i).getTitle());
      if (i < knownForsCount - 1) {
        knownFor.append(TITLE_SEPARATOR);
      }
    }
    return knownFor.toString();
  }

  private String getFormattedRating(double voteAverage) {
    return String.format(RATING_FORMAT, voteAverage);
  }

  private Uri getImagePath(String posterPath) {
    return Uri.parse(config.getImages().getBaseUrl())
        .buildUpon()
        .appendPath(config.getImages().getPosterSizes().get(0))
        .appendPath(posterPath.replaceFirst("/", ""))
        .appendQueryParameter(RestClient.API_KEY_PARAM_NAME, RestClient.MOVIEDB_API_KEY)
        .build();
  }
}
