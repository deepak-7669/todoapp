package com.ds.todoapp.features.presentation.all_todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ds.todoapp.R
import com.ds.todoapp.databinding.FragmentAllTodoBinding
import com.ds.todoapp.features.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllTodoFragment : Fragment() {
    private lateinit var binding: FragmentAllTodoBinding

    val sharedViewModel: MainViewModel by activityViewModels()
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
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_allTodoFragment_to_addTodoFragment)
        }
    }
}