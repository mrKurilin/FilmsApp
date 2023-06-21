package com.mrkurilin.filmsapp.presentation

import androidx.recyclerview.widget.DiffUtil
import com.mrkurilin.filmsapp.presentation.top_films.model.TopFilmUIModel

class TopFilmUIModelDiffUtilItemCallback : DiffUtil.ItemCallback<TopFilmUIModel>() {

    override fun areItemsTheSame(
        oldTopFilmUIModel: TopFilmUIModel,
        newTopFilmUIModel: TopFilmUIModel
    ): Boolean {
        return oldTopFilmUIModel.filmId == newTopFilmUIModel.filmId
    }

    override fun areContentsTheSame(
        oldTopFilmUIModel: TopFilmUIModel,
        newTopFilmUIModel: TopFilmUIModel
    ): Boolean {
        return oldTopFilmUIModel == newTopFilmUIModel
    }
}