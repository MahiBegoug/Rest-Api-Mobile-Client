package com.example.restapi


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.restapi.data.ApiService
import com.example.restapi.data.Todo
import kotlinx.android.synthetic.main.fragment_add_todo.*
import kotlinx.android.synthetic.main.todo_layout.view.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 */
class AddTodoFragment : Fragment() {

    private var todo: Todo? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_todo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            todo = AddTodoFragmentArgs.fromBundle(it).todo
            editText.setText(todo?.title)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        save_btn.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create<ApiService>(ApiService::class.java)

            val mTodo = Todo(1,1, editText.text.toString(),false)

            if (todo==null)
            {
                service.addTodo(mTodo).enqueue(object : Callback<Todo> {

                    override fun onResponse(call: Call<Todo>, response: Response<Todo>)
                    {
                        Toast.makeText(requireContext(), "Saved Succufully", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<Todo>, t: Throwable)
                    {
                        Log.i("):","No there is a Failure")
                    }


                })
            } else {
                mTodo.id = todo!!.id
                service.updateTodo(mTodo.id,mTodo.userId,mTodo.title,mTodo.complete).enqueue(object : Callback<Todo> {

                    override fun onResponse(call: Call<Todo>, response: Response<Todo>)
                    {
                        Toast.makeText(requireContext(), "Updated Succufully", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<Todo>, t: Throwable)
                    {
                        Log.i("):","No there is a Failure")
                    }


                })
            }


            val action = AddTodoFragmentDirections.actionSaveTodo()
            Navigation.findNavController(requireView()).navigate(action)
        }



    }

    private fun deleteNote(itemId:Int) {
        AlertDialog.Builder(context).apply {
            setTitle("Are youe ")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes") {_,_ ->

                    val retrofit = Retrofit.Builder()
                        .baseUrl("https://jsonplaceholder.typicode.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    val service = retrofit.create<ApiService>(ApiService::class.java)
                    service.deleteDTodo(itemId)
                    val action = AddTodoFragmentDirections.actionSaveTodo()
                    view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }

            }
            setNegativeButton("No"){_,_->}
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.delete -> if (todo != null) deleteNote(item.itemId) else Toast.makeText(requireContext(), "Cannot Delete", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}
