package com.example.movieapp.movie_details

import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import coil.load
import com.example.movieapp.R
import com.example.movieapp.baseactivity.BaseActivity
import com.example.movieapp.constant.POSTER_BASE_URL
import com.example.movieapp.databinding.ActivityMovieDetailsBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MoviesModel
import com.example.movieapp.network.Status
import com.example.movieapp.room_database.AppDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsActivity : BaseActivity() {
    lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    lateinit var movie: Movie

    lateinit var database: AppDatabase


    override fun initializeViewBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)

        database = AppDatabase.getDatabase(this)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        intent?.let { intent ->
            intent.getParcelableExtra<Movie>(MOVIE)?.let {
                movie = it
            }
        }

        GlobalScope.launch {

            var movieRating = database.masterProductDao().getUserRating(movie.id)
            if (movieRating == true) {

                var movie110 = database.masterProductDao().getMemberInfo(movie.id)
                movie110.rating?.toFloat()?.let { binding.ratingBar.setRating(it) }

            }

        }

        binding.ratingBar.setOnRatingBarChangeListener(object :
            RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {

                GlobalScope.launch {

                    var movie2 = database.masterProductDao().isDataExist(movie.id)

                    if (movie2 == true) {

                        database.masterProductDao().updateRating(movie.id, p1)


                    } else {

                        database.masterProductDao().insert(MoviesModel(0, movie.id, p1))


                    }


                }


            }
        })

    }


    override fun observeViewModel() {
        if (::movie.isInitialized) {
            viewModel.getMovieDetails(movie.id).observe(this) {
                it.let { resource ->
                    when (resource.status) {

                        Status.Success -> {
                            resource.data?.let { movieDetail ->
                                with(binding) {
                                    tvOverView.text = movieDetail.overview
                                    ivPoster.load(POSTER_BASE_URL.plus(movieDetail.poster_path))
                                    tvTitle.text = movieDetail.title
                                    tvRelease.text = getString(R.string.r).plus(" | ")
                                        .plus(movieDetail.release_date)
                                    tvVotes.text = getString(R.string.votes).plus(": ")
                                        .plus(movieDetail.vote_count).plus(" (Users)")
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

            }
        }
    }

    companion object {
        const val MOVIE = "movie"
    }

}