package com.example.restapi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restapi.data.ApiService
import com.example.restapi.data.Todo
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create<ApiService>(ApiService::class.java)

        service.getTodoList().enqueue(object :Callback<List<Todo>>{

            override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
                if ((response != null) && (response.code() == 200)) {
                    recycler_view_todos.apply {
                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        adapter = TodoAdapter(response.body()!!)
                    }
                    Toast.makeText(requireContext(), "Succès", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<List<Todo>>, t: Throwable)
            {
                Toast.makeText(requireContext(), "Echec", Toast.LENGTH_LONG).show()
            }

        })

        add_btn.setOnClickListener {
            val action = HomeFragmentDirections.actionAddTodo()
            Navigation.findNavController(it).navigate(action)
        }

    }

}
