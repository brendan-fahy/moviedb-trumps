package com.breadbin.moviedb_trumps.views;

import android.net.Uri;

public class ActorViewModel {

  private String name;

  private String rating;

  private String knownFor;

  private Uri imageUri;

  private boolean selected;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public Uri getImageUri() {
    return imageUri;
  }

  public void setImageUri(Uri imageUri) {
    this.imageUri = imageUri;
  }

  public String getKnownFor() {
    return knownFor;
  }

  public void setKnownFor(String knownFor) {
    this.knownFor = knownFor;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }
}
