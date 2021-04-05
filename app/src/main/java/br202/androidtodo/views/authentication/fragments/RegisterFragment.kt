package br202.androidtodo.views.authentication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br202.androidtodo.databinding.FragmentRegisterBinding
import br202.androidtodo.services.AuthService

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener { onRegister() }
        binding.registerToLogin.setOnClickListener { goToLogin() }
    }

    private fun onRegister() {
        val email = binding.registerEmail.text.toString()
        val password = binding.registerPassword.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(activity, "Email must not be empty.", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isEmpty()) {
            Toast.makeText(activity, "Password must not be empty.", Toast.LENGTH_SHORT).show()
            return
        }

        AuthService.register(requireActivity(), email, password) { goToLogin() }
    }

    private fun goToLogin() {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterToLogin())
    }
}