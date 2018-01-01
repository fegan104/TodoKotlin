package com.frankegan.todo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.frankegan.todo.model.*
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
    var activityRule = ActivityTestRule<HomeActivity>(HomeActivity::class.java)
    val MOCK_TEXT = "mock text"

    @Test
    fun testAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.frankegan.todo", appContext.packageName)
    }

    @Test
    fun testAdd() {
        val mockStore = ViewModelProviders.of(activityRule.activity).get(TodoStore::class.java)

        activityRule.runOnUiThread({
            mockStore.dispatch(AddTodo(MOCK_TEXT))
            mockStore.subscribe(object : Renderer<TodoModel> {
                override fun render(model: LiveData<TodoModel>) {
                    model.observe(activityRule.activity, Observer { newState ->
                        assertEquals(MOCK_TEXT, newState?.todos?.get(0)?.text)
                    })
                }
            })
        })
    }

    @Test
    fun testToggle() {
        val mockStore = ViewModelProviders.of(activityRule.activity).get(TodoStore::class.java)

        activityRule.runOnUiThread({
            mockStore.dispatch(AddTodo(MOCK_TEXT, 100L))
            mockStore.dispatch(ToggleTodo(100L))
            mockStore.subscribe(object : Renderer<TodoModel> {
                override fun render(model: LiveData<TodoModel>) {
                    model.observe(activityRule.activity, Observer { newState ->
                        assert(newState?.todos?.get(0)?.completed ?: false)
                    })
                }
            })
        })
    }

    @Test
    fun testVisibility() {
        val mockStore = ViewModelProviders.of(activityRule.activity).get(TodoStore::class.java)

        activityRule.runOnUiThread({
            mockStore.dispatch(AddTodo(MOCK_TEXT))
            mockStore.subscribe(object : Renderer<TodoModel> {
                override fun render(model: LiveData<TodoModel>) {
                    model.observe(activityRule.activity, Observer { newState ->
                        assert(newState?.visibility is Visibility.All)
                    })
                }
            })
            mockStore.dispatch(SetVisibility(Visibility.Completed()))
            mockStore.subscribe(object : Renderer<TodoModel> {
                override fun render(model: LiveData<TodoModel>) {
                    model.observe(activityRule.activity, Observer { newState ->
                        assert(newState?.visibility is Visibility.Completed)
                    })
                }
            })
        })
    }
}
