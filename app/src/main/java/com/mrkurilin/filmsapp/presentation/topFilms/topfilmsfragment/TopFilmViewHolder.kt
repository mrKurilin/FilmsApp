package com.mrkurilin.filmsapp.presentation.topFilms.topfilmsfragment

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.databinding.TopFilmViewHolderBinding
import com.mrkurilin.filmsapp.presentation.topFilms.model.TopFilmUIModel
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

    fun bind(film: TopFilmUIModel) {
        val color = ContextCompat.getColor(
            itemView.context,
            getBackgroundColor(film.genres.split(" ").first().removeSuffix(","))
        )
        binding.root.backgroundTintList = ColorStateList.valueOf(
            color
        )
        binding.posterCardView.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE

        Glide.with(itemView)
            .load(film.posterUrl)
            .listener(
                GlideRequestListener(
                    imageView = binding.posterImageView,
                    manageVisibility = {
                        binding.posterCardView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                )
            )
            .into(binding.posterImageView)

        binding.nameTextView.text = film.name
        binding.genresTextView.text = film.genres
        binding.yearTextView.text = film.year.toString()
        binding.countriesTextView.text = film.countries
        binding.favouriteCheckBox.isChecked = film.isFavourite
        binding.watchedCheckBox.isChecked = film.isWatched
    }

    private fun getBackgroundColor(genre: String): Int {
        val color = when (genre) {
            "криминал" -> R.color.crime
            "триллер" -> R.color.thriller
            "комедия" -> R.color.comedy
            "ужасы" -> R.color.horror
            "драма" -> R.color.drama
            "приключения" -> R.color.adventure
            "боевик" -> R.color.action
            "фантастика" -> R.color.science_fiction
            "мелодрама" -> R.color.romance
            else -> R.color.white
        }
        return color
    }
}