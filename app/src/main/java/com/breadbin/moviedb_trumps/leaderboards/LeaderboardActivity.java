package com.breadbin.moviedb_trumps.leaderboards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.breadbin.moviedb_trumps.R;
import com.breadbin.moviedb_trumps.model.LeaderboardEntry;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LeaderboardActivity extends AppCompatActivity {

  public static final String FIREBASE_URL = "https://moviedb-hackathon.firebaseio.com/";

  @Bind(R.id.toolbar)
  Toolbar toolbar;

  @Bind(R.id.rvLeaderboard)
  RecyclerView rvLeaderboard;

  private Firebase firebase;

  private LeaderboardAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_leaderboard);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Firebase.setAndroidContext(this);

    firebase = new Firebase(FIREBASE_URL);

    setupRecyclerView();
  }

  private void setupRecyclerView() {
    Query query = firebase.orderByChild("wins");
    adapter = new LeaderboardAdapter(LeaderboardEntry.class, R.layout.leader_item,
        LeaderboardAdapter.LeaderViewHolder.class, query);
    rvLeaderboard.setAdapter(adapter);
    rvLeaderboard.setHasFixedSize(false);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rvLeaderboard.setLayoutManager(layoutManager);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    adapter.cleanup();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
    }
    return true;
  }
}
