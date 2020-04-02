package com.app.flickerimageview.view

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.flickerimageview.model.ListItem
import com.app.flickerimageview.model.Photo
import kotlin.collections.arrayListOf as arrayListOf1

class DisplayListAdapter(
) : RecyclerView.Adapter<BaseHolder>() {

    private var displayList: ArrayList<ListItem> = arrayListOf1()

    var list: List<ListItem>
        get() = displayList
        set(value) {
            displayList.clear()
            displayList.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return if (viewType == ITEM_TYPE_DISPLAY) {
            DisplayViewHolder(parent)
        } else {
            FooterViewHolder(parent)
        }
    }

    override fun getItemCount() = displayList.size

    override fun getItemViewType(position: Int): Int {
        return if (displayList[position] is Photo) ITEM_TYPE_DISPLAY else ITEM_TYPE_FOOTER
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bind(displayList[position])
    }

    companion object {
        const val ITEM_TYPE_DISPLAY = 1
        const val ITEM_TYPE_FOOTER = 0
    }
}