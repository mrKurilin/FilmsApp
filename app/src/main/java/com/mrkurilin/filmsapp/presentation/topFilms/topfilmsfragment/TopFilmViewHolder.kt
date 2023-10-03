package com.mrkurilin.filmsapp.presentation.topFilms.topfilmsfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrkurilin.filmsapp.databinding.TopFilmViewHolderBinding
import com.mrkurilin.filmsapp.util.GlideRequestListener

class TopFilmViewHolder private constructor(
    val binding: TopFilmViewHolderBinding,
) : RecyclerView.ViewHolder(binding.root) {

    constructor(parent: ViewGroup) : this(
        binding = TopFilmViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    fun bind(
        name: String?,
        genre: String,
        year: Int?,
        country: String?,
        posterUrl: String,
        isFavourite: Boolean,
        isWatched: Boolean,
    ) {
        binding.posterImageView.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE

        Glide.with(itemView)
            .load(posterUrl)
            .listener(
                GlideRequestListener(
                    progressBar = binding.progressBar,
                    imageView = binding.posterImageView,
                )
            )
            .into(binding.posterImageView)

        binding.nameTextView.text = name
        binding.genresTextView.text = genre
        binding.yearTextView.text = year.toString()
        binding.countriesTextView.text = country
        binding.favouriteCheckBox.isChecked = isFavourite
        binding.watchedCheckBox.isChecked = isWatched
    }
}