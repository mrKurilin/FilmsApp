package com.mrkurilin.filmsapp.presentation.topfilmsfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.databinding.FilmViewHolderBinding

class FilmViewHolder private constructor(
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
        imageView = binding.image,
    )

    fun bind(
        name: String?,
        genre: String,
        year: String?,
        country: String?,
        posterUrl: String,
        isFavourite: Boolean = false
    ) {
        binding.image.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE

        Glide.with(itemView)
            .load(posterUrl)
            .listener(posterRequestListener)
            .into(binding.image)

        binding.name.text = name
        binding.genre.text = genre
        binding.year.text = year
        binding.country.text = country

        val drawableRes = if (isFavourite) {
            R.drawable.filled_star
        } else {
            R.drawable.empty_star
        }

        binding.favourite.setImageDrawable(
            AppCompatResources.getDrawable(
                itemView.context,
                drawableRes
            )
        )
    }
}