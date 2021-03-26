package br202.androidtodo.viewModels

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
                it.result
                    ?.map { e ->
                        val todo = e.toObject<Todo>()
                        todo.id = e.id
                        return@map todo
                    }
                    ?.toList()
                    ?.let { data -> _todos.value = data }
            }
        }
    }

    private val _selectedTodo = MutableLiveData<Todo>()

    val todos: LiveData<List<Todo>> get() = _todos
    val selectedTodo: LiveData<Todo> get() = _selectedTodo

    fun addTodo(todo: Todo) {
        TodoRepository.save(todo) {
            _todos.value?.toMutableList()?.let {
                it.add(todo)
                _todos.value = it
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

    fun removeTodo(todo: Todo) {
        todo.id?.let { id ->
            TodoRepository.delete(id) {
                _todos.value = _todos.value?.filter { it.id != id }
            }
        }
    }

    fun selectTodo(todo: Todo) {
        _selectedTodo.value = todo
    }
}