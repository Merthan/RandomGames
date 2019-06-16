package com.gmail.me2development.randomgames.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.me2development.randomgames.R
import com.gmail.me2development.randomgames.databinding.ActivityMainBinding
import com.gmail.me2development.randomgames.model.GameAdapter
import com.gmail.me2development.randomgames.ui.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.viewmodel.ext.android.viewModel


/*
Assignment for Android Developer
1. Pick your top 10 fav books, movies, video games, food, or whatever you wish. Build an
Android app that lists these items. You don’t need a backend service to load this list of items.
Use sensible hardcoding.
2. Create a rating system where you can rate each item. Order the list by the highest rated
item to lowest rated item. Re-order the list, if needed, when user ranks an item.
3. Using RxJava or similar technique, randomly rate items at random times, also re-ordering
the list as necessary. To elaborate this more put a button called RANDOM RATING and on
click of this button code will start rating random item at random time with random rating. And
on same button press again it will stop random rating.
Notes:
1. Feel free to use any UI elements or library.
2. Focus on test driven approach while coding this assignment.
3. Focus on good development practices like proper architecture, dependency injection, data
binding etc.
4. No need to write UI tests but a good unit test coverage is desired.
5. Git repo with commit messages will be a plus.

 */
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()//Injection by Koin

    //All of the animation logic is inside of the Adapter or rather the ListAdapter class
    private val adapter: GameAdapter by lazy { GameAdapter() }

    private val layoutManager by lazy { LinearLayoutManager(this@MainActivity) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        setupDataBinding()

        setupMessageWhenShufflingStateChanged()

        setupRecyclerView()

        //When a change has been detected, tell adapter that the list has changed
        mainViewModel.gameList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun setupRecyclerView(){
        gamesRecyclerView.layoutManager=layoutManager
        gamesRecyclerView.adapter=adapter
    }

    private fun setupDataBinding(){
        with(DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)){
            lifecycleOwner=this@MainActivity
            viewModel=mainViewModel
        }
    }

    private fun setupMessageWhenShufflingStateChanged(){

        mainViewModel.isShuffling.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {

                val shuffling=(sender as ObservableBoolean).get()//Get boolean value by casting
                //Set Toast message depending on shuffling state
                val message=if(shuffling) R.string.toast_message_shuffling else R.string.toast_message_not_shuffling
                Toast.makeText(this@MainActivity,message, Toast.LENGTH_SHORT).show()
            }
        })
    }


}
