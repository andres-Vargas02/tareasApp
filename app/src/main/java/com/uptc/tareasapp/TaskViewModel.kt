package com.uptc.tareasapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel para gestionar las tareas.
 */
class TaskViewModel : ViewModel() {

    // Lista de tareas observada por los fragmentos
    private val _tasks = MutableLiveData<MutableList<Task>>(mutableListOf())
    val tasks: LiveData<MutableList<Task>> = _tasks

    // Variable para generar el ID autoincremental
    private var nextId = 1

    /**
     * Función para agregar una nueva tarea. El ID se genera automáticamente.
     *
     * @param taskTitle Título de la nueva tarea.
     */
    fun addTask(taskTitle: String) {
        val newTask = Task(id = nextId, title = taskTitle) // Crear nueva tarea con ID incremental
        _tasks.value = _tasks.value?.apply {
            add(newTask) // Agregar la nueva tarea a la lista
        }
        nextId++ // Incrementar el ID para la próxima tarea
    }

    /**
     * Cambiar el estado de una tarea (completada o pendiente).
     *
     * @param task Tarea a actualizar.
     * @param completed Nuevo estado de la tarea.
     */
    fun setTaskCompletion(task: Task, completed: Boolean) {
        _tasks.value = _tasks.value?.map {
            if (it.id == task.id) {
                it.copy(completed = completed) // Usar copy porque Task es data class
            } else {
                it
            }
        }?.toMutableList()
    }
}
