package com.mrkurilin.filmsapp.presentation.topfilmsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
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
            topFilmsViewModel.pagingFilms.collect { films ->
                adapter.submitData(lifecycle, films)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}