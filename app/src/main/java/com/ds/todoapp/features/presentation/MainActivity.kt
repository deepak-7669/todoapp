package com.ds.todoapp.features.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.findNavController
import com.ds.todoapp.R
import com.ds.todoapp.databinding.ActivityMainBinding
import com.ds.todoapp.features.domain.util.TodoOrder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        addClickListeners()
        handleOnDestinationChangeListener()
    }

    private fun handleOnDestinationChangeListener() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.addTodoFragment) {
                addNewPageSetting()
            } else {
                homePageSettings()
            }
        }
    }

    private fun homePageSettings() {
        binding.tvToolbarHeading.visibility = View.GONE
        binding.ivMenu.visibility = View.VISIBLE
    }

    private fun addNewPageSetting() {
        binding.tvToolbarHeading.visibility = View.VISIBLE
        binding.tvToolbarHeading.text = getString(R.string.add_a_task)
        binding.ivMenu.visibility = View.GONE
    }

    private fun addClickListeners() {
        binding.ivMenu.setOnClickListener { showPopupMenu() }
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(this, binding.ivMenu)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_item1 -> {
                    mainViewModel.getTodo(TodoOrder.CreatedDate)
                    true
                }

                R.id.menu_item2 -> {
                    mainViewModel.getTodo(TodoOrder.TargetDate)
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }


}