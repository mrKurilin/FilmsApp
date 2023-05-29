package com.mrkurilin.filmsapp.presentation.top_films.topfilmsfragment

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.mrkurilin.filmsapp.presentation.DiffUtilItemCallback
import com.mrkurilin.filmsapp.presentation.top_films.model.TopFilmUIModel

class PagingFilmsAdapter(
    private val onFavouriteClicked: (TopFilmUIModel) -> Unit,
    private val onFilmClicked: (Int) -> Unit,
) : PagingDataAdapter<TopFilmUIModel, TopFilmViewHolder>(DiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFilmViewHolder {
        return TopFilmViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TopFilmViewHolder, position: Int) {
        val film = getItem(position) ?: return

        holder.binding.favouriteImageButton.setOnClickListener {
            onFavouriteClicked(film)
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
        )
    }
}