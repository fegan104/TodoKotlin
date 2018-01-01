package com.frankegan.todo.model

/**
 * Created by frankegan on 12/24/17.
 */
sealed class Action

var counter = 0L
data class AddTodo (val text: String, val id : Long = counter++): Action()

data class ToggleTodo(val id: Long) : Action()

sealed class Visibility {
    class All : Visibility()
    class Active : Visibility()
    class Completed : Visibility()
}

data class SetVisibility(val visibility: Visibility) : Action()