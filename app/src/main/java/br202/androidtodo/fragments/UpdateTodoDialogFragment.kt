package br202.androidtodo.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
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

        preFillInputs()

        return activity?.let {
            val builder = AlertDialog.Builder(it).apply {
                setView(binding.root)

                setPositiveButton(R.string.submit) { dialog, _ ->
                    selectedTodo?.title = binding.dialogTodoTitle.text.toString()
                    selectedTodo?.description = binding.dialogTodoDescription.text.toString()

                    Toast.makeText(it, "Todo Updated", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }

                setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun preFillInputs() {
        selectedTodo = viewModel.selectedTodo.value

        val title = binding.dialogTodoTitle.text
        val description = binding.dialogTodoDescription.text

        title.clear()
        description.clear()

        title.insert(0, selectedTodo?.title)
        description.insert(0, selectedTodo?.description)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        selectedTodo?.let { viewModel.updateTodo(it) }
    }
}