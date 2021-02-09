package com.example.moviecatalogue.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModel()

    private var stateBoolean = false
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE) as Movie
        detailViewModel.movie = movie

        supportActionBar?.title = movie.originalTitle

        populateView(movie)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        detailViewModel.checkFavorite()?.observe(this, { state ->
            stateBoolean = if (state != null) state == 1 else false
            favoriteState(stateBoolean)
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_favorite -> {
                if (stateBoolean)
                    detailViewModel.deleteFavorite()
                else
                    detailViewModel.insertFavorite()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    @SuppressLint("SetTextI18n")
    private fun populateView(movie: Movie) {
        with(binding) {
            tvOriginalTitle.text = movie.originalTitle
            tvTitle.text = movie.title
            tvLanguage.text = movie.originalLanguage
            tvOverview.text = movie.overview
            tvRateAvg.text = movie.voteAverage.toString()
            tvRateCount.text = " (${movie.voteCount.toString()}) "
            tvRelease.text = resources.getString(R.string.release_on, movie.releaseDate)

            Glide.with(applicationContext)
                .load(imageUrl + movie.backdropPath)
                .apply {
                    RequestOptions.placeholderOf(R.drawable.ic_error)
                        .error(R.drawable.ic_error)
                }
                .into(ivPoster)
        }
    }

    private fun favoriteState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_on)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_off)
        }
    }

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        private const val imageUrl = "https://image.tmdb.org/t/p/w500/"
    }
}