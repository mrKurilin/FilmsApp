package com.mrkurilin.filmsapp.presentation

import androidx.recyclerview.widget.DiffUtil
import com.mrkurilin.filmsapp.presentation.top_films.model.TopFilmUIModel

class TopFilmUIModelDiffUtilItemCallback : DiffUtil.ItemCallback<TopFilmUIModel>() {

    override fun areItemsTheSame(oldItem: TopFilmUIModel, newItem: TopFilmUIModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TopFilmUIModel, newItem: TopFilmUIModel): Boolean {
        return oldItem == newItem
    }
}