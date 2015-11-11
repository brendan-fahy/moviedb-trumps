package com.breadbin.moviedb_trumps.model;

public class LeaderboardEntry implements Comparable<LeaderboardEntry> {

  private int actorId;

  private String actorName;

  private int wins;

  private int losses;

  public LeaderboardEntry() {
  }

  public int getActorId() {
    return actorId;
  }

  public void setActorId(int actorId) {
    this.actorId = actorId;
  }

  public String getActorName() {
    return actorName;
  }

  public void setActorName(String actorName) {
    this.actorName = actorName;
  }

  public int getWins() {
    return wins;
  }

  public void setWins(int wins) {
    this.wins = wins;
  }

  public int getLosses() {
    return losses;
  }

  public void setLosses(int losses) {
    this.losses = losses;
  }

  @Override
  public int compareTo(LeaderboardEntry another) {
    return this.getWins() > another.getWins() ? 1 : (this.getWins() < another.getWins() ? -1 : 0);
  }
}
