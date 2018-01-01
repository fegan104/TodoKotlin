package com.frankegan.todo

import android.arch.core.util.Function
import com.frankegan.todo.model.Action

/**
 * Created by frankegan on 12/24/17.
 */
interface Store<T> {

    /**
     * This method applies the given action to the store in a pure functional manner.
     */
    fun dispatch(action: Action)

    /**
     * A Render can subscribe to our store to receive LiveData optionally
     * transformed by the provided Function.
     *
     * @param renderer The renderer that will receive updates form the store.
     * @param func An optional mapping of state to render props.
     */
    fun subscribe(renderer: Renderer<T>, func: Function<T, T> = Function { it })
}