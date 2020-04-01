package com.app.flickerimageview.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.flickerimageview.R
import com.app.flickerimageview.model.Photo
import com.app.flickerimageview.model.SearchItem
import com.app.flickerimageview.utils.loadImage
import kotlinx.android.synthetic.main.fragment_main.view.*

import kotlinx.android.synthetic.main.item_display.view.*

class DisplayListAdapter(private var displayList: ArrayList<Photo>, var favoritesList: ArrayList<Photo>) :
    RecyclerView.Adapter<DisplayListAdapter.DisplayViewHolder>() {

    private lateinit var listener:OnClickListner

    fun updateUsers(searchList: List<Photo>) {
        displayList.addAll(searchList)
        notifyDataSetChanged()
    }

    fun updateFavorites(searchList: List<Photo>){
        favoritesList.clear()
        favoritesList.addAll(searchList)
        notifyDataSetChanged()
    }

    fun addFavorite(item: Photo){
        favoritesList.add(item)
        if(displayList.contains(item))
        {
            notifyItemChanged(displayList.indexOf(item))
        }
    }

    fun removeFavorite(item: Photo){
        if(displayList.contains(item))
        {
            notifyItemChanged(displayList.indexOf(item))
        }
        favoritesList.remove(item)
    }

    fun setListener(onClickListener: OnClickListner){
        listener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = DisplayViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_display, parent, false)
    )

    override fun getItemCount() = displayList.size
    override fun onBindViewHolder(holder: DisplayViewHolder, position: Int) {
        holder.bind(displayList[position])
    }

    fun clear() {
        displayList.clear()
        notifyDataSetChanged()
    }

    inner class DisplayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.tv_title
        private val imageNews = view.image_movie
        private val isFavorite = view.iv_favorite
        fun bind(
            displayItem: Photo
        ) {
            title.text = displayItem.title
            imageNews.loadImage(displayItem)

            val toAdd: Boolean = if (favoritesList.size > 0 && favoritesList.contains(displayItem)) {
                isFavorite.setImageResource(R.drawable.ic_favorite_black_24dp)
                false
            } else {
                isFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                true
            }

            isFavorite.setOnClickListener {
                if(toAdd)
                    listener.onAdd(displayItem)
                else
                    listener.onRemove(displayItem)
                notifyItemChanged(adapterPosition)
            }

        }
    }

    interface OnClickListner {
        fun onAdd(item: Photo)
        fun onRemove(item: Photo)
    }
}