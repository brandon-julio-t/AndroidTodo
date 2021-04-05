package br202.androidtodo.views.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import br202.androidtodo.databinding.FragmentTodoAddBinding
import br202.androidtodo.models.Todo
import br202.androidtodo.viewModels.HomeViewModel

class TodoAddFragment : Fragment() {
    private var _binding: FragmentTodoAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTodoAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.add.setOnClickListener {
            val title = binding.dialogTodoTitle.text.toString()
            val description = binding.dialogTodoDescription.text.toString()
            viewModel.addTodo(Todo(title = title, description = description))
            Toast.makeText(activity, "Todo Created", Toast.LENGTH_SHORT).show()
            findNavController().navigate(TodoAddFragmentDirections.actionTodoAddToHome())
        }

        binding.cancel.setOnClickListener {
            findNavController().navigate(TodoAddFragmentDirections.actionTodoAddToHome())
        }
    }
}