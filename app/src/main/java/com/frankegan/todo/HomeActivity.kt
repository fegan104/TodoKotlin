package com.frankegan.todo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.todo_item.view.*
import org.jetbrains.anko.selector


class HomeActivity : AppCompatActivity(), Renderer<TodoModel> {
    lateinit var store: TodoStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        store = ViewModelProviders.of(this).get(TodoStore::class.java)
        store.state.observe(this, Observer { newState -> render(newState) })

        listView.adapter = TodoAdapter(this, arrayOf())
        listView.setOnItemClickListener({ l, v, p, id -> store.dispatch(ToggleTodo(id)) })

        addButton.setOnClickListener { store.dispatch(AddTodo(editText.text.toString())) }
        fab.setOnClickListener { openDialog() }
    }

    override fun render(newState: TodoModel?) {
        if (newState == null) return

        val keep: (Todo) -> Boolean = when (newState.visibility) {
            is All -> { _ -> true }
            is Active -> { t: Todo -> !t.completed }
            is Completed -> {t: Todo -> t.completed }
        }

        listView.adapter = TodoAdapter(this,
                newState.todos.filter { keep(it) }.toTypedArray())
    }

    fun openDialog() {
        val options = resources.getStringArray(R.array.filter_options).asList()
        selector(getString(R.string.filter_title), options, { d, i ->
            val vis = when (i) {
                1 -> Active()
                2 -> Completed()
                else -> All()
            }
            store.dispatch(SetVisibility(vis))
        })
    }

    class TodoAdapter(context: Context, val todos: Array<Todo>) :
            ArrayAdapter<Todo>(context, 0, todos) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val view = if (convertView == null) {
                LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false)
            } else convertView


            view.textView.text = todos[position].text
            view.checkBox.isChecked = todos[position].completed

            return view
        }

        override fun getItemId(position: Int): Long {
            return todos[position].id
        }
    }
}


