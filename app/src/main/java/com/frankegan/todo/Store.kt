package com.frankegan.todo

import android.arch.lifecycle.MutableLiveData

/**
 * Created by frankegan on 12/24/17.
 */
interface Store<T> {

    var state: MutableLiveData<T>

    fun dispatch(action: Action)

}