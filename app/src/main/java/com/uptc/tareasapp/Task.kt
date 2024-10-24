package com.uptc.tareasapp

/**
 * Clase de datos que representa una tarea.
 *
 * @property id ID único de la tarea.
 * @property title Título de la tarea.
 * @property completed Estado de la tarea: completada o pendiente.
 */
data class Task(
    val id: Int,
    val title: String,
    val completed: Boolean = false
)
