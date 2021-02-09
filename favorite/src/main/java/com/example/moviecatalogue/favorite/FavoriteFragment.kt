package com.example.moviecatalogue.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.ui.MovieAdapter
import com.example.moviecatalogue.detail.DetailActivity
import com.example.moviecatalogue.favorite.databinding.FragmentFavoriteBinding
import com.example.moviecatalogue.favorite.di.favoriteModule
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        if (activity != null) {
            val movieAdapter = MovieAdapter(itemClickCallback)

            favoriteViewModel.favorite.observe(viewLifecycleOwner, { movie ->
                movieAdapter.setMovieList(movie)
                binding.tvEmpty.visibility = if (movie.isEmpty()) View.VISIBLE else View.GONE
            })

            with(binding.rvFavorite) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }


    private val itemClickCallback = object : MovieAdapter.ItemClickCallback {
        override fun onItemClicked(data: Movie) {
            val bundle = Bundle()
            bundle.putParcelable(DetailActivity.EXTRA_MOVIE, data)
            view?.findNavController()
                ?.navigate(R.id.action_navigation_favorite_to_detailActivity, bundle)
        }
    }
}