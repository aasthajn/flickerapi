package com.app.flickerimageview.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.flickerimageview.model.ListItem

abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(data: ListItem)
}

fun ViewGroup.inflate(layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}
