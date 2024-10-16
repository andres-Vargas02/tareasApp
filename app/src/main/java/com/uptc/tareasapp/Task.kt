package com.uptc.tareasapp

data class Task(
    val title: String,
    val description: String = "",
    var completed: Boolean = false
)
