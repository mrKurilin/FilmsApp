package com.mrkurilin.filmsapp.presentation.top_films.topfilmsfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.databinding.TopFilmViewHolderBinding

class TopFilmViewHolder private constructor(
    val binding: TopFilmViewHolderBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private val posterRequestListener = PosterRequestListener(
        progressBar = binding.progressBar,
        imageView = binding.posterImageView,
    )

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
            .listener(posterRequestListener)
            .into(binding.posterImageView)

        binding.nameTextView.text = name
        binding.genresTextView.text = genre
        binding.yearTextView.text = year.toString()
        binding.countriesTextView.text = country

        val favouriteImageButtonDrawableRes = if (isFavourite) {
            R.drawable.filled_star
        } else {
            R.drawable.empty_star
        }

        val watchedImageButtonDrawableRes = if (isWatched) {
            R.drawable.seen
        } else {
            R.drawable.not_seen
        }

        binding.favouriteImageButton.setImageDrawable(
            AppCompatResources.getDrawable(
                itemView.context,
                favouriteImageButtonDrawableRes
            )
        )

        binding.watchedImageButton.setImageDrawable(
            AppCompatResources.getDrawable(
                itemView.context,
                watchedImageButtonDrawableRes
            )
        )
    }
}