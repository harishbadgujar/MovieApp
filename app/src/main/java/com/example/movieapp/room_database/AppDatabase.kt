package com.example.movieapp.room_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.model.MoviesModel

@Database(entities = [MoviesModel::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun masterProductDao(): MoviesDao


    companion object{

        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            if(INSTANCE == null){

                synchronized(this){
                    INSTANCE  = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java,
                        "MoviesDatabase").build()


                }
            }

            return INSTANCE!!

        }


    }
}