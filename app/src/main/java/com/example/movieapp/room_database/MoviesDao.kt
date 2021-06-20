package com.example.movieapp.room_database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movieapp.model.MoviesModel


@Dao
interface MoviesDao {

    @Query("SELECT * FROM movie_rating Where id LIKE :id")
    fun getData(id: Int): MoviesModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(moviesModel: MoviesModel?)

    @Delete
    suspend fun delete(moviesModel: MoviesModel?)

    @Update
    suspend fun update(moviesModel: MoviesModel?)

    @Query("SELECT * FROM movie_rating WHERE id == :id")
    fun isDataExist(id: Int): Boolean

    @Query("UPDATE movie_rating SET rating = :rating WHERE id = :id")
    fun updateRating(id: Int, rating: Float?): Int

    @Query("SELECT * FROM movie_rating WHERE id=:id")
    fun getMemberInfo(id: Int): MoviesModel

    @Query("SELECT * FROM movie_rating WHERE id = :id")
    fun getUserRating(id: Int) : Boolean

}