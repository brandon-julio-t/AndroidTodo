package br202.androidtodo.viewModels

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br202.androidtodo.models.Todo
import br202.androidtodo.repositories.TodoRepository
import br202.androidtodo.services.AuthService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class HomeViewModel : ViewModel() {
    private val _todos: MutableLiveData<List<Todo>> by lazy {
        MutableLiveData<List<Todo>>().also {
            AuthService.user?.let { user ->
                TodoRepository.getAllByUser(user) {
                    it.documents
                        .map { e -> e.toObject<Todo>() }
                        .toList()
                        .filterNotNull()
                        .let { data -> _todos.value = data }

                    _isLoading.value = false
                }
            }
        }
    }

    private val _selectedTodo = MutableLiveData<Todo>()
    private val _isLoading = MutableLiveData(true)

    val isLoading: LiveData<Boolean> get() = _isLoading
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