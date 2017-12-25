package com.frankegan.todo

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addTodo(){
        val MOCK_TEXT = "mock text"
        val mockStore = TodoStore()

        assertEquals(mockStore.state.size, 0)
        mockStore.dispatch(AddTodo(Todo(MOCK_TEXT)))
        assertEquals(mockStore.state.size, 1)
        assertEquals(mockStore.state[0].text, MOCK_TEXT)
    }

    @Test
    fun toggleTodo(){
        val MOCK_TEXT = "mock text"
        val mockStore = TodoStore()

        mockStore.dispatch(AddTodo(Todo(MOCK_TEXT)))
        assertEquals(mockStore.state.size, 1)

        mockStore.dispatch(ToggleTodo(Todo(MOCK_TEXT)))
        assert(mockStore.state[0].completed)
    }

}
