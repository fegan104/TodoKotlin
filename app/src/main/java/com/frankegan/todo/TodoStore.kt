package com.frankegan.todo

import android.arch.lifecycle.ViewModel

/**
 * Created by frankegan on 12/24/17.
 */
class TodoStore : Store<MutableList<Todo>>, ViewModel() {
    //our state
    override var state: MutableList<Todo> = mutableListOf()

    override fun dispatch(action: Action) {
        state = reduce(state, action)
    }

    fun reduce(state: MutableList<Todo>, action: Action): MutableList<Todo> {
        return when (action) {
            is AddTodo -> state.toMutableList().apply { add(action.payload) }
            is ToggleTodo -> state.map { t ->
                if (t == action.payload) {
                    t.copy(completed = !t.completed)
                } else t
            } as MutableList<Todo>
            else -> state
        }
    }
}