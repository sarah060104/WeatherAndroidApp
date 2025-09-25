package be.he2b.starship

import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class Coordinates (x: Double, y: Double, z: Double) {

    val x : Double = x
    val y : Double = y
    val z : Double = z

    fun distanceTo(other: Coordinates): Double {
        return sqrt((this.x - other.x).pow(2) +
                (this.y - other.y).pow(2) +
                (this.z - other.z).pow(2));
    }

    override fun toString(): String {
        return "Coordinates(x=$x, y=$y, z=$z)"
    }

    companion object{
        fun generateCoordinates() : Coordinates{
            val x : Double = Random.nextDouble(-200.0, 200.0)
            val y : Double = Random.nextDouble(-200.0, 200.0)
            val z : Double = Random.nextDouble(-200.0, 200.0)

            return Coordinates(x, y, z)
        }
    }




}