package br202.androidtodo.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import br202.androidtodo.R
import br202.androidtodo.databinding.DialogTodoBinding
import br202.androidtodo.models.Todo
import br202.androidtodo.repositories.TodoRepository
import kotlin.random.Random

class TodoDialogFragment : DialogFragment() {
    private var _binding: DialogTodoBinding? = null;
    private val binding get() = _binding!!

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = DialogTodoBinding.inflate(inflater, container, false)
//        return _binding?.root
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (_binding == null) {
            _binding = DialogTodoBinding.inflate(LayoutInflater.from(context))
        }

        return activity?.let {
            val builder = AlertDialog.Builder(it).apply {
                setView(binding.root)

                setPositiveButton(R.string.submit) { dialog, _ ->
                    val title = binding.dialogTodoTitle.text.toString()
                    val description = binding.dialogTodoDescription.text.toString()

                    val todo = Todo(Random.nextInt(), title, description)

                    val success = TodoRepository.save(todo)

                    val message = if (success) "Success" else "An error occurred"
                    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()

                    dialog.dismiss()
                }

                setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity = requireActivity()
        if (activity is DialogInterface.OnDismissListener) {
            activity.onDismiss(dialog)
        }
    }
}