package br202.androidtodo.views.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br202.androidtodo.R
import br202.androidtodo.services.AuthService
import br202.androidtodo.views.authentication.AuthenticationActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.actionLogout -> {
            AuthService.logout()
            goToLogin()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun goToLogin() {
        startActivity(
            Intent(
                this,
                AuthenticationActivity::class.java
            )
        )
        finish()
    }
}