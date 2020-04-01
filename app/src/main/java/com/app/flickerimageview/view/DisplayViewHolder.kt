package com.app.flickerimageview.view

import android.view.ViewGroup
import com.app.flickerimageview.R
import com.app.flickerimageview.model.ListItem
import com.app.flickerimageview.model.Photo
import com.app.flickerimageview.utils.loadImage
import kotlinx.android.synthetic.main.item_display.view.*

class DisplayViewHolder(parent: ViewGroup) : BaseHolder(parent.inflate(R.layout.item_display)) {
    private val title = itemView.tv_title
    private val image = itemView.image_movie

    override fun bind(data: ListItem) {
        if(data is Photo) {
            title.text = data.title
            image.loadImage(data)
        }
    }
}
