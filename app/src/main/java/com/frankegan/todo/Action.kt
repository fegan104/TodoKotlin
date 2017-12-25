package com.frankegan.todo

/**
 * Created by frankegan on 12/24/17.
 */
sealed abstract class Action

data class AddTodo (val payload: Todo): Action()

data class ToggleTodo(val payload: Todo) : Action()