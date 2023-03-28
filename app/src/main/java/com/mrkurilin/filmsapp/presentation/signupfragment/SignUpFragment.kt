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
import com.google.firebase.auth.FirebaseAuthException
import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.data.ViewModelFactory
import com.mrkurilin.filmsapp.data.exceptions.*
import com.mrkurilin.filmsapp.data.extensions.EditTextExtensions.Companion.setEmptyError
import com.mrkurilin.filmsapp.data.extensions.FragmentExtensions.Companion.hideKeyboard
import com.mrkurilin.filmsapp.data.extensions.FragmentExtensions.Companion.showLongToast
import com.mrkurilin.filmsapp.databinding.FragmentSignUpBinding
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels { ViewModelFactory.SignUpViewModel }
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

        binding.repeatPasswordEditText.setOnEditorActionListener { _, actionId, _ ->
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
        val repeatedPassword = binding.repeatPasswordEditText.text.toString()
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

    private fun handleException(exception: Exception) {
        when (exception) {
            is FirebaseAuthException -> {
                handleFirebaseAuthException(exception)
            }
            is EmptyFieldsException -> {
                binding.emailEditText.setEmptyError()
                binding.passwordEditText.setEmptyError()
                binding.repeatPasswordEditText.setEmptyError()
            }
            is InvalidEmailException -> {
                binding.emailEditText.error = getString(R.string.invalid_email)
            }
            is InvalidPasswordException -> {
                binding.passwordEditText.error = getString(R.string.invalid_password)
            }
            is PasswordsMismatchException -> {
                binding.repeatPasswordEditText.error = getString(R.string.mismatch_password)
            }
            is InvalidEmailAndPasswordException -> {
                binding.passwordEditText.error = getString(R.string.invalid_password)
                binding.emailEditText.error = getString(R.string.invalid_email)
            }
        }
    }

    private fun handleFirebaseAuthException(firebaseAuthException: FirebaseAuthException) {
        when (firebaseAuthException.errorCode) {
            FirebaseAuthExceptionErrorCodes.ERROR_INVALID_EMAIL -> {
                showLongToast(R.string.bad_formatted_email)
                binding.emailEditText.error = getString(R.string.bad_formatted_email)
            }
            FirebaseAuthExceptionErrorCodes.ERROR_WRONG_PASSWORD,
            FirebaseAuthExceptionErrorCodes.ERROR_USER_NOT_FOUND,
            -> {
                showLongToast(R.string.wrong_email_or_password)
            }

            else -> {
                showLongToast(R.string.wrong_email_or_password)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}