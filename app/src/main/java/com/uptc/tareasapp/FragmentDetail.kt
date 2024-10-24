package com.uptc.tareasapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

/**
 * Fragmento que muestra los detalles de una tarea.
 */
class FragmentDetail : Fragment(R.layout.fragment_detail) {

    private val taskViewModel: TaskViewModel by activityViewModels() // ViewModel compartido

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskTitleTextView = view.findViewById<TextView>(R.id.tvTitle)
        val taskStatusTextView = view.findViewById<TextView>(R.id.tvStatus)

        // Obtener el ID de la tarea desde los argumentos
        val taskId = arguments?.getInt("taskId")

        // Buscar la tarea en el ViewModel y mostrar sus detalles
        taskViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            val task = tasks.find { it.id == taskId } // Buscar tarea por ID
            task?.let {
                taskTitleTextView.text = it.title // Mostrar título de la tarea
                taskStatusTextView.text = if (it.completed) "Completada" else "Pendiente" // Mostrar estado
            }
        }
    }
}
