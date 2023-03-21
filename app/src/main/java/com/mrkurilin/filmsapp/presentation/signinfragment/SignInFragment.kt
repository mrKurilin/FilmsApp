package com.mrkurilin.filmsapp.presentation.signinfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuthException
import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.data.ViewModelFactory
import com.mrkurilin.filmsapp.data.exceptions.*
import com.mrkurilin.filmsapp.data.extensions.EditTextExtensions.Companion.setEmptyError
import com.mrkurilin.filmsapp.data.extensions.EditTextExtensions.Companion.setInvalidEmailError
import com.mrkurilin.filmsapp.data.extensions.EditTextExtensions.Companion.setInvalidPasswordError
import com.mrkurilin.filmsapp.data.extensions.FragmentExtensions.Companion.showLongToast
import com.mrkurilin.filmsapp.databinding.FragmentSignInBinding
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModels { ViewModelFactory.SignInViewModel }
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

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
            viewModel.signInButtonPressed(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.signUpTextView.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(action)
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
                binding.progressBar.visibility = View.VISIBLE
                binding.signInGroup.visibility = View.INVISIBLE
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
            }
            is InvalidPasswordException -> {
                binding.passwordEditText.setInvalidPasswordError()
            }
            is InvalidEmailException -> {
                binding.emailEditText.setInvalidEmailError()
            }
            is InvalidEmailAndPasswordException -> {
                binding.emailEditText.setInvalidEmailError()
                binding.passwordEditText.setInvalidPasswordError()
            }
            else -> {
                exception.printStackTrace()
                showLongToast(R.string.unknown_error)
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