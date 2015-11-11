package com.breadbin.moviedb_trumps;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.breadbin.moviedb_trumps.leaderboards.LeaderboardActivity;
import com.breadbin.moviedb_trumps.model.Genre;
import com.breadbin.moviedb_trumps.views.ActorAdapter;
import com.breadbin.moviedb_trumps.views.ActorViewModel;
import com.breadbin.moviedb_trumps.views.VersusView;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ActorsView {

  @Bind(R.id.toolbar)
  Toolbar toolbar;

  @Bind(R.id.recyclerView)
  RecyclerView recyclerView;

  private ActorsPresenter presenter;

  private ActorAdapter adapter;

  private VersusView versusView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    presenter = new ActorsPresenter(this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.onStart();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    } else if (id == R.id.action_leaderboards) {
      Intent intent = new Intent(this, LeaderboardActivity.class);
      startActivity(intent);
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void showActors(List<ActorViewModel> actors) {
    adapter = new ActorAdapter(actors, presenter);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
    recyclerView.setHasFixedSize(false);
  }

  @Override
  public void updateTitle(String title) {
    setTitle(title);
  }

  @Override
  public void updateTitle(int stringResId) {
    setTitle(stringResId);
  }

  @Override
  public void showFight(ActorViewModel first, ActorViewModel second, Map<Integer, Genre> commonGenres) {
    if (commonGenres.size() > 0) {
      versusView = (VersusView) getLayoutInflater().inflate(R.layout.dialog_versus, null);
      versusView.setPresenter(presenter);

      versusView.bindTo(first, second, commonGenres);

      new AlertDialog.Builder(this)
          .setTitle(getString(R.string.versus_dialog_title))
          .setView(versusView)
          .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              presenter.onCancelFight();
              adapter.clearSelections();

            }
          })
          .show();
    } else {
      new AlertDialog.Builder(this)
          .setTitle("These actors are incomparable!")
          .setMessage("The actors you have selected have not appeared in any movies of the same genre. Try again with someone else.")
          .setPositiveButton(android.R.string.ok, null)
          .show();
    }
  }

  @Override
  public void showWinner(int winnerIndex, double firstScore, double secondScore) {
    versusView.showWinner(winnerIndex, firstScore, secondScore);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }

  @Override
  public Context getContext() {
    return getApplicationContext();
  }
}
