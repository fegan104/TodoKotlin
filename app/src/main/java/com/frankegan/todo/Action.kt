package com.frankegan.todo

/**
 * Created by frankegan on 12/24/17.
 */
sealed class Action

var counter = 0L
data class AddTodo (val text: String, val id : Long = counter++): Action()

data class ToggleTodo(val id: Long) : Action()