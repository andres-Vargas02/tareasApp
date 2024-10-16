package com.uptc.tareasapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel que gestiona la lista de tareas.
 */
class TaskViewModel : ViewModel() {
    private val _tasks = MutableLiveData<MutableList<Task>>(mutableListOf())
    val tasks: LiveData<MutableList<Task>> get() = _tasks

    /**
     * Añadir una nueva tarea a la lista.
     */
    fun addTask(task: Task) {
        _tasks.value?.add(task)
        _tasks.value = _tasks.value // Para notificar cambios
    }

    /**
     * Marcar una tarea como completada.
     */
    fun completeTask(task: Task) {
        task.completed = true
        _tasks.value = _tasks.value // Para notificar cambios
    }
}
