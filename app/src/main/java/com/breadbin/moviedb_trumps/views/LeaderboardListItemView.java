package com.breadbin.moviedb_trumps.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.breadbin.moviedb_trumps.R;
import com.breadbin.moviedb_trumps.model.LeaderboardEntry;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LeaderboardListItemView extends LinearLayout {

  @Bind(R.id.tvName)
  TextView tvName;

  @Bind(R.id.tvWins)
  TextView tvWins;

  @Bind(R.id.tvLosses)
  TextView tvLosses;

  public LeaderboardListItemView(Context context) {
    super(context);
  }

  public LeaderboardListItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public LeaderboardListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public LeaderboardListItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }

  public void bindTo(LeaderboardEntry entry) {
    tvName.setText(entry.getActorName());
    tvWins.setText(entry.getWins());
    tvLosses.setText(entry.getLosses());
  }
}
