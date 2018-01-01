package com.frankegan.todo

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.frankegan.todo.model.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.todo_item.view.*
import org.jetbrains.anko.selector

class HomeActivity : AppCompatActivity(), Renderer<TodoModel> {
    private lateinit var store: TodoStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        store = ViewModelProviders.of(this).get(TodoStore::class.java)
        store.subscribe(this, mapStateToProps)

        listView.adapter = TodoAdapter(this, listOf())
        listView.setOnItemClickListener({_,_,_, id -> store.dispatch(ToggleTodo(id)) })

        addButton.setOnClickListener { store.dispatch(AddTodo(editText.text.toString())) }
        fab.setOnClickListener { openDialog() }
    }

    override fun render(model: LiveData<TodoModel>) {
        model.observe(this, Observer { newState ->
            listView.adapter = TodoAdapter(this, newState?.todos ?: listOf())
        })
    }

    /**
     * We're filtering out all the todos not visible.
     */
    private val mapStateToProps = Function<TodoModel, TodoModel> {

        val keep: (Todo) -> Boolean = when (it.visibility) {
            is Visibility.All -> { _ -> true }
            is Visibility.Active -> { t: Todo -> !t.completed }
            is Visibility.Completed -> { t: Todo -> t.completed }
        }
        return@Function it.copy(todos = it.todos.filter { keep(it) })
    }

    private fun openDialog() {
        val options = resources.getStringArray(R.array.filter_options).asList()
        selector(getString(R.string.filter_title), options, { _, i ->
            val vis = when (i) {
                1 -> Visibility.Active()
                2 -> Visibility.Completed()
                else -> Visibility.All()
            }
            store.dispatch(SetVisibility(vis))
        })
    }

    class TodoAdapter(context: Context, val todos: List<Todo>) :
            ArrayAdapter<Todo>(context, 0, todos) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val view = convertView ?: LayoutInflater.from(context)
                    .inflate(R.layout.todo_item, parent, false)

            view.textView.text = todos[position].text
            view.checkBox.isChecked = todos[position].completed

            return view
        }

        override fun getItemId(position: Int): Long = todos[position].id
    }
}


