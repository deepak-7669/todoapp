package com.ds.todoapp.features.domain.util

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.ds.todoapp.R
import com.ds.todoapp.features.domain.util.DateUtil.HH_MM_AA
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

object DialogUtils {
    private val TAG = DialogUtils::class.java.simpleName

    data class Time(val time: String, val timeFormat: String)

    /**
     * This Method Shows a time picker dialog and on time selected we force it to return time and time format.
     * We can also handle error or onCanelListner as per our requirement.
     */
    fun showTimePickerDialog(
        childFragmentManager: FragmentManager, onTimeSelected: (Time) -> Unit
    ) {
        val cal = Calendar.getInstance()
        val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).build()
        timePicker.addOnPositiveButtonClickListener {
            cal.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            cal.set(Calendar.MINUTE, timePicker.minute)
            val selectedTime = DateUtil.convertTo12HourFormat(
                hour = timePicker.hour, minute = timePicker.minute, format = HH_MM_AA
            )
            if (selectedTime.contains(" ")) {
                val timeInFormat = selectedTime.split(" ")
                onTimeSelected.invoke(Time(time = timeInFormat[0], timeFormat = timeInFormat[1]))
            }
        }
        timePicker.show(childFragmentManager, TAG)
    }

    fun showTodoDeleteDialog(
        context: Context, onPositiveButtonClick: () -> Unit
    ) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_delete)
        val tvCancel = dialog.findViewById<TextView>(R.id.tvCancel)
        val tvOK = dialog.findViewById<TextView>(R.id.tvOK)
        tvOK.setOnClickListener {
            onPositiveButtonClick.invoke()
            dialog.dismiss()
        }
        tvCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}