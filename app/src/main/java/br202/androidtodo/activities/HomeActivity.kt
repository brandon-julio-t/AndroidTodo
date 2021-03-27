package br202.androidtodo.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import br202.androidtodo.R
import br202.androidtodo.adapters.TodoAdapter
import br202.androidtodo.databinding.ActivityHomeBinding
import br202.androidtodo.fragments.AddTodoDialogFragment
import br202.androidtodo.fragments.UpdateTodoDialogFragment
import br202.androidtodo.models.Todo
import br202.androidtodo.services.AuthService
import br202.androidtodo.viewModels.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private val todos = mutableListOf<Todo>()
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = AuthService.user
        if (user == null) {
            goToLogin()
        }

        binding.homeGreeting.text = getString(R.string.greeting, user?.email)
        binding.homeTodoList.adapter = TodoAdapter(viewModel) { event -> reduceEvent(event) }
        binding.homeAddTodoButton.setOnClickListener { reduceEvent("add-todo") }

        viewModel.todos.observe(this, {
            todos.clear()
            todos.addAll(it)
            binding.homeTodoList.adapter?.notifyDataSetChanged()
            binding.homeNoTodo.isVisible = todos.size <= 0
        })

        viewModel.isLoading.observe(this, {
            binding.progressIndicator.isVisible = it
        })
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

    private fun reduceEvent(event: String) = when (event) {
        "add-todo" -> AddTodoDialogFragment()
            .show(supportFragmentManager, "add-todo-dialog")

        "update-todo" -> UpdateTodoDialogFragment()
            .show(supportFragmentManager, "update-todo-dialog")

        else -> null
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