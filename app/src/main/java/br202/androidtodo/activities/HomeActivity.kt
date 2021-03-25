package br202.androidtodo.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br202.androidtodo.R
import br202.androidtodo.adapters.TodoAdapter
import br202.androidtodo.databinding.ActivityHomeBinding
import br202.androidtodo.fragments.TodoDialogFragment
import br202.androidtodo.models.Todo
import br202.androidtodo.repositories.TodoRepository
import br202.androidtodo.services.AuthService
import com.google.firebase.firestore.ktx.toObject

class HomeActivity : AppCompatActivity(), DialogInterface.OnDismissListener {
    private lateinit var binding: ActivityHomeBinding
    private val todos = mutableListOf<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = AuthService.user()

        if (user == null) {
            goToLogin()
        }

        binding.homeGreeting.text = getString(R.string.greeting, user?.email)

        binding.todoList.adapter = TodoAdapter(todos) { fetchAllTodos() }

        binding.addTodoButton.setOnClickListener {
            TodoDialogFragment().show(supportFragmentManager, "todo-dialog")
        }

        fetchAllTodos()
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
        fetchAllTodos()
    }

    private fun fetchAllTodos() {
        TodoRepository.getAll {
            it.result
                ?.map { e ->
                    val todo = e.toObject<Todo>()
                    todo.id = e.id
                    return@map todo
                }
                ?.toList()
                ?.let { data ->
                    todos.clear()
                    todos.addAll(data)
                    binding.todoList.adapter?.notifyDataSetChanged()
                }
        }
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