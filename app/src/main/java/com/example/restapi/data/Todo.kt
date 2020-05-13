package com.example.restapi.data

import java.io.Serializable

data class Todo(
    var id: Int = 0,
    var userId: Int = 0,
    var title: String ="",
    var complete: Boolean =false
) :Serializable

