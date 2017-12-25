package com.frankegan.todo

/**
 * Created by frankegan on 12/24/17.
 */
interface Store<T> {

    var state: T

    fun dispatch(action: Action)

}