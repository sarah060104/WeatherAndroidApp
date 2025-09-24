package be.he2b.starship

class Spaceship(startLocation : Planet){
    var location : Planet = startLocation
        private set //??
    var fuel = 50.0
        private set //??
    var metalCargo = 0

    fun move(destination: SpaceObject){
        val distancePlanets : Double = this.location.coordinates.distanceTo(
            destination.coordinates)
        if (fuel <distancePlanets/10){
            println("Not enough fuel to go planet ${destination.name}")
        } else{
            changeCoordinates(destination)
            this.fuel = fuel - distancePlanets/10
            println("Fuel quantity left : $fuel L")
        }
    }
    fun changeCoordinates(destination: SpaceObject){
        this.location.coordinates = destination.coordinates
    }

    fun refuel(quantity : Double){
        if (quantity > 0) {
            fuel += quantity
        }
        println("refuel : + $fuel L")
    }

}


