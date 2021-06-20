package com.example.movieapp.model

import com.example.movieapp.movie_details.Genre

/**
 * Created by Harish on 18-06-2021
 */
data class MovieDetails(
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Float,
    val poster_path: String,
    val release_date: String,
    val status: String,
    val tagline: String,
    val title: String,
    val vote_average: Float,
    val vote_count: Int
)