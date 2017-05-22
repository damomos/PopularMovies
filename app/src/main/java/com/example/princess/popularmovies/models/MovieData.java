package com.example.princess.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Princess on 5/16/2017.
 */

public class MovieData implements Parcelable{

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("release_date")
    @Expose
    private String date;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("original_title")
    @Expose
    private String title;

    @SerializedName("backdrop_path")
    @Expose
    private String backDrop;

    @SerializedName("vote_average")
    @Expose
    private Double rating;


    //This method reads the details from the source and its protected so as not to be tampered with
    protected MovieData(Parcel in) {
        posterPath = in.readString();
        overview = in.readString();
        date = in.readString();
        id = in.readInt();
        title = in.readString();
        backDrop = in.readString();
        rating = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(date);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(backDrop);
        dest.writeDouble(rating);

    }

    //This method accesses the protected data and gets the details
    public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData [size];
        }
    };

    public Integer getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackDrop() {
        return backDrop;
    }

    public String getOverview() {
        return overview;
    }

    public Double getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(Double rating) {

        this.rating = rating;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setBackDrop(String backDrop) {
        this.backDrop = backDrop;
    }

    public void setId(Integer id) {
        this.id = id;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
