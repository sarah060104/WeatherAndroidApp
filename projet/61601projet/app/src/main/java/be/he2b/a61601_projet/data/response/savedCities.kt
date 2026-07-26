package be.he2b.a61601_projet.data.response

data class savedCities(

    /**
     * data class used to save cities and their geo coding
     * TODO : convert into table for database
     */
    val name : String,
    val latitude: Double,
    val longitude: Double
)