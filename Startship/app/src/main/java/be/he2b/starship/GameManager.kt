package be.he2b.starship

import android.app.GameState

class GameManager (spaceship: Spaceship, planets : List<Planet>,
gameState: GameState){

    val planets : List<Planet> = planets
    val spaceShip : Spaceship = spaceship
    var gameState : GameState = gameState

    fun startGame(gameLoop : () -> Unit){

    }
    fun moveToDestination(destination : Planet){
        spaceShip.move(destination)
    }

    fun mine(){
        spaceShip.mine()
        spaceShip.location.getResourceTaken()

    }
    fun exchangeMetalForFuel(){
        spaceShip.refuel()
    }
}