package com.breadbin.moviedb_trumps.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.breadbin.moviedb_trumps.ActorsPresenter;
import com.breadbin.moviedb_trumps.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int MOVIE_TYPE = 1;
  private static final int LOGO_TYPE = 2;

  private List<ActorViewModel> actors;
  private ActorsPresenter presenter;

  public ActorAdapter(List<ActorViewModel> actors, ActorsPresenter presenter) {
    this.actors = actors;
    this.presenter = presenter;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case MOVIE_TYPE:
        return new ActorViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.card_actor, parent, false));
      default:
        return new LogoViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.logo_moviedb, parent, false));
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
    switch(getItemViewType(position)) {
      case MOVIE_TYPE:
        ActorViewHolder actorViewHolder = ((ActorViewHolder) holder);
        actorViewHolder.bindTo(position);
        break;
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (position < actors.size()) {
      return MOVIE_TYPE;
    } else {
      return LOGO_TYPE;
    }
  }

  @Override
  public int getItemCount() {
    return (actors == null) ? 0 : actors.size() + 1;
 }

  public void clearSelections() {
    for (int i = 0; i < actors.size(); i++) {
      actors.get(i).setSelected(false);
    }
    notifyDataSetChanged();
  }

  public final class ActorViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.movieCard)
    ActorCard movieCard;

    public ActorViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bindTo(final int position) {
      movieCard.bindTo(actors.get(position));
      movieCard.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          notifyItemChanged(position);
          actors.get(position).setSelected(!actors.get(position).isSelected());
          if (actors.get(position).isSelected()) {
            presenter.selectActor(position);
          } else {
            presenter.unSelectActor(position);
          }
          notifyItemChanged(position);
        }
      });
    }
  }

  public final class LogoViewHolder extends RecyclerView.ViewHolder {

    public LogoViewHolder(View itemView) {
      super(itemView);
    }
  }
}
