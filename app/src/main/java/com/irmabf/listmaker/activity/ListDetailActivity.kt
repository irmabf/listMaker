package com.irmabf.listmaker.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.widget.EditText
import com.irmabf.listmaker.adapter.ListItemsRecyclerViewAdapter
import com.irmabf.listmaker.R
import com.irmabf.listmaker.model.TaskList
import kotlinx.android.synthetic.main.fragment_list_detail.*

class ListDetailActivity : AppCompatActivity() {

    lateinit var list: TaskList

    lateinit var listItemsRecyclerView: RecyclerView

    lateinit var addTaskButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)

        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)

        title = list.name

        listItemsRecyclerView = findViewById<RecyclerView>(R.id.list_items_reyclerview)
        listItemsRecyclerView.adapter = ListItemsRecyclerViewAdapter(list)
        list_items_reyclerview.layoutManager = LinearLayoutManager(this)

        addTaskButton = findViewById<FloatingActionButton>(R.id.add_task_button)
        addTaskButton.setOnClickListener {
            showCreateTaskDialog()
        }
    }

    private fun showCreateTaskDialog() {
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT

        AlertDialog.Builder(this)
                .setTitle(R.string.task_to_add)
                .setView(taskEditText)
                .setPositiveButton(R.string.add_task, { dialog, _ ->
                    val task = taskEditText.text.toString()
                    list.tasks.add(task)
                    val recyclerAdapter = listItemsRecyclerView.adapter as ListItemsRecyclerViewAdapter
                    recyclerAdapter.notifyItemInserted(list.tasks.size)
                    dialog.dismiss()
                })
                .create()
                .show()
    }
    override fun onBackPressed() {
        val bundle = Bundle()
        bundle.putParcelable(MainActivity.INTENT_LIST_KEY, list)

        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }
}
