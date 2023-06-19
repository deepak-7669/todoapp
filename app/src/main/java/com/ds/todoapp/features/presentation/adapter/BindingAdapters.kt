package com.ds.todoapp.features.presentation.adapter

import android.text.Spannable
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.ds.todoapp.R
import com.ds.todoapp.features.domain.model.Todo
import com.ds.todoapp.features.domain.util.DateUtil
import com.ds.todoapp.features.domain.util.DateUtil.HH_MM_AA

@BindingAdapter("strikeThroughText")
fun strikeThroughText(textView: TextView, todo: Todo) {
    textView.text = todo.title
    if (todo.isCompleted) {
        val spannableString = SpannableString(textView.text)
        spannableString.setSpan(
            StrikethroughSpan(),
            0,
            textView.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = spannableString
    }else{
        textView.paintFlags = 0 // Remove strikethrough
    }
}

@BindingAdapter("setTextColor")
fun setTextColor(textView: TextView,todo: Todo){
    val context = textView.context
    val colorRes = if (DateUtil.compareTimeWithCurrentFormat(todo.targetDate, HH_MM_AA) && !todo.isCompleted) {
        R.color.text_color_red
    } else {
        R.color.black
    }
    textView.setTextColor(ContextCompat.getColor(context, colorRes))

}
@BindingAdapter("setTaskStatusVisibility")
fun setTaskStatusVisibility(textView: TextView,todo: Todo){
    textView.visibility = if (DateUtil.compareTimeWithCurrentFormat(todo.targetDate, HH_MM_AA) && !todo.isCompleted) {
       View.VISIBLE
    } else {
        View.GONE
    }
}
