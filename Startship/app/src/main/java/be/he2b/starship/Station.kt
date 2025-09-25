package be.he2b.starship

class Station (name : String, location : Coordinates,
               val metalPrice: Double) : SpaceObject(
    location, name){

    fun exchangeMetalForFuel( metalQuantity : Int) : Double{
        if(metalQuantity <= 0){
            throw Exception("Metal quantity must be greater than zero" +
                    ", $metalQuantity")
        }
        val fuelQuantity = metalQuantity * this.metalPrice
        return fuelQuantity
    }
}