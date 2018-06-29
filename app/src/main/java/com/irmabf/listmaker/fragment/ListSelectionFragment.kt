package com.irmabf.listmaker.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.irmabf.listmaker.model.ListDataManager
import com.irmabf.listmaker.adapter.ListSelectionRecyclerViewAdapter
import com.irmabf.listmaker.R
import com.irmabf.listmaker.model.TaskList

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ListSelectionFragment : Fragment(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {


    //This creates a new ListDataManager as soon as the activity is created
    lateinit var listDataManager: ListDataManager

    lateinit var  listsRecyclerView: RecyclerView
    //1
    private var listener: OnListItemFragmentInteractionListener? = null

    interface OnListItemFragmentInteractionListener {
        fun onListItemClicked(list: TaskList)
    }
    //2
    companion object {
        fun newInstance() : ListSelectionFragment {
            val fragment = ListSelectionFragment()
            return fragment
        }
    }

    fun addList(list : TaskList) {

        listDataManager.saveList(list)

        val recyclerAdapter = listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter
        recyclerAdapter.addList(list)
    }

    fun saveList(list: TaskList) {
        listDataManager.saveList(list)
        updateLists()
    }

    private fun updateLists() {
        val lists = listDataManager.readLists()
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
    }

    //3
    /**
    *onAttach is the first lifecycle method run by a fragment.
     * This method is run when the fragment is first associated with an Activity,
     * which gives us a chance to set up anything required before the fragment
     * is created
     *
     * -> We assign the CONTEXT of the Fragment to the LISTENER variable
     * -> This CONTEXT will be the MainActivity that WILL CONTAIN THE FRAGMENT
    */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListItemFragmentInteractionListener) {
            listener = context
            listDataManager = ListDataManager(context)
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }
    /***
     * onCreate is used  when a fragment is in the process of being created
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * onCreateView is where the fragment acquires the layout
     * it must have in order to be presented within the actiivty*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_selection, container, false)
    }
    /***
     * This lifecycle method runs when the Activity the Fragment is attached to has
     * finished running onCreate()
     * This ensures we have an Activity to work and something to our widgets
     */

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val lists = listDataManager.readLists()
        view?.let {
            listsRecyclerView = it.findViewById<RecyclerView>(R.id.lists_recyclerview)
            listsRecyclerView.layoutManager = LinearLayoutManager(activity)
            listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
        }
    }

    /*
    * onDetach is the final lifecycle method to be called, is called when a fragment
    * is no longer attached to an activity*/
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun listItemClicked(list: TaskList) {
        listener?.onListItemClicked(list)
    }
}
