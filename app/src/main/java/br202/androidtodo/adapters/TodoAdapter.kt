package br202.androidtodo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import br202.androidtodo.R
import br202.androidtodo.databinding.ItemTodoBinding
import br202.androidtodo.repositories.TodoRepository

class TodoAdapter() :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    class ViewHolder(view: ItemTodoBinding) : RecyclerView.ViewHolder(view.root) {
        var id = 0
        val title: TextView = view.todoItemTitle
        val description: TextView = view.todoItemDescription

        lateinit var notifyDataSetChanged: () -> Unit

        init {
            view.todoDeleteBtn.setOnClickListener {
                TodoRepository.delete(id)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = TodoRepository.getAll()[position]
        holder.id = todo.id
        holder.title.text = todo.title
        holder.description.text = todo.description
        holder.notifyDataSetChanged = { notifyDataSetChanged() }
    }

    override fun getItemCount() = TodoRepository.getAll().size
}