package br202.androidtodo.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br202.androidtodo.databinding.ActivityLoginBinding
import br202.androidtodo.services.AuthService
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (AuthService.user() != null) {
            goToHome()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "Email must not be empty.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(this, "Password must not be empty.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AuthService.login(this, email, password) { goToHome() }
        }

        binding.loginToRegister.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }
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
}