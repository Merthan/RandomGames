package com.gmail.me2development.randomgames

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gmail.me2development.randomgames.di.applicationModule
import com.gmail.me2development.randomgames.model.Game
import com.gmail.me2development.randomgames.ui.viewmodels.MainViewModel
import com.gmail.me2development.randomgames.ui.viewmodels.sortedByRating
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject




class MainViewModelTest : KoinTest{
    private val viewModel by inject<MainViewModel>()

    //Added to allow Viewmodel/LiveData access
    @Rule @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    //Helper function to create a game without drawable but with a rating, only used here
    private fun createGame(id:Int,rating:Float,name:String=id.toString())= Game(id,name,0)
        .also { it.rating=rating }

    @Before
    fun before(){
        startKoin {
            modules(listOf(applicationModule))
        }
        if(viewModel.isShuffling.get()){
            viewModel.changeShufflingState()//Reset to false if state is true
        }
    }

    @After
    fun after(){
        stopKoin()
    }

    @Test
    fun checkIfAGameListIsSortedAfterCallingSortMethod(){

        val unsortedList=listOf(
            createGame(0,4.5f),
            createGame(1,4.0f),
            createGame(2,3.5f),
            createGame(3,5.0f),
            createGame(4,1f)
        )

        val manuallySortedList= listOf(
            createGame(3,5.0f),
            createGame(0,4.5f),
            createGame(1,4.0f),
            createGame(2,3.5f),
            createGame(4,1f)
        )

        //Thanks to Kotlin, == works here (not related to object references but to content)
        assert(unsortedList.sortedByRating() == manuallySortedList)
    }

    @Test
    fun checkIfShufflingStateMatchesExpectedValues(){
        assert(!viewModel.isShuffling.get())//At beginning, should not be shuffling
        viewModel.changeShufflingState()
        assert(viewModel.isShuffling.get())//After method call, should be true/shuffling
        viewModel.changeShufflingState()
        assert(!viewModel.isShuffling.get())//And again, should be false
    }

    @Test
    fun checkIfGameListFromRepositoryIsNeitherNullOrEmpty(){
        assert(!viewModel.gameList.value.isNullOrEmpty())
    }

    //Test created to check what happens when two games have the same rating, a edge condition
    @Test
    fun checkSortingWithEqualRatingsInAList(){

        val unsortedList=listOf(
            createGame(0,4.5f),
            createGame(1,4.5f),
            createGame(2,3.5f),
            createGame(3,5.0f),
            createGame(4,5.0f),
            createGame(5,1f)
        )
        val sortedList=unsortedList.sortedByRating()

        //If element at a position has value x, the element at position x+1 has to have the same or a smaller value
        var nextRatingWasBigger=false
        sortedList.forEachIndexed { index, game ->
            if(index!=0){//Jump over first value
                if(sortedList[index-1].rating<game.rating){//Check if previous value wasn't bigger or equal
                    nextRatingWasBigger=true
                }
            }
        }

        assert(!nextRatingWasBigger)
    }





}