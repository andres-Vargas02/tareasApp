package com.uptc.tareasapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador para mostrar las tareas en un RecyclerView.
 *
 * @property tasks Lista de tareas a mostrar.
 * @property onCheckBoxChanged Función que se ejecuta al cambiar el estado del CheckBox.
 * @property onTaskClicked Función que se ejecuta al hacer clic en una tarea.
 */
class TaskAdapter(
    private var tasks: List<Task>,
    private val onCheckBoxChanged: (Task, Boolean) -> Unit,
    private val onTaskClicked: (Task) -> Unit // Manejar clic en la tarea
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

        // Listener para manejar el clic en la tarea completa
        holder.itemView.setOnClickListener {
            onTaskClicked(task)
        }
    }

    override fun getItemCount(): Int = tasks.size

    /**
     * Actualiza la lista de tareas y notifica los cambios al adaptador.
     *
     * @param newTasks Nueva lista de tareas.
     */
    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged() // Notificar cambios al adaptador
    }
}
