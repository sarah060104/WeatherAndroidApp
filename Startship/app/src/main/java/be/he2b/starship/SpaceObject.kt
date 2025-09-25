package be.he2b.starship

abstract class SpaceObject (
    open val coordinates : Coordinates,
    private val _nameObject: String){
    /**
     * class to give each type of space object a common caracteristic
     */

    open val name: String
        get(){
            if(name.isEmpty()){
                return "Object unknown"
            }
            else {
                return name
            }
        }
}