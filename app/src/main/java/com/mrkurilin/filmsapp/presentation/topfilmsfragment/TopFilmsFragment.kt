package com.mrkurilin.filmsapp.presentation.topfilmsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.mrkurilin.filmsapp.databinding.FragmentTopFilmsBinding
import com.mrkurilin.filmsapp.di.appComponent
import com.mrkurilin.filmsapp.di.lazyViewModel
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
            onFavouritePressed = { film ->
                topFilmsViewModel.entryFavourite(film)
            }
        )
        binding.films.adapter = adapter

        lifecycleScope.launch {
            topFilmsViewModel.pagingFilmsFlow.collect { films ->
                adapter.submitData(lifecycle, films)
            }
        }

        lifecycleScope.launch {
            topFilmsViewModel.uiStateFlow.collect { topFilmsUiState ->
                updateUI(topFilmsUiState)
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collect { combinedLoadStates ->
                when (val loadState = combinedLoadStates.refresh) {
                    is LoadState.Error -> {
                        topFilmsViewModel.errorOccurred(loadState.error)
                    }
                    is LoadState.NotLoading -> {
                        topFilmsViewModel.filmsLoaded()
                    }
                    LoadState.Loading -> {
                        topFilmsViewModel.loadingState()
                    }
                }
            }
        }
    }

    private fun updateUI(topFilmsUiState: TopFilmsUIState) {
        when (topFilmsUiState) {
            is TopFilmsUIState.Error -> {
                binding.loadingErrorGroup.isVisible = true
                binding.progressBar.isVisible = false
                binding.films.isVisible = false
            }
            TopFilmsUIState.FilmsLoaded -> {
                binding.progressBar.isVisible = false
                binding.films.isVisible = true
                binding.loadingErrorGroup.isVisible = false
            }
            TopFilmsUIState.Loading -> {
                binding.progressBar.isVisible = true
                binding.films.isVisible = false
                binding.loadingErrorGroup.isVisible = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}