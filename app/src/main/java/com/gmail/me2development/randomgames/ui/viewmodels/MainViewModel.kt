package com.gmail.me2development.randomgames.ui.viewmodels

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.me2development.randomgames.data.Repository
import com.gmail.me2development.randomgames.model.Game
import kotlinx.coroutines.*
import kotlin.random.Random



class MainViewModel(private val repo:Repository): ViewModel(), CoroutineScope {

    //Variables for using Coroutines
    private var job: Job? = null
    override val coroutineContext = Dispatchers.Main

    //Describes the random setting of ratings within the game list, turned off by default/start
    val isShuffling=ObservableBoolean(false)

    //Create LiveData with the hardcoded list data
    val gameList = MutableLiveData<List<Game>>().also {
        it.value = repo.getData()//Initial value
    }

    init {
        gameList.value!!.forEach { game ->
            //Adding onPropertyChanged to tell the objects that the LiveData itself is
            // updated whenever a property of the object changes (Rating in this case)
            game.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback(){
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    //results in a callback in the observe method of gameList
                    gameList.postValue(gameList.value!!.sortedByRating())//Also sorts the list before observe is called
                }
            })
        }
    }

    //Cancel random rating/shuffling
    private fun stopShuffling() = job?.cancel()

    //Start cancelable job for rating/shuffling
    private fun startShuffling() {
        job = launch {
            while (true) {
                delaySomewhereBetween(1,5)
                //Number between 0 and 10 halfed to get values like 5 or 4.5 that the Ratingbar can show
                gameList.value!!.random().rating=(Random.nextInt(0,10)/2.0).toFloat()
            }
        }
    }

    //Method called by FAB onClick (Databinding)
    fun changeShufflingState(){
        with(isShuffling.get()){
            isShuffling.set(!this)//Changes drawable as well due to being Observable and being Data-"bound" ;D
            if(this)
                stopShuffling()
            else
                startShuffling()
        }
    }

    //Random delay between the specified seconds, calling with 1 and 3 would lead to 1, 2 or 3 seconds
    private suspend fun delaySomewhereBetween(startInSeconds:Int, endInSeconds:Int){
        //delay takes milliseconds, so a random number between the parameters times 1000
        delay((startInSeconds..endInSeconds).random().times(1000).toLong())
    }

}

//Sorts list by rating
fun List<Game>.sortedByRating()=sortedBy { game -> -game.rating }
