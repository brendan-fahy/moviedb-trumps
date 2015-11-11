package com.breadbin.moviedb_trumps.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.breadbin.moviedb_trumps.R;
import com.breadbin.moviedb_trumps.model.Genre;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GenreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<Genre> genres;

  private VersusView dialog;

  public GenreAdapter(List<Genre> genres, VersusView dialog) {
    this.genres = genres;
    this.dialog = dialog;
    Collections.sort(genres);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new GenreViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.genre_item, parent, false));
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ((GenreViewHolder) holder).bindTo(genres.get(position));
  }

  @Override
  public int getItemCount() {
    return genres.size();
  }

  public void animateTo(Genre selected) {
    applyAndAnimateRemovals(selected);
//    applyAndAnimateAdditions(models);
  }

  private void applyAndAnimateRemovals(Genre selected) {
    for (int i = genres.size() - 1; i >= 0; i--) {
      if (!selected.equals(genres.get(i))) {
        removeItem(i);
      }
    }
  }

  private void applyAndAnimateAdditions(List<Genre> newModels) {
    for (int i = 0, count = newModels.size(); i < count; i++) {
      if (!genres.contains(newModels.get(i))) {
        addItem(i, newModels.get(i));
      }
    }
  }

  public Genre removeItem(int position) {
    final Genre model = genres.remove(position);
    notifyItemRemoved(position);
    return model;
  }

  public void addItem(int position, Genre model) {
    genres.add(position, model);
    notifyItemInserted(position);
  }

  public class GenreViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tvGenreName)
    TextView tvGenreName;

    public GenreViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bindTo(final Genre genre) {
      tvGenreName.setText(genre.getName());
      tvGenreName.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          dialog.onGenreClick(genre);
          animateTo(genre);
        }
      });
    }
  }
}
