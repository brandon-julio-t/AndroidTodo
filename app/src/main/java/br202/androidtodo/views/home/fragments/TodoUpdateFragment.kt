package br202.androidtodo.views.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import br202.androidtodo.databinding.FragmentTodoUpdateBinding
import br202.androidtodo.models.Todo
import br202.androidtodo.viewModels.HomeViewModel

class TodoUpdateFragment : Fragment() {
    private var _binding: FragmentTodoUpdateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTodoUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        binding.update.setOnClickListener {
            viewModel.selectedTodo.value?.let { viewModel.updateTodo(it) }
            Toast.makeText(activity, "Todo Updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(TodoUpdateFragmentDirections.actionTodoUpdateToHome())
        }

        binding.cancel.setOnClickListener {
            findNavController().navigate(TodoUpdateFragmentDirections.actionTodoUpdateToHome())
        }
    }
}