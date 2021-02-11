package com.example.moviecatalogue.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.ui.MovieAdapter
import com.example.moviecatalogue.databinding.FragmentHomeBinding
import com.example.moviecatalogue.detail.DetailActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.d("Start Fragment")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            movieAdapter = MovieAdapter(itemClickCallback)

            observeMovie()
            observeTextQuery()
            observeSearch()

            with(binding.content.rvMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun observeSearch() {
        homeViewModel.search.observe(viewLifecycleOwner, { movie ->
            Timber.d("Observe search")
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        showSuccess(movie.data?.isEmpty() == true)
                        movieAdapter.setMovieList(movie.data)
                    }
                    is Resource.Error -> showError()
                }
            }
        })
    }

    private fun observeMovie() {
        homeViewModel.movie.observe(viewLifecycleOwner, { movie ->
            Timber.d("Observe movie")
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        showSuccess(movie.data?.isEmpty() == true)
                        movieAdapter.setMovieList(movie.data)
                    }
                    is Resource.Error -> showError()
                }
            }
        })
    }

    private val itemClickCallback = object : MovieAdapter.ItemClickCallback {
        override fun onItemClicked(data: Movie) {
            Timber.d("Clicked on movie list in home")
            val bundle = Bundle()
            bundle.putParcelable(DetailActivity.EXTRA_MOVIE, data)
            view?.findNavController()
                ?.navigate(R.id.action_navigation_home_to_detailActivity, bundle)
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun observeTextQuery() {
        binding.content.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    Timber.d("Search movie: $p0")
                    if (p0.isNotEmpty())
                        homeViewModel.setSearchQuery(p0.toString())
                    else
                        Handler(Looper.getMainLooper()).postDelayed({ observeMovie() }, 650)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun showLoading() {
        with(binding.content) {
            progressBar.visibility = View.VISIBLE
            viewError.root.visibility = View.GONE
            viewEmpty.root.visibility = View.GONE
        }
    }

    private fun showError() {
        with(binding.content) {
            progressBar.visibility = View.GONE
            viewError.root.visibility = View.VISIBLE
            viewEmpty.root.visibility = View.GONE
        }
    }

    private fun showSuccess(empty: Boolean) {
        with(binding.content) {
            progressBar.visibility = View.GONE
            viewError.root.visibility = View.GONE
            viewEmpty.root.visibility = if (empty) View.VISIBLE else View.GONE
        }
    }
}