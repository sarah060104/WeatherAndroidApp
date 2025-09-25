package be.he2b.starship

class Spaceship(startLocation : Planet){
    var location : SpaceObject = startLocation
        private set
    var fuel = 50.0
        private set
    var metalCargo = 0

    fun move(destination: SpaceObject){
        val distancePlanets : Double = this.location.coordinates.distanceTo(
            destination.coordinates)
        val requiredFuel = distancePlanets/10
        if (fuel<requiredFuel){
            throw Exception("Not enough fuel")
        }
        fuel -= requiredFuel

        location = destination
    }

    fun refuel(quantity : Double){
        if (quantity > 0) {
            fuel += quantity
        }
    }


    /**
     * gather metal resources from planet
     */
    fun gatherMetal(){
        if (location is Planet) {
            metalCargo += (location as Planet).mine()
        }
    }

}


