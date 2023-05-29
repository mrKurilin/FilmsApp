package com.mrkurilin.filmsapp.presentation.film_details.filmdetailsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.databinding.FragmentFilmDetailsBinding
import com.mrkurilin.filmsapp.di.appComponent
import com.mrkurilin.filmsapp.di.lazyViewModel
import kotlinx.coroutines.launch

class FilmDetailsFragment : Fragment() {

    private val filmDetailsViewModel by lazyViewModel {
        appComponent().filmDetailsViewModel()
    }

    private val args: FilmDetailsFragmentArgs by navArgs()

    private var _binding: FragmentFilmDetailsBinding? = null
    private val fragmentFilmDetailsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilmDetailsBinding.inflate(inflater, container, false)
        filmDetailsViewModel.loadFilm(args.filmId)
        return fragmentFilmDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            filmDetailsViewModel.uiStateFlow.collect { uiState ->
                updateUI(uiState)
            }
        }
    }

    private fun updateUI(uiState: FilmDetailsUIState) {
        when (uiState) {
            is FilmDetailsUIState.Error -> {

            }

            is FilmDetailsUIState.FilmLoaded -> {
                val film = uiState.filmDetailsUiModel
                fragmentFilmDetailsBinding.countriesTextView.text = getString(
                    R.string.countries, film.countries
                )
                fragmentFilmDetailsBinding.filmYearTextView.text = getString(
                    R.string.year, film.year
                )
                fragmentFilmDetailsBinding.genresTextView.text = getString(
                    R.string.genres, film.genres
                )
                fragmentFilmDetailsBinding.descriptionTextView.text = film.description
                fragmentFilmDetailsBinding.nameTextView.text = film.name

                Glide.with(requireContext())
                    .load(film.posterUrl)
                    .into(fragmentFilmDetailsBinding.posterImageView)
            }

            is FilmDetailsUIState.Loading -> {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}