package com.mrkurilin.filmsapp.presentation.top_films.topfilmsfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.databinding.LoadStateViewHolderBinding

class LoadStateViewHolder(
    parent: ViewGroup,
    retry: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.load_state_view_holder,
        parent,
        false
    )
) {

    private val binding = LoadStateViewHolderBinding.bind(itemView)
    private val progressBar: ProgressBar = binding.progressBar
    private val errorTextView: TextView = binding.errorTextView
    private val retry: Button = binding.tryAgainButton.also { button ->
        button.setOnClickListener {
            progressBar.isVisible = true
            button.isVisible = false
            errorTextView.isVisible = false
            retry()
        }
    }

    fun bind(loadState: LoadState) {
        progressBar.isVisible = loadState is LoadState.Loading
        retry.isVisible = loadState is LoadState.Error
        errorTextView.isVisible = loadState is LoadState.Error
    }
}