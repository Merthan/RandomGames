package com.gmail.me2development.randomgames.data

import com.gmail.me2development.randomgames.R
import com.gmail.me2development.randomgames.model.Game


class Repository{
    //Simple Implementation, in theory interfaces/abstractions could for example have been used here
    fun getData()= hardCodedGames

}
//List of Hardcoded Game objects, Drawable is set using Databinding
private val hardCodedGames=listOf(
    Game(0, "Prey", R.drawable.vector_asset_launcher_default),
    Game(1, "SOMA", R.drawable.vector_asset_launcher_blue),
    Game(2, "Far Cry 3", R.drawable.vector_asset_launcher_green),
    Game(3, "Far Cry 4", R.drawable.vector_asset_launcher_red),
    Game(4, "Borderlands 2", R.drawable.vector_asset_launcher_purple),
    Game(5, "Borderlands TPS", R.drawable.vector_asset_launcher_red),
    Game(6, "Borderlands 3", R.drawable.vector_asset_launcher_violet),
    Game(7, "TFTB", R.drawable.vector_asset_launcher_blue),
    Game(8, "Super Smash Bros Ultimate", R.drawable.vector_asset_launcher_teal),
    Game(9, "Prey : Mooncrash", R.drawable.vector_asset_launcher_default)
)