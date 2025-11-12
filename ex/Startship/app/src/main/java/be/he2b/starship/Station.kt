package be.he2b.starship

import kotlin.random.Random

class Station (name : String, location : Coordinates,
               val metalPrice: Double) : SpaceObject(
    location, name) {

    fun exchangeMetalForFuel(metalQuantity: Int): Double {
        if (metalQuantity <= 0) {
            throw Exception(
                "Metal quantity must be greater than zero" +
                        ", $metalQuantity"
            )
        }
        val fuelQuantity = metalQuantity * this.metalPrice
        return fuelQuantity
    }

    companion object {
        fun createRandomStation(): Station {
            val stationNumber = Random.nextInt(1, 1000)
            val metalPrice = Random.nextDouble(0.5, 2.0)
            val name = "Station_X$stationNumber"
            val coordinates = Coordinates.generateCoordinates()

        return Station(name, coordinates,metalPrice )
        }
    }
}