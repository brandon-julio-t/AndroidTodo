package br202.androidtodo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br202.androidtodo.databinding.ItemTodoBinding
import br202.androidtodo.models.Todo
import br202.androidtodo.repositories.TodoRepository

class TodoAdapter(private val todos: List<Todo>, private val deleteCallback: () -> Unit) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    class ViewHolder(view: ItemTodoBinding) : RecyclerView.ViewHolder(view.root) {
        var id = ""
        val title: TextView = view.todoItemTitle
        val description: TextView = view.todoItemDescription

        lateinit var onDelete: () -> Unit

        init {
            view.todoDeleteBtn.setOnClickListener {
                TodoRepository.delete(id) { onDelete() }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = todos[position]
        holder.id = todo.id.toString()
        holder.title.text = todo.title
        holder.description.text = todo.description
        holder.onDelete = { deleteCallback() }
    }

    override fun getItemCount() = todos.size
}