package com.frankegan.todo

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), Renderer<List<Todo>> {
    lateinit var adapter: ArrayAdapter<Todo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        adapter = ArrayAdapter<Todo>(this, R.layout.todo_item)
        listView.adapter = adapter
    }

    override fun render(model: List<Todo>) {
        adapter.addAll(model)
    }

    class TodoAdapter(context: Context, layout: Int) : ArrayAdapter<Todo>(context, layout) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            return super.getView(position, convertView, parent)
        }
    }
}


