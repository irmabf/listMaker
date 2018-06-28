package com.irmabf.listmaker

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.FrameLayout

import kotlinx.android.synthetic.main.activity_list.*

class MainActivity : AppCompatActivity(), ListSelectionFragment.OnListItemFragmentInteractionListener {

    private  var fragmentContainer: FrameLayout? = null

    private var listSelectionFragment: ListSelectionFragment = ListSelectionFragment.newInstance()

    override fun onListItemClicked(list: TaskList) {
        showListDetail(list)
    }

    companion object {
        val INTENT_LIST_KEY  = "list"
        val LIST_DETAIL_REQUEST_CODE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        fragmentContainer = findViewById(R.id.fragment_container)

        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, listSelectionFragment)
                .commit()

        fab.setOnClickListener { view ->
            showCreateListDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCreateListDialog() {
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)

        val builder = AlertDialog.Builder(this)
        val listTitleEditText =  EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        builder.setPositiveButton(positiveButtonTitle, { dialog, i ->

            val list = TaskList(listTitleEditText.text.toString())
           listSelectionFragment.addList(list)

            dialog.dismiss()
            showListDetail(list)
        })
        builder.create().show()
    }

    private fun showListDetail(list: TaskList) {
        val listDetailIntent =
                Intent(this, ListDetailActivity::class.java)
        listDetailIntent.putExtra(INTENT_LIST_KEY, list)
        //startActivity(listDetailIntent)
        startActivityForResult(listDetailIntent, LIST_DETAIL_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data:
    Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 1
        if (requestCode == LIST_DETAIL_REQUEST_CODE) {
            // 2
            data?.let {
                // 3
                listSelectionFragment.saveList(data.getParcelableExtra<TaskList>(INTENT_LIST_KEY))
            }
        }
    }


}
