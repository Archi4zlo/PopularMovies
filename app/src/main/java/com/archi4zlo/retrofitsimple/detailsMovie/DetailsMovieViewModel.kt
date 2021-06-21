package com.archi4zlo.retrofitsimple.detailsMovie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.archi4zlo.retrofitsimple.data.remote.movie.Movie

class DetailsMovieViewModel(movie: Movie, application: Application) : AndroidViewModel(application) {
    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    init {
        _selectedMovie.value = movie
    }
}