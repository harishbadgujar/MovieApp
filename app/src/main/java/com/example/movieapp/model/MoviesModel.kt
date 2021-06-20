package com.example.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "movie_rating")
data class MoviesModel(

    @PrimaryKey(autoGenerate = true)
    @SerializedName("localId")
    @Expose
    var localId: Int = 0,

    @SerializedName("id")
    @Expose
    var id: Int = 0,

    @SerializedName("rating")
    @Expose
    var rating: Float? = null)
