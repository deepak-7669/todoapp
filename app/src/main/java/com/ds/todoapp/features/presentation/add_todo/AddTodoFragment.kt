package com.ds.todoapp.features.presentation.add_todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ds.todoapp.databinding.FragmentAddTodoBinding
import com.ds.todoapp.features.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddTodoFragment : Fragment() {
    private lateinit var binding: FragmentAddTodoBinding
    val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

}