package com.gmail.me2development.randomgames

import com.gmail.me2development.randomgames.model.Game
import com.gmail.me2development.randomgames.model.GameException
import org.junit.Assert.assertFalse

import org.junit.Test
import org.koin.test.KoinTest


class GameTest : KoinTest {

    //Inline Game Exception helper function
    private inline fun isThrowingGameException(block: () -> Unit):Boolean{
        return try{
            block()
            false
        }catch (E: GameException){
            //Stacktraces not printed to keep the tests visible
            true
        }
    }


    //Test created to check what happens when two games have the same rating, a edge condition
    @Test
    fun checkIfWrongGameCreationFails(){

        assert(//Empty name
            isThrowingGameException { Game(0,"",0)  }
        )
        assert(//Blank name
            isThrowingGameException { Game(1,"",0)  }
        )
        assert(//Negative Id
            isThrowingGameException { Game(-1,"",0)  }
        )
    }

    @Test
    fun checkIfSettingWrongGameRatingFails(){
        val game=Game(0,"ValidName",0)

        assertFalse(//Correct rating
            isThrowingGameException {
                game.rating=2.5f
            }
        )

        assert(//False rating
            isThrowingGameException {
                game.rating=7f
            }
        )

        assert(//Not .5 and not Integer
            isThrowingGameException {
                game.rating=3.3f
            }
        )

    }


}