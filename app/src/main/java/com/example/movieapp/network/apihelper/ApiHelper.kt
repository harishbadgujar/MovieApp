package com.example.movieapp.network.apihelper

import com.example.movieapp.constant.API_KEY
import com.example.movieapp.network.apiservice.ApiService
import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {

    suspend fun getPopularMovies() = apiService.getPopularMovies(API_KEY)

    suspend fun getUpcomingMovies() = apiService.getUpcomingMovies(API_KEY)

    suspend fun getMovieDetails(id: Int) = apiService.getMovie(id, API_KEY)
}