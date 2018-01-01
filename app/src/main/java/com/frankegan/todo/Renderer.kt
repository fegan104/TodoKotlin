package com.frankegan.todo

import android.arch.lifecycle.LiveData

/**
 * Created by frankegan on 12/24/17.
 */
interface Renderer<T> {

    /**
     * To render a ui you need a model.
     */
    fun render(model: LiveData<T>)
}