package com.mrkurilin.filmsapp.presentation.signinfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mrkurilin.filmsapp.databinding.FragmentSignInBinding
import com.mrkurilin.filmsapp.presentation.exceptionhandler.AuthExceptionHandleChain
import com.mrkurilin.filmsapp.util.extensions.appComponent
import com.mrkurilin.filmsapp.util.extensions.hideKeyboard
import com.mrkurilin.filmsapp.util.extensions.lazyViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by lazyViewModel { stateHandle ->
        appComponent().signInViewModel().create(stateHandle)
    }

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var authExceptionHandleChain: AuthExceptionHandleChain

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButton.setOnClickListener {
            tryToSignIn()
        }

        binding.signUpTextView.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        binding.passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                tryToSignIn()
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            viewModel.uiStateFlow.collect { signInUIState ->
                updateUi(signInUIState)
            }
        }
    }

    private fun updateUi(signInUIState: SignInUIState) {
        when (signInUIState) {
            is SignInUIState.Error -> {
                handleException(signInUIState.exception)
                binding.progressBar.visibility = View.INVISIBLE
                binding.signInGroup.visibility = View.VISIBLE
            }
            SignInUIState.Initial -> {
                binding.progressBar.visibility = View.INVISIBLE
                binding.signInGroup.visibility = View.VISIBLE
            }
            SignInUIState.SignedIn -> {
                val action = SignInFragmentDirections.actionSignInFragmentToMainFragment()
                findNavController().navigate(action)
            }
            SignInUIState.Loading -> {
                hideKeyboard()
                binding.progressBar.visibility = View.VISIBLE
                binding.signInGroup.visibility = View.INVISIBLE
            }
        }
    }

    private fun handleException(exception: Throwable) {
        authExceptionHandleChain.handle(
            exception = exception,
            emailEditText = binding.emailEditText,
            passwordEditText = binding.passwordEditText,
            context = requireContext()
        )
    }

    private fun tryToSignIn() {
        viewModel.tryToSignIn(
            binding.emailEditText.text.toString(),
            binding.passwordEditText.text.toString()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}