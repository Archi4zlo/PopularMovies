package com.archi4zlo.retrofitsimple.detailsMovie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.archi4zlo.retrofitsimple.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details_movie.*


class DetailsMovieFragment : Fragment(R.layout.fragment_details_movie) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createDetailData()
    }

    fun createDetailData() {

        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        val movie = DetailsMovieFragmentArgs.fromBundle(requireArguments()).selectedMovie
        val viewModelFactory = DetailViewModelFactory(movie, application)
        val movieListViewModel = ViewModelProviders.of(this,viewModelFactory).get(DetailsMovieViewModel::class.java)

        movieListViewModel.selectedMovie.observe(viewLifecycleOwner, Observer {
            if (it != null) {

                val imageDetailPster = main_poster_image
                movie_title_text.text = it.title
                if (it.adult == false){
                    movie_adult_text.text = "Not an adult"
                } else {
                    movie_adult_text.text = "18+"
                }
                movie_overview_text.text = it.overview
                movie_release_date_text.text = it.release_date
                movie_original_language_text.text = it.original_language

                val url ="https://image.tmdb.org/t/p/w500/" + it.poster_path
                Glide.with(imageDetailPster)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.default_thumb)
                    .error(R.drawable.default_thumb)
                    .fallback(R.drawable.default_thumb)
                    .into(imageDetailPster)

            } else {
                Toast.makeText(context, "Error in getting data from api.", Toast.LENGTH_LONG).show()
            }
        })
    }
}