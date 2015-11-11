package com.breadbin.moviedb_trumps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {

  @SerializedName("adult")
  @Expose
  private boolean adult;
  @SerializedName("backdrop_path")
  @Expose
  private String backdropPath;
  @SerializedName("genre_ids")
  @Expose
  private int[] genreIds;
  @SerializedName("id")
  @Expose
  private int id;
  @SerializedName("original_title")
  @Expose
  private String originalTitle;
  @SerializedName("release_date")
  @Expose
  private String releaseDate;
  @SerializedName("poster_path")
  @Expose
  private String posterPath;
  @SerializedName("popularity")
  @Expose
  private double popularity;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("vote_average")
  @Expose
  private double voteAverage;
  @SerializedName("vote_count")
  @Expose
  private int voteCount;
  @SerializedName("media_type")
  @Expose
  private String mediaType;

  /**
   *
   * @return
   * The adult
   */
  public boolean isAdult() {
    return adult;
  }

  /**
   *
   * @param adult
   * The adult
   */
  public void setAdult(boolean adult) {
    this.adult = adult;
  }

  /**
   *
   * @return
   * The backdropPath
   */
  public String getBackdropPath() {
    return backdropPath;
  }

  /**
   *
   * @param backdropPath
   * The backdrop_path
   */
  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  /**
   *
   * @return
   * The id
   */
  public int getId() {
    return id;
  }

  /**
   *
   * @param id
   * The id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   *
   * @return
   * The originalTitle
   */
  public String getOriginalTitle() {
    return originalTitle;
  }

  /**
   *
   * @param originalTitle
   * The original_title
   */
  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  /**
   *
   * @return
   * The releaseDate
   */
  public String getReleaseDate() {
    return releaseDate;
  }

  /**
   *
   * @param releaseDate
   * The release_date
   */
  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  /**
   *
   * @return
   * The posterPath
   */
  public String getPosterPath() {
    return posterPath;
  }

  /**
   *
   * @param posterPath
   * The poster_path
   */
  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  /**
   *
   * @return
   * The popularity
   */
  public double getPopularity() {
    return popularity;
  }

  /**
   *
   * @param popularity
   * The popularity
   */
  public void setPopularity(double popularity) {
    this.popularity = popularity;
  }

  /**
   *
   * @return
   * The title
   */
  public String getTitle() {
    return title;
  }

  /**
   *
   * @param title
   * The title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   *
   * @return
   * The voteAverage
   */
  public double getVoteAverage() {
    return voteAverage;
  }

  /**
   *
   * @param voteAverage
   * The vote_average
   */
  public void setVoteAverage(double voteAverage) {
    this.voteAverage = voteAverage;
  }

  /**
   *
   * @return
   * The voteCount
   */
  public int getVoteCount() {
    return voteCount;
  }

  /**
   *
   * @param voteCount
   * The vote_count
   */
  public void setVoteCount(int voteCount) {
    this.voteCount = voteCount;
  }

  /**
   *
   * @return
   * The mediaType
   */
  public String getMediaType() {
    return mediaType;
  }

  /**
   *
   * @param mediaType
   * The media_type
   */
  public void setMediaType(String mediaType) {
    this.mediaType = mediaType;
  }

  public int[] getGenreIds() {
    return genreIds;
  }

  public void setGenreIds(int[] genreIds) {
    this.genreIds = genreIds;
  }
}