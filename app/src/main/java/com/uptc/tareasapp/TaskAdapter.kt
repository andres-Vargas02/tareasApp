package com.uptc.tareasapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador para mostrar las tareas en un RecyclerView.
 */
class TaskAdapter(
    private var tasks: List<Task>,
    private val onCheckBoxChanged: (Task, Boolean) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitleTask)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        // Desactivar listeners previos antes de actualizar el estado
        holder.checkBox.setOnCheckedChangeListener(null)

        holder.title.text = task.title
        holder.checkBox.isChecked = task.completed

        // Activar el listener para actualizar el estado de la tarea cuando se presiona el CheckBox
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            onCheckBoxChanged(task, isChecked)
        }
    }

    override fun getItemCount(): Int = tasks.size

    // Actualizar la lista de tareas
    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
