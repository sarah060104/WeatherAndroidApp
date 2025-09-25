package be.he2b.starship

fun main() {
    val planet1 = Planet.createRandomPlanet()
    println("Nom : ${planet1.name} , Location : ${planet1.coordinates} , exploitationRate : ${planet1.exploitationRate}")
    val planet2 = Planet.createRandomPlanet()
    println("Nom : ${planet2.name} , Location :${planet2.coordinates} , exploitationRate : ${planet2.exploitationRate}")
    val planet3 = Planet.createRandomPlanet()
    println("Nom : ${planet3.name} , Location :${planet3.coordinates} , exploitationRate : ${planet3.exploitationRate}")

    val station1 = Station.createRandomStation()
    println("${station1.name} ${station1.coordinates} ${station1.metalPrice}")

}

fun displayWelcomeMessage(name: String?) { // point 3
    val upperCaseName = name?.uppercase() //type String comme le nom ou null
    println("$upperCaseName, welcome to Starship")
}


