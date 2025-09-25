package be.he2b.starship

fun main() {


    val planet0 = Planet("Earth", Coordinates(0.0, 0.0, 0.0), 10, 1000)
    val planet1 = Planet("Mars", Coordinates(-100.0, -100.0, 10.0), 5, 500)
    val station = Station("X5445", Coordinates(-100.0, -100.0, 10.0), 5.0,)
    val planet2 = Planet("Jupiter", Coordinates(400.0, 400.0, 200.0), 8, 800)


    val spaceship = Spaceship(planet0)
    try {
        var minedMetal = planet0.getResourceTaken()
        spaceship.metalCargo += minedMetal
// move to Mars
        spaceship.move(planet1)
// extract some metal form Mars
        minedMetal = planet1.getResourceTaken()
        spaceship.metalCargo += minedMetal
// move to the space station
        spaceship.move(station)
// refuel
        val fuel = station.exchangeMetalForFuel(spaceship.metalCargo)
        spaceship.refuel(fuel)
// move to Jupiter
        spaceship.move(planet2)
    } catch (e: Exception) {
        println(e.message)

    }


}

fun displayWelcomeMessage(name: String?) { // point 3
    val upperCaseName = name?.uppercase() //type String comme le nom ou null
    println("$upperCaseName, welcome to Starship")
}


