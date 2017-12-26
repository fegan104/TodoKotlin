package com.frankegan.todo

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by frankegan on 12/24/17.
 */
class TodoStore : Store<List<Todo>>, ViewModel() {
    //our state
    override var state: MutableLiveData<List<Todo>> = MutableLiveData()

    val initState = listOf<Todo>()

    override fun dispatch(action: Action) {
        println("oldState = ${state.value}")
        println("action = ${action}")
        state.value = reduce(state.value, action)
        println("newState = ${state.value}")
        println("⬇️")
    }

    private fun reduce(state: List<Todo>?, action: Action): List<Todo> {
        val newState = state ?: initState

        return when (action) {
            is AddTodo -> newState.toMutableList().apply { add(Todo(action.text, action.id)) }
            is ToggleTodo -> newState.map { t ->
                if (t.id == action.id) {
                    t.copy(completed = !t.completed)
                } else t
            } as MutableList<Todo>
        }
    }
}