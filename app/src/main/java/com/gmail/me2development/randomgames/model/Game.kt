package com.gmail.me2development.randomgames.model

import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.gmail.me2development.randomgames.BR


data class Game(
    val id:Int,
    val name:String,
    val imageResource:Int
): BaseObservable(){

    var rating:Float=0.toFloat()
    @Bindable get
    set(value){
        field=value
        notifyPropertyChanged(BR.rating)
    }

}
