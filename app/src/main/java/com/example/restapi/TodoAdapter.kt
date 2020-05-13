package com.example.restapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.restapi.data.Todo
import kotlinx.android.synthetic.main.todo_layout.view.*

class TodoAdapter(private val todos:List<Todo>) :RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

    override fun getItemCount() = todos.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int)
    {
      holder.view.textView_todoTitle.text = todos[position].title

      holder.view.setOnClickListener {
            val action = HomeFragmentDirections.actionAddTodo()
            action.todo = todos[position]
            Navigation.findNavController(it).navigate(action)
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.TodoViewHolder
    {
        return TodoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.todo_layout,parent,false)
        )
    }

    class TodoViewHolder(val view: View): RecyclerView.ViewHolder(view)
    {

    }
}