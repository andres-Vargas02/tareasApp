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
 * Fragmento que muestra las tareas pendientes y permite agregar nuevas tareas.
 */
class FragmentPending : Fragment(R.layout.fragment_pending) {

    private val taskViewModel: TaskViewModel by activityViewModels() // ViewModel compartido

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editText = view.findViewById<EditText>(R.id.editTextText)
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewPending)

        // Inicializamos el adaptador con las tareas pendientes
        val adapter = TaskAdapter(listOf(), onCheckBoxChanged = { task, isChecked ->
            // Cambiar el estado de la tarea basado en el CheckBox
            taskViewModel.setTaskCompletion(task, isChecked)
        }, onTaskClicked = { task ->
            // Navegar al fragmento de detalles con el ID de la tarea
            val bundle = Bundle().apply {
                putInt("taskId", task.id)
            }
            findNavController().navigate(R.id.action_fragmentPending_to_fragmentDetail, bundle)
        })

        // Configurar el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Botón para agregar nuevas tareas
        btnAdd.setOnClickListener {
            val taskTitle = editText.text.toString()
            if (taskTitle.isNotEmpty()) {
                taskViewModel.addTask(taskTitle) // Agregar la tarea
                editText.text.clear() // Limpiar el campo de entrada
            }
        }

        // Observamos los cambios en la lista de tareas y filtramos solo las pendientes
        taskViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            val pendingTasks = tasks.filter { !it.completed } // Filtrar tareas pendientes
            adapter.updateTasks(pendingTasks) // Actualizar el adaptador
        }

        // Navegar al fragmento de tareas completadas
        view.findViewById<Button>(R.id.btnCompleted).setOnClickListener {
            findNavController().navigate(R.id.action_fragmentPending_to_fragmentCompleted)
        }
    }
}
