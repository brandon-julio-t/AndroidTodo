package br202.androidtodo.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br202.androidtodo.models.Todo
import br202.androidtodo.repositories.TodoRepository
import com.google.firebase.firestore.ktx.toObject

class HomeViewModel : ViewModel() {
    private val _todos: MutableLiveData<List<Todo>> by lazy {
        MutableLiveData<List<Todo>>().also {
            TodoRepository.getAll {
                it.documents
                    .map { e -> e.toObject<Todo>() }
                    .toList()
                    .filterNotNull()
                    .let { data -> _todos.value = data }

                Log.wtf("hehe", _todos.value.toString())
            }
        }
    }

    private val _selectedTodo = MutableLiveData<Todo>()

    val todos: LiveData<List<Todo>> get() = _todos
    val selectedTodo: LiveData<Todo> get() = _selectedTodo

    fun addTodo(todo: Todo) {
        TodoRepository.save(todo) { saved ->
            _todos.value?.toMutableList()?.let { list ->
                saved.get().addOnSuccessListener { snapshot ->
                    snapshot.toObject<Todo>()?.let {
                        list.add(it)
                        _todos.value = list
                    }
                }
            }
        }
    }

    fun updateTodo(todo: Todo) {
        TodoRepository.update(todo) {
            _todos.value = _todos.value?.map {
                if (todo.id == it.id) todo else it
            }
        }
    }

    fun deleteTodo(todo: Todo) {
        TodoRepository.delete(todo.id) {
            _todos.value = _todos.value?.filter { it.id != todo.id }
        }
    }

    fun selectTodo(todo: Todo) {
        _selectedTodo.value = todo
    }
}