package br202.androidtodo.views.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import br202.androidtodo.R
import br202.androidtodo.adapters.TodoAdapter
import br202.androidtodo.databinding.FragmentHomeBinding
import br202.androidtodo.models.Todo
import br202.androidtodo.services.AuthService
import br202.androidtodo.viewModels.HomeViewModel
import br202.androidtodo.views.authentication.fragments.RegisterFragmentDirections

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val todos = mutableListOf<Todo>()
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = AuthService.user
        if (user == null) {
            goToLogin()
        }

        binding.homeGreeting.text = getString(R.string.greeting, user?.email)
        binding.homeTodoList.adapter = TodoAdapter(viewModel) { event -> reduceEvent(event) }
        binding.homeAddTodoButton.setOnClickListener { reduceEvent("add-todo") }

        viewModel.todos.observe(viewLifecycleOwner) {
            todos.clear()
            todos.addAll(it)
            binding.homeTodoList.adapter?.notifyDataSetChanged()
            binding.homeNoTodo.isVisible = todos.size <= 0
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressIndicator.isVisible = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun goToLogin() {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterToLogin())
        activity?.finish()
    }

    private fun reduceEvent(event: String) = when (event) {
        "add-todo" -> findNavController().navigate(HomeFragmentDirections.actionHomeToTodoAdd())
        "update-todo" -> findNavController().navigate(HomeFragmentDirections.actionHomeToTodoUpdate())
        else -> null
    }
}