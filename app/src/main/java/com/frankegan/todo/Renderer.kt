package com.frankegan.todo

/**
 * Created by frankegan on 12/24/17.
 */
interface Renderer<T> {
    fun  render(model : T)
}