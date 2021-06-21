package com.archi4zlo.retrofitsimple.movieList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.archi4zlo.retrofitsimple.MovieApp
import com.archi4zlo.retrofitsimple.R
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {
    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView() {
        val movieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)
        movieListViewModel.fetchMovieList((activity?.application as? MovieApp)?.movieApi)

        val statusImage = view?.findViewById<ImageView>(R.id.recyclerImageViewStatus)
        val buttonTryAgain = view?.findViewById<Button>(R.id.bnt_tryagain)
        val textViewInternet = view?.findViewById<TextView>(R.id.txt_checkInternetConnection)
        movieListViewModel.status.observe(viewLifecycleOwner, Observer {
            if (statusImage != null && buttonTryAgain != null && textViewInternet != null) {
                        movieListViewModel.bindStatus(statusImage,buttonTryAgain,textViewInternet,it)
            }
        })

        buttonTryAgain?.setOnClickListener {
            movieListViewModel.fetchMovieList((activity?.application as? MovieApp)?.movieApi)
        }
        movies_grid.apply {
            recyclerViewAdapter = RecyclerViewAdapter(RecyclerViewAdapter.OnClickListener { movieListViewModel.displayPropertyDetails(it) })

            movieListViewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
                if (null != it){
                    this.findNavController().navigate(MovieListFragmentDirections.actionMovieListFragmentToDetailsMovieFragment(it))
                    movieListViewModel.displayPropertyDetailsComplete()
                }
            })
            adapter = recyclerViewAdapter

        }

        movieListViewModel.movies.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                recyclerViewAdapter.setListData(it.results)
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })
    }



}