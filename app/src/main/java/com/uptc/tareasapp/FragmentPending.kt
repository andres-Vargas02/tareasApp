package com.uptc.tareasapp

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
 * Fragmento que muestra y permite agregar tareas pendientes.
 */
class FragmentPending : Fragment(R.layout.fragment_pending) {

    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editText = view.findViewById<EditText>(R.id.editTextText)
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewPending)

        // Inicializamos el adaptador
        val adapter = TaskAdapter(taskViewModel.tasks.value ?: listOf()) { task ->
            taskViewModel.completeTask(task)
        }
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

        // Observamos los cambios en la lista de tareas
        taskViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            adapter.updateTasks(tasks)
        }

        // Navegar al fragmento de tareas completadas
        view.findViewById<Button>(R.id.btnCompleted).setOnClickListener {
            findNavController().navigate(R.id.action_fragmentPending_to_fragmentCompleted)
        }
    }
}
