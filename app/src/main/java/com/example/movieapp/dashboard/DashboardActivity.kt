package com.example.movieapp.dashboard

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapter.PopularMoviesAdapter
import com.example.movieapp.adapter.UpcomingMoviesAdapter
import com.example.movieapp.baseactivity.BaseActivity
import com.example.movieapp.databinding.ActivityDashboardBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.movie_details.MovieDetailsActivity
import com.example.movieapp.network.ApiResponse
import com.example.movieapp.network.Resource
import com.example.movieapp.network.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : BaseActivity() {
    lateinit var binding: ActivityDashboardBinding
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    private lateinit var upcomingMoviesAdapter: UpcomingMoviesAdapter

    override fun initializeViewBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        setSupportActionBar(binding.toolbar)
        if (isOnline()) {
            binding.progressBar.isVisible = true
            setPopularMovieAdapter()
            setUpcomingMovieAdapter()
        }
    }

    override fun observeViewModel() {

        observerPopularMovies()
        observeUpcomingMovies()
    }


    private fun observerPopularMovies() {

        dashboardViewModel.getPopularMovies().observe(this, {
            it.let { resource ->
                when (resource.status) {

                    Status.Success -> {
                        resource.data?.let { it ->
                            it.results?.let { popularMovies ->

                                binding.nestedScrollView.isVisible = true
                                binding.groupPopular.visibility = View.VISIBLE
                                popularMoviesAdapter.addPopularMovies(popularMovies)
                            }
                        }
                    }

                    Status.Error -> {

                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }

                    Status.Loading -> {


                    }

                }
            }


        })


    }

    private fun observeUpcomingMovies() {
        dashboardViewModel.getUpcomingMovies().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.Success -> {
                        resource.data?.let { it ->
                            it.results?.let { popularMovies ->
                                binding.progressBar.isVisible = false
                                binding.groupUpcoming.visibility = View.VISIBLE
                                upcomingMoviesAdapter.addUpcomingMovies(popularMovies)
                            }
                        }
                    }

                    Status.Error -> {
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }

                    Status.Loading -> {

                    }
                }
            }
        })
    }

    private fun setPopularMovieAdapter() {
        binding.rvPopular.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        popularMoviesAdapter = PopularMoviesAdapter(listOf())
        binding.rvPopular.adapter = popularMoviesAdapter
    }

    private fun setUpcomingMovieAdapter() {
        binding.rvUpcoming.layoutManager = GridLayoutManager(this, 3)
        val screenWidth = getScreenWidth(this)
        val imageWidth = (screenWidth - 45) / 3
        upcomingMoviesAdapter = UpcomingMoviesAdapter(listOf(), imageWidth, onMovieClick)
        binding.rvUpcoming.adapter = upcomingMoviesAdapter
    }

    private val onMovieClick: (Movie) -> Unit = { movie ->
        Intent(this, MovieDetailsActivity::class.java).apply {
            putExtra(MovieDetailsActivity.MOVIE, movie)
            startActivity(this)
        }
    }

}