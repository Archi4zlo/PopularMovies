package com.archi4zlo.retrofitsimple.detailsMovie

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.archi4zlo.retrofitsimple.data.remote.movie.Movie

class DetailViewModelFactory (
    private val movie: Movie,
    private val application: Application): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailsMovieViewModel::class.java)){
            return DetailsMovieViewModel(movie,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

