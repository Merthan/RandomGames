package com.gmail.me2development.randomgames

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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

    @Before
    fun before(){
        startKoin {
            modules(listOf(applicationModule))
        }
    }



    @After
    fun after(){
        stopKoin()
    }

    @Test
    fun checkIfListIsSorted(){

        with(viewModel.gameList.value){
            assert(!isNullOrEmpty())
            println(this)
        }


    }


}