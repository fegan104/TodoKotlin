package com.frankegan.todo

import android.arch.core.util.Function
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.frankegan.todo.model.*

/**
 * Created by frankegan on 12/24/17.
 */
class TodoStore : Store<TodoModel>, ViewModel() {
    //Our state or "store"
    private val state: MutableLiveData<TodoModel> = MutableLiveData()

    //Default state that will be rendered initially
    private val initState = TodoModel(listOf(), Visibility.All())

    override fun dispatch(action: Action) {
        println("oldState = ${state.value}")
        println("action = $action")
        state.value = reduce(state.value, action)
        println("newState = ${state.value}")
        println("⬇️")
    }

    private fun reduce(state: TodoModel?, action: Action): TodoModel {
        val newState = state ?: initState

        return when (action) {
            is AddTodo -> newState.copy(todos = newState.todos.toMutableList().apply { add(Todo(action.text, action.id)) })
            is ToggleTodo -> newState.copy(
                    todos = newState.todos.map {
                        if (it.id == action.id) {
                            it.copy(completed = !it.completed)
                        } else it
                    } as MutableList<Todo>)
            is SetVisibility -> newState.copy(visibility = action.visibility)
        }
    }

    override fun subscribe(renderer: Renderer<TodoModel>, func: Function<TodoModel, TodoModel>) {
        renderer.render(Transformations.map(state, func))
    }
}