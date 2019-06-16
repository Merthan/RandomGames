package com.gmail.me2development.randomgames.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.gmail.me2development.randomgames.BR

//Game is of Type BaseObservable to notify the UI/LiveData of Rating changes,
//Which is why only Rating has a custom setter

data class Game(val id:Int, val name:String, val imageResource:Int): BaseObservable(){

    init {
        //Checking if one of the 2 conditions is true
        val message=when{
            id < 0 -> "GameId must be bigger than 0"
            name.isBlank() -> "GameName must not be empty"
            else -> null
        }
        message?.let { 
            throw GameException(it)//Called ONLY when message is not null due to ?.let
        }
            
    }

    var rating:Float=0.toFloat()
    @Bindable get
    set(value){
        if(!validRatings.contains(value.toDouble())){
            throw GameException("Please Specify a rating between 0 and 5," +
                    " either Integers or .5 (like 4.5, excluding 5.5) numbers are accepted")
        }
        //If not, set value and notify (Observable)
        field=value
        notifyPropertyChanged(BR.rating)
    }

}
private val validRatings=listOf(0.0,0.5,1.0,1.5,2.0,2.5,3.0,3.5,4.0,4.5,5.0)
