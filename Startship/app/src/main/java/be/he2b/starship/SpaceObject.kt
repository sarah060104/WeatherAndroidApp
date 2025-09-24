package be.he2b.starship

abstract class SpaceObject (
    open val coordinates : Coordinates,
    private val nameObject: String){
    /**
     * classe pour donner carac commune à objet dans espace
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