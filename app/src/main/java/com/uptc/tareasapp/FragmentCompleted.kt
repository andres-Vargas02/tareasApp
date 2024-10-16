package com.uptc.tareasapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Fragmento que muestra las tareas completadas.
 */
class FragmentCompleted : Fragment(R.layout.fragment_completed) {

    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewCompleted)

        // Obtenemos las tareas completadas
        val completedTasks = taskViewModel.tasks.value?.filter { it.completed } ?: listOf()

        // Asignamos el adaptador al RecyclerView
        val adapter = TaskAdapter(completedTasks) { task -> }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Observamos los cambios en la lista de tareas
        taskViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            val updatedTasks = tasks.filter { it.completed }
            adapter.updateTasks(updatedTasks)
        }
    }
}
