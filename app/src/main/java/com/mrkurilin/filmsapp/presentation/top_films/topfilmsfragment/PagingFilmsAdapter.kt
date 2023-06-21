package com.mrkurilin.filmsapp.presentation.top_films.topfilmsfragment

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.mrkurilin.filmsapp.presentation.TopFilmUIModelDiffUtilItemCallback
import com.mrkurilin.filmsapp.presentation.top_films.model.TopFilmUIModel

class PagingFilmsAdapter(
    private val entryFavouriteFilm: (Int) -> Unit,
    private val entryWatchedFilm: (Int) -> Unit,
    private val onFilmClicked: (Int) -> Unit,
) : PagingDataAdapter<TopFilmUIModel, TopFilmViewHolder>(TopFilmUIModelDiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFilmViewHolder {
        return TopFilmViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TopFilmViewHolder, position: Int) {
        val film = getItem(position) ?: return

        holder.binding.favouriteImageButton.setOnClickListener {
            entryFavouriteFilm(film.filmId)
            holder.changeFavouriteImageButtonDrawable()
        }

        holder.binding.watchedImageButton.setOnClickListener {
            entryWatchedFilm(film.filmId)
            holder.changeWatchedImageButtonDrawable()
        }

        holder.binding.root.setOnClickListener {
            onFilmClicked(film.filmId)
        }

        holder.bind(
            name = film.name,
            genre = film.genres,
            year = film.year,
            country = film.countries,
            posterUrl = film.posterUrl,
            isFavourite = film.isFavourite,
            isWatched = film.isWatched,
        )
    }
}