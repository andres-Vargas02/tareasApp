package com.uptc.tareasapp

import TaskViewModel
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Fragmento que muestra solo las tareas completadas.
 */
class FragmentCompleted : Fragment(R.layout.fragment_completed) {

    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewCompleted)

        // Inicializamos el adaptador con las tareas completadas
        val adapter = TaskAdapter(listOf(), onCheckBoxChanged = { task, isChecked ->
            // Cambiar el estado de la tarea basado en el CheckBox
            taskViewModel.setTaskCompletion(task, isChecked)
        })

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Observamos los cambios en la lista de tareas y filtramos solo las completadas
        taskViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            val completedTasks = tasks.filter { it.completed }
            adapter.updateTasks(completedTasks)
        }
    }
}
