package com.gmail.me2development.randomgames

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.me2development.randomgames.data.Repository
import com.gmail.me2development.randomgames.model.Game
import kotlinx.coroutines.*
import kotlin.random.Random

class MainViewModel(private val repo:Repository): ViewModel(), CoroutineScope {



    //Describes the random setting of ratings within the game list, turned off by default/start
    val isShuffling=ObservableBoolean(false)

    //Create LiveData with the hardcoded list data
    val gameList = MutableLiveData<List<Game>>().also {
        it.value = repo.getData()//Initial value
        it.value!!.forEach { game ->

            //Adding onPropertyChanged to tell the objects that the LiveData itself is updated whenever a property of the object changes (Rating in this case)

            game.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    //results in a callback in the observe method of gameList
                    it.postValue(it.value)
                }
            })

        }
    }


    private var job: Job? = null
    override val coroutineContext = Dispatchers.Main

    private fun startShuffling() {
        job = launch {
            while (true) {
                delaySomewhereBetween(1,5)
                gameList.value?.random()?.rating=(Random.nextInt(0,10)/2.0).toFloat()
            }
        }
    }

    private fun stopShuffling() = job?.cancel()


    fun changeShufflingState(){
        if(isShuffling.get()){
            stopShuffling()
            isShuffling.set(false)//Changes drawable as well due to being Observable and being Data-"bound" ;D
        }else{
            startShuffling()
            isShuffling.set(true)//Changes drawable as well due to being Observable and being Data-"bound" ;D
        }
    }

    //Random delay between the specified seconds, calling with 1 and 3 would lead to 1, 2 or 3 seconds
    private suspend fun delaySomewhereBetween(startInSeconds:Int, endInSeconds:Int){
        delay((startInSeconds..endInSeconds).random().times(1000).toLong())
    }

}
