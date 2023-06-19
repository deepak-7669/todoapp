package com.ds.todoapp.features.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ds.todoapp.R
import com.ds.todoapp.databinding.ItemTodoBinding
import com.ds.todoapp.features.domain.model.Todo

class TodoAdapter :
    RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(
            oldItem: Todo,
            newItem: Todo
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Todo,
            newItem: Todo
        ): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: Todo, newItem: Todo): Any? {
            return if (oldItem.isCompleted != newItem.isCompleted) {
                Payload.IS_COMPLETED_CHANGED
            } else {
                super.getChangePayload(oldItem, newItem)
            }
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_todo, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            val payload = payloads.firstOrNull()
            if (payload is Int && payload == Payload.IS_COMPLETED_CHANGED) {
                holder.bind(differ.currentList[position])
                return
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class MyViewHolder(private val itemTaskBinding: ItemTodoBinding) :
        RecyclerView.ViewHolder(itemTaskBinding.root) {
        fun bind(todo: Todo) {
            itemTaskBinding.todoModel = todo
            itemTaskBinding.executePendingBindings()
            itemTaskBinding.root.setOnClickListener {
                onDeleteClickListener?.invoke(todo)
            }
            itemTaskBinding.cbCompleted.setOnCheckedChangeListener { _, it ->
                val newItem = todo.copy(isCompleted = it)
                onTaskStatusChangedListener?.invoke(newItem)
            }
        }
    }

    private var onDeleteClickListener: ((Todo) -> Unit)? = null
    fun setOnDeleteClickListener(listener: (Todo) -> Unit) {
        onDeleteClickListener = listener
    }

    private var onTaskStatusChangedListener: ((Todo) -> Unit)? = null
    fun setOnTaskStatusChangedListener(listener: (Todo) -> Unit) {
        onTaskStatusChangedListener = listener
    }

    private object Payload {
        const val IS_COMPLETED_CHANGED = 1
    }
}