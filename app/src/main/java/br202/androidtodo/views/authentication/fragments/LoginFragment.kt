package br202.androidtodo.views.authentication.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br202.androidtodo.databinding.FragmentLoginBinding
import br202.androidtodo.services.AuthService
import br202.androidtodo.views.home.HomeActivity

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener { onLogin() }
        binding.loginToRegister.setOnClickListener { goToRegister() }

        if (AuthService.user != null) {
            goToHome()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onLogin() {
        val email = binding.loginEmail.text.toString()
        val password = binding.loginPassword.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(activity, "Email must not be empty.", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isEmpty()) {
            Toast.makeText(activity, "Password must not be empty.", Toast.LENGTH_SHORT).show()
            return
        }

        binding.progress.isVisible = true
        AuthService.login(requireActivity(),
            email,
            password,
            { goToHome() },
            { binding.progress.isVisible = false }
        )
    }

    private fun goToHome() {
        startActivity(
            Intent(
                activity,
                HomeActivity::class.java
            )
        )

        activity?.finish()

        binding.progress.isVisible = false
    }

    private fun goToRegister() {
        findNavController().navigate(LoginFragmentDirections.actionLoginToRegister())
        binding.progress.isVisible = false
    }
}