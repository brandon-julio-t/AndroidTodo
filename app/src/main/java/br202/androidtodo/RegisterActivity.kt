package br202.androidtodo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br202.androidtodo.databinding.ActivityRegisterBinding
import br202.androidtodo.services.AuthService

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            val email = binding.registerEmail.text.toString()
            val password = binding.registerPassword.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "Email must not be empty.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(this, "Password must not be empty.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AuthService.register(this, email, password) { goToLogin() }
        }

        binding.registerToLogin.setOnClickListener { goToLogin() }
    }

    private fun goToLogin() {
        startActivity(
            Intent(
                this,
                LoginActivity::class.java
            )
        )
        finish()
    }
}