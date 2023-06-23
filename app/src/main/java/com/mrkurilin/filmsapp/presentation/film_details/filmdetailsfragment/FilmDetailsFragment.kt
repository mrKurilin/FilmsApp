package com.mrkurilin.filmsapp.presentation.film_details.filmdetailsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.databinding.FragmentFilmDetailsBinding
import com.mrkurilin.filmsapp.di.appComponent
import com.mrkurilin.filmsapp.di.lazyViewModel
import com.mrkurilin.filmsapp.presentation.film_details.model.FilmDetailsUiModel
import com.mrkurilin.filmsapp.util.GlideRequestListener
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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    filmDetailsViewModel.uiStateFlow.collect { uiState ->
                        updateUI(uiState)
                    }
                }

                launch {
                    tryToLoadFilm()
                }
            }
        }

        fragmentFilmDetailsBinding.tryAgainButton.setOnClickListener {
            lifecycleScope.launch {
                filmDetailsViewModel.onLoading()
                delay(500)
                tryToLoadFilm()
            }
        }
    }

    private fun updateUI(uiState: FilmDetailsUIState) {
        when (uiState) {
            is FilmDetailsUIState.Error -> {
                fragmentFilmDetailsBinding.errorGroup.isVisible = true
                fragmentFilmDetailsBinding.progressBar.isVisible = false
                fragmentFilmDetailsBinding.filmDetailsGroup.isVisible = false
            }

            is FilmDetailsUIState.Loading -> {
                fragmentFilmDetailsBinding.errorGroup.isVisible = false
                fragmentFilmDetailsBinding.progressBar.isVisible = true
                fragmentFilmDetailsBinding.filmDetailsGroup.isVisible = false
            }

            is FilmDetailsUIState.FilmLoaded -> {
                fragmentFilmDetailsBinding.errorGroup.isVisible = false
                fragmentFilmDetailsBinding.progressBar.isVisible = false
                fragmentFilmDetailsBinding.filmDetailsGroup.isVisible = true
                bind(uiState.filmDetailsUiModel)
            }
        }
    }

    private fun bind(filmDetailsUiModel: FilmDetailsUiModel) {
        Glide.with(fragmentFilmDetailsBinding.posterImageView)
            .load(filmDetailsUiModel.posterUrl)
            .addListener(
                GlideRequestListener(
                    progressBar = fragmentFilmDetailsBinding.posterLoadingProgressBar,
                    imageView = fragmentFilmDetailsBinding.posterImageView,
                )
            )
            .into(fragmentFilmDetailsBinding.posterImageView)

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
        fragmentFilmDetailsBinding.watchedCheckBox.isChecked = filmDetailsUiModel.isWatched
        fragmentFilmDetailsBinding.favouriteCheckBox.isChecked = filmDetailsUiModel.isFavourite

        fragmentFilmDetailsBinding.favouriteCheckBox.setOnClickListener {
            filmDetailsViewModel.toggleFavouriteFilm(filmDetailsUiModel.filmId)
        }

        fragmentFilmDetailsBinding.watchedCheckBox.setOnClickListener {
            filmDetailsViewModel.toggleWatchedFilm(filmDetailsUiModel.filmId)
        }
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