package com.example.restapi.data

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("todos")
    fun getTodoList(): Call<List<Todo>>

    @GET("todos/{id}")
    fun getTodo(@Path("id") id: Int): Call<Todo>

    @POST("todos")
    fun addTodo(@Body newDestination: Todo): Call<Todo>

    @FormUrlEncoded
    @PUT("todos/{id}")
    fun updateTodo(
        @Path("id") id: Int,
        @Field("userId") city: Int,
        @Field("title") desc: String,
        @Field("complete") complete: Boolean
    ): Call<Todo>

    @DELETE("todos/{id}")
    fun deleteDTodo(@Path("id") id: Int): Call<Unit>
}