package com.archi4zlo.retrofitsimple.movieList

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.archi4zlo.retrofitsimple.R
import com.archi4zlo.retrofitsimple.data.remote.movie.Movie
import com.archi4zlo.retrofitsimple.data.remote.movie.MovieApi
import com.archi4zlo.retrofitsimple.data.remote.movie.MovieListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

enum class MovieApiStatus{LOADING, ERROR, DONE}
class MovieListViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    private val TAG = "movieListViewModel"

    private val _status = MutableLiveData<MovieApiStatus>()
    val status: LiveData<MovieApiStatus>
        get() = _status

    private val _movies = MutableLiveData<MovieListResponse>()
    val movies: LiveData<MovieListResponse>
        get() = _movies

    private val _navigateToSelectedProperty = MutableLiveData<Movie?>()
    val navigateToSelectedProperty: LiveData<Movie?>
        get() = _navigateToSelectedProperty

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun fetchMovieList(movieApi: MovieApi?) {
        movieApi?.let {
            _status.value = MovieApiStatus.LOADING
            compositeDisposable.add(movieApi.getQuestList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _movies.value = it
                    _status.value = MovieApiStatus.DONE
                }, {
                    Log.e(TAG, "Не получилось подтянуть фильмы")
                    _status.value = MovieApiStatus.ERROR
                })
            )
        }
    }
    fun bindStatus(statusImageView: ImageView, btnTryAgain: Button, textViewInternet: TextView, status: MovieApiStatus?){
        when(status){
            MovieApiStatus.LOADING -> {
                statusImageView.visibility = View.VISIBLE
                statusImageView.setImageResource(R.drawable.loading_animation)
            }
            MovieApiStatus.ERROR ->{
                statusImageView.visibility = View.VISIBLE
                btnTryAgain.visibility = View.VISIBLE
                textViewInternet.visibility = View.VISIBLE
                statusImageView.setImageResource(R.drawable.ic_connection_error)

            }
            MovieApiStatus.DONE -> {
                textViewInternet.visibility = View.GONE
                statusImageView.visibility = View.GONE
                btnTryAgain.visibility = View.GONE
            }
        }
    }
    fun displayPropertyDetails(movie: Movie){
        _navigateToSelectedProperty.value = movie
    }
    fun displayPropertyDetailsComplete(){
        _navigateToSelectedProperty.value = null
    }
}