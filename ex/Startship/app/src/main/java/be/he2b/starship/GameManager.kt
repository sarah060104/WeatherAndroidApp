package be.he2b.starship

import android.app.GameState

class GameManager (spaceship: Spaceship, planets : List<Planet>,
                   station: Station,
gameState: GameState){

    val planets : List<Planet> = planets
    val spaceShip : Spaceship = spaceship
    var gameState : GameState = gameState
    val station: Station = station

    fun startGame(gameLoop : () -> Unit){

    }
    fun moveToDestination(destination : Planet){
        spaceShip.move(destination)
    }

    fun mine(){
        spaceShip.gatherMetal()

    }
    fun exchangeMetalForFuel(){

    }
}