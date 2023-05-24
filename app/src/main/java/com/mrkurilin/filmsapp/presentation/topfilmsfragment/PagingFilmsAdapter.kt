package com.mrkurilin.filmsapp.presentation.topfilmsfragment

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.mrkurilin.filmsapp.domain.model.Film
import com.mrkurilin.filmsapp.presentation.DiffUtilItemCallback

class PagingFilmsAdapter(
    private val onFavouritePressed: (Film) -> Unit,
    private val openFilmDetails: (Int) -> Unit,
) : PagingDataAdapter<Film, FilmViewHolder>(DiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val holder = FilmViewHolder(parent)

        holder.binding.favourite.setOnClickListener {
            val film = getItem(holder.absoluteAdapterPosition)!!
            onFavouritePressed(film)
        }

        holder.binding.root.setOnClickListener {
            val film = getItem(holder.absoluteAdapterPosition)!!
            openFilmDetails(film.filmId)
        }

        return holder
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position) ?: return

        holder.bind(
            name = film.name,
            genre = film.genres,
            year = film.year,
            country = film.countries,
            posterUrl = film.posterUrl,
        )
    }
}