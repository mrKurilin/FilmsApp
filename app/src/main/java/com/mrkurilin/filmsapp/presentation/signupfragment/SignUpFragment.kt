package com.mrkurilin.filmsapp.presentation.signupfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mrkurilin.filmsapp.databinding.FragmentSignUpBinding
import com.mrkurilin.filmsapp.presentation.ViewModelFactory
import com.mrkurilin.filmsapp.presentation.exceptionhandler.SignUpExceptionHandleChain
import com.mrkurilin.filmsapp.util.extensions.hideKeyboard
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels { ViewModelFactory.signUpViewModel }

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val signUpExceptionHandleChain = SignUpExceptionHandleChain()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.signUpButton.setOnClickListener {
            tryToSignUp()
        }

        binding.signInTextView.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            findNavController().navigate(action)
        }

        binding.confirmPasswordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                tryToSignUp()
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            viewModel.uiStateFlow.collect { signUpUiState ->
                updateUI(signUpUiState)
            }
        }
    }

    private fun tryToSignUp() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val repeatedPassword = binding.confirmPasswordEditText.text.toString()
        viewModel.tryToSignUp(email, password, repeatedPassword)
    }

    private fun updateUI(signUpUiState: SignUpUIState) {
        when (signUpUiState) {
            is SignUpUIState.Initial -> {
                binding.signUpGroup.visibility = View.VISIBLE
                binding.progressBar.visibility = View.INVISIBLE
            }
            is SignUpUIState.Loading -> {
                hideKeyboard()
                binding.signUpGroup.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.VISIBLE
            }
            is SignUpUIState.SignedUp -> {
                val action = SignUpFragmentDirections.actionSignUpFragmentToMainFragment()
                findNavController().navigate(action)
            }
            is SignUpUIState.Error -> {
                handleException(signUpUiState.exception)
                binding.progressBar.visibility = View.INVISIBLE
                binding.signUpGroup.visibility = View.VISIBLE
            }
        }
    }

    private fun handleException(exception: Throwable) {
        signUpExceptionHandleChain.handle(
            exception,
            binding.emailEditText,
            binding.passwordEditText,
            binding.confirmPasswordEditText,
            requireContext()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}