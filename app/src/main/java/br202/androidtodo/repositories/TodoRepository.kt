package br202.androidtodo.repositories

import br202.androidtodo.models.Todo

object TodoRepository {
    private val todo = mutableListOf<Todo>(
        Todo(1, "Todo #1 title", "Todo #1 description"),
        Todo(2, "Todo #2 title", "Todo #2 description"),
        Todo(3, "Todo #3 title", "Todo #3 description"),
    )

    fun getAll(): List<Todo> {
        return todo.toList()
    }

    fun save(todo: Todo): Boolean {
        return this.todo.add(todo)
    }

    fun delete(id: Int) {
        todo.removeIf { it.id == id }
    }
}