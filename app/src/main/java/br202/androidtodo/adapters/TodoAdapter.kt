package br202.androidtodo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br202.androidtodo.databinding.ItemTodoBinding
import br202.androidtodo.viewModels.HomeViewModel

class TodoAdapter(
    private val viewModel: HomeViewModel,
    private val reduceEvent: (event: String) -> Unit,
) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    class ViewHolder(view: ItemTodoBinding) : RecyclerView.ViewHolder(view.root) {
        var id = ""
        val title: TextView = view.todoItemTitle
        val description: TextView = view.todoItemDescription

        lateinit var onUpdate: () -> Unit
        lateinit var onDelete: () -> Unit

        init {
            view.todoDeleteBtn.setOnClickListener { onDelete() }
            view.todoUpdateBtn.setOnClickListener { onUpdate() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        viewModel.todos.value?.get(position)?.let {
            holder.id = it.id
            holder.title.text = it.title
            holder.description.text = it.description
            holder.onUpdate = {
                reduceEvent("update-todo")
                viewModel.selectTodo(it)
            }
            holder.onDelete = { viewModel.deleteTodo(it) }
        }
    }

    override fun getItemCount() = viewModel.todos.value?.size ?: 0
}