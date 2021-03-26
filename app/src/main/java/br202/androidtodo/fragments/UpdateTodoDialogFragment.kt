package br202.androidtodo.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import br202.androidtodo.R
import br202.androidtodo.databinding.DialogTodoBinding
import br202.androidtodo.models.Todo
import br202.androidtodo.viewModels.HomeViewModel

class UpdateTodoDialogFragment : DialogFragment() {
    private var selectedTodo: Todo? = null;
    private var _binding: DialogTodoBinding? = null;
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (_binding == null) {
            _binding = DialogTodoBinding.inflate(LayoutInflater.from(context))
        }

        selectedTodo = viewModel.selectedTodo.value
        val todoTitle = binding.dialogTodoTitle.text
        val todoDescription = binding.dialogTodoDescription.text

        listOf(todoTitle, todoDescription).forEach { it.clear() }

        todoTitle.insert(0, selectedTodo?.title)
        todoDescription.insert(0, selectedTodo?.description)

        return activity?.let {
            val builder = AlertDialog.Builder(it).apply {
                setView(binding.root)

                setPositiveButton(R.string.submit) { dialog, _ ->
                    selectedTodo?.title = todoTitle.toString()
                    selectedTodo?.description = todoDescription.toString()

                    Toast.makeText(it, "Todo Updated", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }

                setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        selectedTodo?.let { viewModel.updateTodo(it) }
    }
}