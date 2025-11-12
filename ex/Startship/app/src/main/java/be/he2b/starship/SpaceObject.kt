package be.he2b.starship

abstract class SpaceObject (
    open val coordinates : Coordinates,
    private val _nameObject: String){
    /**
     * class to give each type of space object a common caracteristic
     */

    val name: String = _nameObject
        get(){
            if (field.isEmpty()) {
                throw Exception("No name")
            } else {
                return field
            }//attention name.isEmpty() va appeler le getter pour prendre le nom, à l'infini, stackoveerflow
        }
}