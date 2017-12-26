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


class HomeActivity : AppCompatActivity(), Renderer<List<Todo>> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val store = ViewModelProviders.of(this).get(TodoStore::class.java)
        store.state.observe(this, Observer { newState -> render(newState) })

        listView.adapter = TodoAdapter(this, arrayOf())
        listView.setOnItemClickListener({ l, v, p, id -> store.dispatch(ToggleTodo(id)) })

        addButton.setOnClickListener { store.dispatch(AddTodo(editText.text.toString())) }
    }

    override fun render(newState: List<Todo>?) {
        if (newState == null) return

        listView.adapter = TodoAdapter(this, newState.toTypedArray())
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


