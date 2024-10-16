package com.uptc.tareasapp

import TaskViewModel
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController

/**
 * Fragmento que muestra las tareas pendientes y permite agregar nuevas tareas.
 */
class FragmentPending : Fragment(R.layout.fragment_pending) {

    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editText = view.findViewById<EditText>(R.id.editTextText)
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewPending)

        // Inicializamos el adaptador con las tareas pendientes
        val adapter = TaskAdapter(listOf(), onCheckBoxChanged = { task, isChecked ->
            // Cambiar el estado de la tarea basado en el CheckBox
            taskViewModel.setTaskCompletion(task, isChecked)
        })

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Botón para agregar nuevas tareas
        btnAdd.setOnClickListener {
            val taskTitle = editText.text.toString()
            if (taskTitle.isNotEmpty()) {
                val task = Task(title = taskTitle)
                taskViewModel.addTask(task)
                editText.text.clear()
            }
        }

        // Observamos los cambios en la lista de tareas y filtramos solo las pendientes
        taskViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            val pendingTasks = tasks.filter { !it.completed }
            adapter.updateTasks(pendingTasks)
        }

        // Navegar al fragmento de tareas completadas
        view.findViewById<Button>(R.id.btnCompleted).setOnClickListener {
            findNavController().navigate(R.id.action_fragmentPending_to_fragmentCompleted)
        }
    }
}
