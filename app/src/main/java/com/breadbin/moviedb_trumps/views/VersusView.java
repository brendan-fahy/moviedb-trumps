package com.breadbin.moviedb_trumps.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.breadbin.moviedb_trumps.ActorsPresenter;
import com.breadbin.moviedb_trumps.R;
import com.breadbin.moviedb_trumps.model.Genre;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VersusView extends RelativeLayout {

  private static final String SCORE_FORMAT = "%.1f";

  @Bind(R.id.ivFirst)
  ImageView ivFirst;

  @Bind(R.id.tvFirst)
  TextView tvFirst;

  @Bind(R.id.tvFirstScore)
  TextView tvFirstScore;

  @Bind(R.id.ivSecond)
  ImageView ivSecond;

  @Bind(R.id.tvSecond)
  TextView tvSecond;

  @Bind(R.id.tvSecondScore)
  TextView tvSecondScore;

  @Bind(R.id.rvGenres)
  RecyclerView rvGenres;

  private ActorsPresenter presenter;

  public VersusView(Context context) {
    super(context);
  }

  public VersusView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public VersusView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public VersusView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }

  public void setPresenter(ActorsPresenter presenter) {
    this.presenter = presenter;
  }

  public void bindTo(ActorViewModel first, ActorViewModel second, Map<Integer, Genre> genres) {
    tvFirst.setText(first.getName());
    tvSecond.setText(second.getName());

    Picasso.with(getContext()).load(first.getImageUri()).into(ivFirst);
    Picasso.with(getContext()).load(second.getImageUri()).into(ivSecond);

    GenreAdapter adapter = new GenreAdapter(new ArrayList<>(genres.values()), this);
    rvGenres.setAdapter(adapter);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rvGenres.setLayoutManager(layoutManager);
  }

  public void onGenreClick(Genre genre) {
    presenter.onGenreClick(genre);
  }

  public void showWinner(int winnerIndex, double firstScore, double secondScore) {
    tvFirstScore.setText(String.format(SCORE_FORMAT, firstScore));
    tvFirstScore.setVisibility(View.VISIBLE);
    tvSecondScore.setText(String.format(SCORE_FORMAT, secondScore));
    tvSecondScore.setVisibility(View.VISIBLE);

    if (firstScore > secondScore) {
      highlightImage(ivFirst);
      highlightText(tvFirst);
      reduce(ivSecond);
      reduce(tvSecond);
      reduce(tvSecondScore);
    } else if (firstScore < secondScore) {
      highlightImage(ivSecond);
      highlightText(tvSecond);
      reduce(ivFirst);
      reduce(tvFirst);
      reduce(tvFirstScore);
    }
  }

  private void highlightImage(View view) {
    view.animate().scaleX(1.5f).scaleY(1.5f).setDuration(500).start();
  }

  private void highlightText(TextView view) {
    ObjectAnimator.ofArgb(view, "textColor", view.getCurrentTextColor(), Color.BLACK);
  }

  private void reduce(View view) {
    view.animate().alpha(0.3f).setDuration(300).start();
  }
}
