package com.ds.todoapp.features.presentation.all_todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ds.todoapp.R
import com.ds.todoapp.databinding.FragmentAllTodoBinding
import com.ds.todoapp.features.domain.model.Todo
import com.ds.todoapp.features.domain.util.DialogUtils
import com.ds.todoapp.features.presentation.MainViewModel
import com.ds.todoapp.features.presentation.adapter.TodoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllTodoFragment : Fragment() {
    private lateinit var binding: FragmentAllTodoBinding
    private val sharedViewModel: MainViewModel by activityViewModels()
    private val adapter: TodoAdapter = TodoAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllTodoBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        addOnClickListener()
        addFlowCollector()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.tasksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.tasksRecyclerView.adapter = adapter
        adapter.setOnTaskStatusChangedListener {
            sharedViewModel.updateTodo(it)
        }
        adapter.setOnDeleteClickListener {
            handleTodoDelete(it)
        }
    }

    private fun handleTodoDelete(todo: Todo) {
        DialogUtils.showTodoDeleteDialog(requireContext()) {
            sharedViewModel.deleteTodo(todo)
        }
    }

    private fun addFlowCollector() {
        lifecycleScope.launch {
            sharedViewModel.todoFlow.collect {
                adapter.differ.submitList(it)
                handleRecyclerView(it.size)
            }
        }
    }

    private fun handleRecyclerView(size: Int) {
        binding.tasksRecyclerView.visibility = if (size > 0) View.VISIBLE else View.GONE
    }

    private fun addOnClickListener() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_allTodoFragment_to_addTodoFragment)
        }
    }
}