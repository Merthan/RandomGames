package com.gmail.me2development.randomgames.model

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmail.me2development.randomgames.databinding.LayoutGameItemCardviewBinding


//Class extends ListAdapter which is a (native) solution for getting Recyclerview animations when items change
//In this case, We have Games which have Ratings that change
class GameAdapter : ListAdapter<Game, GameAdapter.ViewHolder>(diffGameItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Inflating and Databinding of GameItem
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutGameItemCardviewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(private val binding: LayoutGameItemCardviewBinding) : RecyclerView.ViewHolder(binding.root) {
        //Very efficient/short way of databinding recyclerview items!
        fun bind(item: Game) {
            binding.game = item
            binding.executePendingBindings()
        }
    }
}

//Required for DiffUtil (Recyclerview Animations)
private val diffGameItemCallback = object : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game) = (oldItem.id == newItem.id)
    override fun areContentsTheSame(oldItem: Game, newItem: Game) = oldItem.hasSameContent(newItem)
}


private fun Game.hasSameContent(gameItem: Game): Boolean {
    //Required for DiffUtil (Recyclerview Animations)
    return (this.id == gameItem.id) && (this.rating == gameItem.rating) && (this.name==gameItem.name)
}

//Used to set resources using android:src attribute in XML,
// otherwise the Resource Int gets set as a color instead like #FFF
@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}