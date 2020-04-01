package com.app.flickerimageview.utils

import android.widget.ImageView
import androidx.constraintlayout.solver.Cache
import com.app.flickerimageview.R
import com.app.flickerimageview.model.Photo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(photo: Photo) {
    val url = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_m.jpg"
    val options = RequestOptions()
        .error(R.drawable.ic_cloud_off_black_24dp)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(this)
}