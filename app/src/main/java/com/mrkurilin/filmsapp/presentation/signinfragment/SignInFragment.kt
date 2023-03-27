package com.mrkurilin.filmsapp.presentation.signinfragment

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
                binding.passwordEditText.error = getString(R.string.invalid_password)
            }
            is InvalidEmailException -> {
                binding.emailEditText.error = getString(R.string.invalid_email)
            }
            is InvalidEmailAndPasswordException -> {
                binding.passwordEditText.error = getString(R.string.invalid_password)
                binding.emailEditText.error = getString(R.string.invalid_email)
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