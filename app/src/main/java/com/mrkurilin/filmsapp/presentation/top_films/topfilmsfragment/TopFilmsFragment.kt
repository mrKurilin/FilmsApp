package com.mrkurilin.filmsapp.presentation.top_films.topfilmsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.mrkurilin.filmsapp.databinding.FragmentTopFilmsBinding
import com.mrkurilin.filmsapp.di.appComponent
import com.mrkurilin.filmsapp.di.lazyViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TopFilmsFragment : Fragment() {

    private val topFilmsViewModel by lazyViewModel {
        appComponent().topFilmsViewModel()
    }

    private var _binding: FragmentTopFilmsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTopFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PagingFilmsAdapter(
            onFavouriteClicked = { film ->
                topFilmsViewModel.entryFavourite(film)
            },
            onFilmClicked = { filmId ->
                val action = TopFilmsFragmentDirections.actionTopFilmsFragmentToFilmDetailsFragment(filmId)
                findNavController().navigate(action)
            }
        )

        val adapterWithFooter = adapter.withLoadStateFooter(
            footer = TopFilmsLoadStateAdapter(
                retry = {
                    lifecycleScope.launch {
                        delay(500)
                        adapter.retry()
                    }
                }
            )
        )

        binding.filmsRecyclerView.adapter = adapterWithFooter

        binding.tryAgainButton.setOnClickListener {
            lifecycleScope.launch {
                topFilmsViewModel.onFilmsLoading()
                delay(500)
                adapter.retry()
            }
        }

        lifecycleScope.launch {
            topFilmsViewModel.pagingFilmsFlow.collect { filmsPagingData ->
                adapter.submitData(lifecycle, filmsPagingData)
            }
        }

        lifecycleScope.launch {
            topFilmsViewModel.uiStateFlow.collect { topFilmsUiState ->
                updateUI(topFilmsUiState)
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collect { combinedLoadStates ->
                val loadState = combinedLoadStates.refresh
                when (loadState) {
                    is LoadState.Error -> {
                        topFilmsViewModel.onFilmsLoadingError(loadState.error)
                    }
                    is LoadState.NotLoading -> {
                        topFilmsViewModel.onFilmsNotLoading()
                    }
                    LoadState.Loading -> {
                        topFilmsViewModel.onFilmsLoading()
                    }
                }
            }
        }
    }

    private fun updateUI(topFilmsUiState: TopFilmsUIState) {
        when (topFilmsUiState) {
            is TopFilmsUIState.Error -> {
                binding.progressBar.isVisible = false
                binding.filmsRecyclerView.isVisible = false
                binding.loadingErrorGroup.isVisible = true
            }
            is TopFilmsUIState.FilmsLoaded -> {
                binding.progressBar.isVisible = false
                binding.filmsRecyclerView.isVisible = true
                binding.loadingErrorGroup.isVisible = false
            }
            is TopFilmsUIState.Loading -> {
                binding.progressBar.isVisible = true
                binding.filmsRecyclerView.isVisible = false
                binding.loadingErrorGroup.isVisible = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}