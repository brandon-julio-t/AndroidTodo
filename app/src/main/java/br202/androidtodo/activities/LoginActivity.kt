package br202.androidtodo.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br202.androidtodo.databinding.ActivityLoginBinding
import br202.androidtodo.services.AuthService

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener { onLogin() }
        binding.loginToRegister.setOnClickListener { goToRegister() }

        if (AuthService.user != null) {
            goToHome()
            return
        }
    }

    private fun onLogin() {
        val email = binding.loginEmail.text.toString()
        val password = binding.loginPassword.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(this, "Email must not be empty.", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Password must not be empty.", Toast.LENGTH_SHORT).show()
            return
        }

        AuthService.login(this, email, password) { goToHome() }
    }

    private fun goToHome() {
        startActivity(
            Intent(
                this,
                HomeActivity::class.java
            )
        )

        finish()
    }

    private fun goToRegister() {
        startActivity(
            Intent(
                this,
                RegisterActivity::class.java
            )
        )
    }
}