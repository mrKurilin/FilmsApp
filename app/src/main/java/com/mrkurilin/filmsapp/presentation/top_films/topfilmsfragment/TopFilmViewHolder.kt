package com.mrkurilin.filmsapp.presentation.top_films.topfilmsfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.databinding.FilmViewHolderBinding

class TopFilmViewHolder private constructor(
    val binding: FilmViewHolderBinding,
) : RecyclerView.ViewHolder(binding.root) {

    constructor(parent: ViewGroup) : this(
        FilmViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    private val posterRequestListener = PosterRequestListener(
        progressBar = binding.progressBar,
        imageView = binding.posterImageView,
    )

    fun bind(
        name: String?,
        genre: String,
        year: Int?,
        country: String?,
        posterUrl: String,
        isFavourite: Boolean = false
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

        val drawableRes = if (isFavourite) {
            R.drawable.filled_star
        } else {
            R.drawable.empty_star
        }

        binding.favouriteImageButton.setImageDrawable(
            AppCompatResources.getDrawable(
                itemView.context,
                drawableRes
            )
        )
    }
}