package com.irmabf.listmaker.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.irmabf.listmaker.R

class ListItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    val taskTextView = itemView?.findViewById<TextView>(R.id.textview_task) as TextView
}
