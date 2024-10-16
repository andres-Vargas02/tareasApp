package com.uptc.tareasapp

data class Task(
    val id: Int = (0..1000).random(),
    val title: String,
    val completed: Boolean = false
)
