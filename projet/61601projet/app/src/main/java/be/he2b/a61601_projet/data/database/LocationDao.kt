package be.he2b.a61601_projet.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.Optional

@Dao
interface LocationDao {


    /**
     * get list of locations saved in the database
     * (to show search history)
     */
    @Query("SELECT * FROM locations ORDER BY added_at DESC")
    fun getAllLocations(): Flow<List<Location>>

    /**
     * get favorite location from table locations
     */
    @Query("SELECT * FROM locations WHERE is_favorite = 1 ")
    fun SearchFavorite(): Flow<List<Location>>

    /**
     * check if name of locations already in the database (to receive its geographical data long and lat
     */
    @Query("SELECT * FROM locations WHERE name LIKE '%' || :query || '%' ORDER BY name")
    suspend fun search(query: String): Optional<Location>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(location: Location): Long

    @Delete
    suspend fun delete(location: Location)

    @Query("DELETE FROM locations WHERE id = :id")
    suspend fun deleteById(id: Long)
}
