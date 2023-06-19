package com.ds.todoapp.features.presentation.add_todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ds.todoapp.databinding.FragmentAddTodoBinding
import com.ds.todoapp.features.domain.model.TaskInputModel
import com.ds.todoapp.features.domain.util.AppConstant
import com.ds.todoapp.features.domain.util.DialogUtils
import com.ds.todoapp.features.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddTodoFragment : Fragment() {
    private lateinit var binding: FragmentAddTodoBinding
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        handleOnClickListener()
        handleFlow()
        return binding.root
    }

    private fun handleFlow() {
        lifecycleScope.launch {
            sharedViewModel.errorFlow.collect {
                Toast.makeText(requireContext(), getString(it), Toast.LENGTH_LONG).show()
            }
        }
        lifecycleScope.launch {
            sharedViewModel.responseFlow.collect {
                Toast.makeText(requireContext(), getString(it), Toast.LENGTH_LONG).show()
                findNavController().popBackStack()

            }
        }
    }

    private fun handleOnClickListener() {
        binding.etTaskTime.setOnClickListener {
            showDateTimePicker()
        }
        binding.tvAdd.setOnClickListener { insertTask() }
        binding.tvCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun insertTask() {
        sharedViewModel.processInputAndResponse(
            TaskInputModel(
                taskTitle = binding.etTaskTitle.text.toString(),
                taskTime = binding.etTaskTime.text.toString(),
                todoHourFormat = binding.spinnerTaskTime.selectedItem.toString()
            )
        )
    }

    private fun showDateTimePicker() {
        DialogUtils.showTimePickerDialog(childFragmentManager) { time ->
            handleTimeInputs(time)
        }
    }

    private fun handleTimeInputs(time: DialogUtils.Time) {
        binding.etTaskTime.setText(time.time)
        if (time.timeFormat == AppConstant.TimeFormat.AM)
            binding.spinnerTaskTime.setSelection(0)
        else binding.spinnerTaskTime.setSelection(1)
    }
}