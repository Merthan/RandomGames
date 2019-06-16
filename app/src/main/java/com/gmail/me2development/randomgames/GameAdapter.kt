package com.gmail.me2development.randomgames

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmail.me2development.randomgames.databinding.LayoutGameItemCardviewBinding
import com.gmail.me2development.randomgames.model.Game
import androidx.databinding.BindingAdapter



class GameAdapter : ListAdapter<Game, GameAdapter.ViewHolder>(diffGameItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

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

private val diffGameItemCallback = object : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Game, newItem: Game) = oldItem.hasSameContent(newItem)
}

private fun Game.hasSameContent(gameItem: Game): Boolean {
    return (this.id == gameItem.id) && (this.rating == gameItem.rating)
}

@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}