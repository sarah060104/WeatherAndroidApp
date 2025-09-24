import be.he2b.starship.Coordinates
import be.he2b.starship.Planet
import be.he2b.starship.Spaceship

fun main(){
    print("Your name is : ..")
    val name : String = readln();
    displayWelcomeMessage(name)
    val planetList : List<Planet> = listOf(
        Planet("Earth", Coordinates(0.0, 0.0, 0.0), 10, 1000),
        Planet("Mars", Coordinates(-100.0, -100.0, 10.0), 5, 500),
        Planet("Jupiter", Coordinates(400.0, 400.0, 200.0), 8, 800)
    )
    val spaceship : Spaceship = Spaceship (planetList.get(0))
    spaceship.move(planetList.get(1))
    spaceship.move(planetList.get(2))
}

fun displayWelcomeMessage(name : String?){ // point 3
    val upperCaseName = name?.uppercase() //type String comme le nom ou null
    println("$upperCaseName, welcome to Starship")
}


