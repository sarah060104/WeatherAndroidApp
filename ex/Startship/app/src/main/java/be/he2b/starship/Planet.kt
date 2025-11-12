package be.he2b.starship

import java.util.random.RandomGenerator
import kotlin.math.min
import kotlin.random.Random

class Planet(name : String, coordinates: Coordinates,
             val exploitationRate : Int, private var metalResources: Int)
    : SpaceObject(coordinates, name)
{
    fun mine() : Int{
        val quantity = min(metalResources, exploitationRate)
        if (quantity > 0) {
            metalResources -= quantity
        }
        return quantity
    }
    //session statique utilisé pour création de planet
    companion object{
        val syllableList = listOf<String>("ju", "pi", "ter", "ma", "rs", "sa", "turn","ve", "nus",
        "nep", "tu", "ne", "plu", "to", "er", "ra", "mi", "ro", "na", "li", "sa", "ta", "li",
        "on", "ce", "de", "xe", "bi", "zo", "ka", "lu", "xi")

        fun createRandomPlanet() : Planet{

            val coordinates = Coordinates.generateCoordinates()
            val exploitationRate : Int = Random.nextInt(10, 50)
            val metalResources : Int = Random.nextInt(10, 30)

            val s1 : String = syllableList.get(Random.nextInt(0, syllableList.size))
            val s2 : String = syllableList.get(Random.nextInt(0, syllableList.size))
            val s3 : String = syllableList.get(Random.nextInt(0, syllableList.size))

            val name = s1 + s2+ s3
            return Planet(name, coordinates, exploitationRate, metalResources)
        }

    }







}
