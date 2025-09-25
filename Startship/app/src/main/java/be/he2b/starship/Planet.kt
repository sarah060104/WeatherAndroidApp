package be.he2b.starship

class Planet(name : String, coordinates: Coordinates,
             metalResources: Int, exploratioRate : Int)
    : SpaceObject(coordinates, name)
{

    override val name : String = name
    override var coordinates : Coordinates = coordinates
    var metalResources : Int = metalResources
    val exploratioRate : Int = exploratioRate

    fun getResourceTaken() : Int{
        val metalMine = metalResources
        metalResources = 0
        return metalMine
    }




}
