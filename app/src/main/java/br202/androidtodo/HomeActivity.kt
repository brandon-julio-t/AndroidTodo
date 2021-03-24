package br202.androidtodo

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br202.androidtodo.adapters.TodoAdapter
import br202.androidtodo.databinding.ActivityHomeBinding
import br202.androidtodo.fragments.TodoDialogFragment
import br202.androidtodo.services.AuthService
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity(), DialogInterface.OnDismissListener {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = AuthService.user()

        if (user == null) {
            goToLogin()
        }

        binding.homeGreeting.text = getString(R.string.greeting, user?.email)

        binding.todoList.adapter = TodoAdapter()

        binding.addTodoButton.setOnClickListener {
            TodoDialogFragment().show(supportFragmentManager, "todo-dialog")
        }
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

    override fun onDismiss(dialog: DialogInterface?) {
        binding.todoList.adapter?.notifyDataSetChanged()
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
}