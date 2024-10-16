import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uptc.tareasapp.Task

class TaskViewModel : ViewModel() {

    // Lista de tareas observada por los fragmentos
    private val _tasks = MutableLiveData<MutableList<Task>>(mutableListOf())
    val tasks: MutableLiveData<MutableList<Task>> = _tasks

    // Agregar nueva tarea
    fun addTask(task: Task) {
        _tasks.value?.add(task)
        _tasks.value = _tasks.value // Notificar cambios
    }

    // Cambiar el estado de una tarea (completada o pendiente)
    fun setTaskCompletion(task: Task, completed: Boolean) {
        _tasks.value = _tasks.value?.map {
            if (it.id == task.id) {
                it.copy(completed = completed)
            } else {
                it
            }
        }?.toMutableList()
    }
}
