package be.he2b.starship

import kotlin.math.pow
import kotlin.math.sqrt

class Coordinates (x: Double, y: Double, z: Double) {

    val x : Double = x
    val y : Double = y
    val z : Double = z

    fun distanceTo(other: Coordinates): Double {
        return sqrt((this.x - other.x).pow(2) +
                (this.y - other.y).pow(2) +
                (this.z - other.z).pow(2));
    }
}