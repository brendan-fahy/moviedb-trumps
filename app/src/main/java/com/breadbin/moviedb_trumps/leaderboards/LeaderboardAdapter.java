package com.breadbin.moviedb_trumps.leaderboards;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.breadbin.moviedb_trumps.R;
import com.breadbin.moviedb_trumps.model.LeaderboardEntry;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LeaderboardAdapter extends FirebaseRecyclerViewAdapter<LeaderboardEntry, LeaderboardAdapter.LeaderViewHolder> {

  public LeaderboardAdapter(Class<LeaderboardEntry> modelClass, int modelLayout, Class<LeaderViewHolder> viewHolderClass, Query ref) {
    super(modelClass, modelLayout, viewHolderClass, ref);
  }

  @Override
  public void populateViewHolder(LeaderViewHolder leaderViewHolder, LeaderboardEntry leaderboardEntry) {
    leaderViewHolder.bindTo(leaderboardEntry);
  }

  public static class LeaderViewHolder extends RecyclerView.ViewHolder {
//
//    @Bind(R.id.vgListItemView)
//    LeaderboardListItemView itemView;
    @Bind(R.id.tvName)
    TextView tvName;

    @Bind(R.id.tvWins)
    TextView tvWins;

    @Bind(R.id.tvLosses)
    TextView tvLosses;

    public LeaderViewHolder(View itemView) {
      super(itemView);

      ButterKnife.bind(this, itemView);
    }

    public void bindTo(LeaderboardEntry entry) {
//      itemView.bindTo(entry);
      tvName.setText(entry.getActorName());
      tvWins.setText(String.valueOf(entry.getWins()));
      tvLosses.setText(String.valueOf(entry.getLosses()));
    }
  }

}
