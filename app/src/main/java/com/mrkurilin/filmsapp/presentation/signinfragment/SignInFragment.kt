package com.mrkurilin.filmsapp.presentation.signinfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.databinding.FragmentSignInBinding
import com.mrkurilin.filmsapp.di.appComponent
import com.mrkurilin.filmsapp.di.lazyViewModel
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignInAuthFieldWithErrorMessage
import com.mrkurilin.filmsapp.util.extensions.hideKeyboard
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    private val signInViewModel: SignInViewModel by lazyViewModel {
        appComponent().signInViewModel()
    }

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
            signInViewModel.uiStateFlow.collect { signInUIState ->
                updateUi(signInUIState)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun tryToSignIn() {
        signInViewModel.tryToSignIn(
            email = binding.emailEditText.text.toString().trim(),
            password = binding.passwordEditText.text.toString(),
        )
    }

    private fun updateUi(signInUIState: SignInUIState) {
        when (signInUIState) {
            is SignInUIState.Error -> {
                handleException(signInUIState.exception)
                showSignInGroupOnly()
            }

            SignInUIState.Initial -> {
                showSignInGroupOnly()
            }

            SignInUIState.SignedIn -> {
                findNavController().navigate(
                    R.id.topFilmsFragment, null,
                    NavOptions.Builder().setPopUpTo(
                        findNavController().graph.startDestinationId, true
                    ).build()
                )
            }

            SignInUIState.Loading -> {
                hideKeyboard()
                binding.signInGroup.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.VISIBLE
            }

            is SignInUIState.ValidationError -> {
                showSignInGroupOnly()
                signInUIState.signInAuthFieldsWithErrorMessage.forEach { field ->
                    when (field) {
                        is SignInAuthFieldWithErrorMessage.Email -> {
                            binding.emailEditText.error = getString(field.messageRes)
                        }

                        is SignInAuthFieldWithErrorMessage.Password -> {
                            binding.passwordEditText.error = getString(field.messageRes)
                        }
                    }
                }
            }
        }
    }

    private fun handleException(exception: Throwable) {
        when (exception) {
            is FirebaseNetworkException -> {
                Toast.makeText(
                    requireContext(),
                    R.string.no_network,
                    Toast.LENGTH_LONG
                ).show()
            }

            is FirebaseAuthException -> {
                Toast.makeText(
                    requireContext(),
                    R.string.wrong_email_or_password,
                    Toast.LENGTH_LONG
                ).show()
            }

            else -> {
                Toast.makeText(
                    requireContext(),
                    R.string.unknown_error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showSignInGroupOnly() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.signInGroup.visibility = View.VISIBLE
    }
}