package com.example.movieapp.network

import com.example.movieapp.model.Movie
import com.squareup.moshi.Json

/**
 * Created by Harish on 17-06-2021
 */
class ApiResponse {
    @field:Json(name = "page")
    val page: Int = 0

    @field:Json(name = "total_results")
    val total_results: Int = 0

    @field:Json(name = "total_pages")
    val total_pages: Int = 0

    @field:Json(name = "results")
    val results: List<Movie>? = null
}