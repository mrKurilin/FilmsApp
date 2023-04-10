package com.mrkurilin.filmsapp.presentation.signupfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.databinding.FragmentSignUpBinding
import com.mrkurilin.filmsapp.di.appComponent
import com.mrkurilin.filmsapp.di.lazyViewModel
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignUpAuthFieldWithErrorMessage
import com.mrkurilin.filmsapp.domain.exceptions.PasswordsMismatchException
import com.mrkurilin.filmsapp.util.extensions.hideKeyboard
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    private val signUpViewModel: SignUpViewModel by lazyViewModel {
        appComponent().signUpViewModel()
    }

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

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
            signUpViewModel.uiStateFlow.collect { signUpUiState ->
                updateUI(signUpUiState)
            }
        }
    }

    private fun tryToSignUp() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()
        signUpViewModel.tryToSignUp(email, password, confirmPassword)
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
            is SignUpUIState.ValidationError -> {
                binding.progressBar.visibility = View.INVISIBLE
                binding.signUpGroup.visibility = View.VISIBLE
                signUpUiState.signUpAuthFieldsWithErrorMessage.forEach { field ->
                    when (field) {
                        is SignUpAuthFieldWithErrorMessage.ConfirmPassword -> {
                            binding.confirmPasswordEditText.error = getString(field.messageRes)
                        }
                        is SignUpAuthFieldWithErrorMessage.Email -> {
                            binding.emailEditText.error = getString(field.messageRes)
                        }
                        is SignUpAuthFieldWithErrorMessage.Password -> {
                            binding.passwordEditText.error = getString(field.messageRes)
                        }
                    }

                }
            }
        }
    }

    private fun handleException(exception: Throwable) {
        when (exception) {
            is PasswordsMismatchException -> {
                binding.confirmPasswordEditText.error = getString(R.string.mismatch_password)
            }
            is FirebaseNetworkException -> {
                Toast.makeText(requireContext(), R.string.no_network, Toast.LENGTH_LONG).show()
            }
            is FirebaseAuthException -> {
                Toast.makeText(context, R.string.wrong_email_or_password, Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(context, R.string.unknown_error, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}