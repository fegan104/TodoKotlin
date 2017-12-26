package com.frankegan.todo

import android.support.test.InstrumentationRegistry
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    var uiThreadTestRule = UiThreadTestRule()
    val MOCK_TEXT = "mock text"

    @Test
    fun testAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.frankegan.todo", appContext.packageName)
    }

    @Test
    fun testAdd() {
        val mockStore = TodoStore()

        uiThreadTestRule.runOnUiThread({ mockStore.dispatch(AddTodo(MOCK_TEXT)) })
        assertEquals(mockStore.state.value?.todos?.get(0)?.text, MOCK_TEXT)
    }

    @Test
    fun testToggle() {
        val mockStore = TodoStore()

        uiThreadTestRule.runOnUiThread({ mockStore.dispatch(AddTodo(MOCK_TEXT, 100L)) })
        assertEquals(mockStore.state.value?.todos?.size, 1)

        uiThreadTestRule.runOnUiThread({ mockStore.dispatch(ToggleTodo(100L)) })
        assert(mockStore.state.value?.todos?.get(0)?.completed ?: false)
    }

    @Test
    fun testVisibility(){
        val mockStore = TodoStore()
        assert(mockStore.state.value?.visibility is All)
        uiThreadTestRule.runOnUiThread({ mockStore.dispatch(SetVisibility(Completed())) })
        assert(mockStore.state.value?.visibility is Completed)
    }
}
