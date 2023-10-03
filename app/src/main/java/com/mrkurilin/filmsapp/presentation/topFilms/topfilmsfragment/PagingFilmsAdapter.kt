package com.mrkurilin.filmsapp.presentation.topFilms.topfilmsfragment

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.mrkurilin.filmsapp.presentation.TopFilmUIModelDiffUtilItemCallback
import com.mrkurilin.filmsapp.presentation.topFilms.model.TopFilmUIModel

class PagingFilmsAdapter(
    private val toggleFavouriteFilm: (Int?) -> Unit,
    private val toggleWatchedFilm: (Int?) -> Unit,
    private val onFilmClicked: (Int) -> Unit,
) : PagingDataAdapter<TopFilmUIModel, TopFilmViewHolder>(TopFilmUIModelDiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFilmViewHolder {
        return TopFilmViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TopFilmViewHolder, position: Int) {
        val film = getItem(position) ?: return

        holder.binding.favouriteCheckBox.setOnClickListener {
            toggleFavouriteFilm(getItem(position)?.filmId)
        }

        holder.binding.watchedCheckBox.setOnClickListener {
            toggleWatchedFilm(getItem(position)?.filmId)
        }

        holder.binding.root.setOnClickListener {
            onFilmClicked(film.filmId)
        }

        holder.bind(film)
    }
}