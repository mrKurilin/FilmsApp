package com.mrkurilin.filmsapp.presentation.film_details.filmdetailsfragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.databinding.FragmentFilmDetailsBinding
import com.mrkurilin.filmsapp.di.appComponent
import com.mrkurilin.filmsapp.di.lazyViewModel
import com.mrkurilin.filmsapp.presentation.film_details.model.FilmDetailsUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FilmDetailsFragment : Fragment() {

    private val args: FilmDetailsFragmentArgs by navArgs()
    private val filmDetailsViewModel by lazyViewModel {
        appComponent().filmDetailsViewModel()
    }

    private var _binding: FragmentFilmDetailsBinding? = null
    private val fragmentFilmDetailsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilmDetailsBinding.inflate(inflater, container, false)
        return fragmentFilmDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            launch {
                filmDetailsViewModel.uiStateFlow.collect { uiState ->
                    updateUI(uiState)
                }
            }

            launch {
                tryToLoadFilm()
            }
        }

        fragmentFilmDetailsBinding.tryAgainButton.setOnClickListener {
            lifecycleScope.launch {
                filmDetailsViewModel.onLoading()
                delay(500)
                tryToLoadFilm()
            }
        }

        fragmentFilmDetailsBinding.favouriteImageButton.setOnClickListener {
            filmDetailsViewModel.entryFavouriteFilm()
        }

        fragmentFilmDetailsBinding.watchedImageButton.setOnClickListener {
            filmDetailsViewModel.entryWatchedFilm()
        }
    }

    private fun updateUI(uiState: FilmDetailsUIState) {
        when (uiState) {
            is FilmDetailsUIState.Error -> {
                fragmentFilmDetailsBinding.errorGroup.isVisible = true
                fragmentFilmDetailsBinding.progressBar.isVisible = false
                fragmentFilmDetailsBinding.filmDetailsGroup.isVisible = false
            }

            is FilmDetailsUIState.FilmUpdatedAndReadyToShow -> {
                fragmentFilmDetailsBinding.errorGroup.isVisible = false
                fragmentFilmDetailsBinding.progressBar.isVisible = false
                fragmentFilmDetailsBinding.filmDetailsGroup.isVisible = true
                bind(uiState.filmDetailsUiModel)
            }

            is FilmDetailsUIState.Loading -> {
                fragmentFilmDetailsBinding.errorGroup.isVisible = false
                fragmentFilmDetailsBinding.progressBar.isVisible = true
                fragmentFilmDetailsBinding.filmDetailsGroup.isVisible = false
            }

            is FilmDetailsUIState.FilmLoaded -> {
                fragmentFilmDetailsBinding.errorGroup.isVisible = false
                fragmentFilmDetailsBinding.progressBar.isVisible = true
                fragmentFilmDetailsBinding.filmDetailsGroup.isVisible = false
                loadPoster(uiState.posterUrl)
            }
        }
    }

    private fun loadPoster(posterUrl: String) {
        Glide.with(fragmentFilmDetailsBinding.posterImageView)
            .load(posterUrl)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    glideException: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    filmDetailsViewModel.onErrorOccurred()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    fragmentFilmDetailsBinding.posterImageView.setImageDrawable(resource)
                    filmDetailsViewModel.onGlideResourceReady()
                    return false
                }
            })
            .into(fragmentFilmDetailsBinding.posterImageView)
    }

    private fun bind(filmDetailsUiModel: FilmDetailsUiModel) {
        fragmentFilmDetailsBinding.countriesTextView.text = getString(
            R.string.countries, filmDetailsUiModel.countries
        )
        fragmentFilmDetailsBinding.filmYearTextView.text = getString(
            R.string.year, filmDetailsUiModel.year
        )
        fragmentFilmDetailsBinding.genresTextView.text = getString(
            R.string.genres, filmDetailsUiModel.genres
        )
        fragmentFilmDetailsBinding.descriptionTextView.text = filmDetailsUiModel.description
        fragmentFilmDetailsBinding.nameTextView.text = filmDetailsUiModel.name

        val favouriteImageButtonDrawableRes = if (filmDetailsUiModel.isFavourite) {
            R.drawable.filled_star
        } else {
            R.drawable.empty_star
        }

        val watchedImageButtonDrawableRes = if (filmDetailsUiModel.isWatched) {
            R.drawable.seen
        } else {
            R.drawable.not_seen
        }

        fragmentFilmDetailsBinding.favouriteImageButton.setImageDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                favouriteImageButtonDrawableRes
            )
        )

        fragmentFilmDetailsBinding.watchedImageButton.setImageDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                watchedImageButtonDrawableRes
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun tryToLoadFilm() {
        try {
            filmDetailsViewModel.loadFilm(args.filmId)
        } catch (exception: Exception) {
            filmDetailsViewModel.onErrorOccurred()
        }
    }
}