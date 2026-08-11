package be.he2b.a61601_projet.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

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
     * Mark the given location as favorite and unmark every other one
     * in a single atomic UPDATE (only one favorite at a time).
     */
    @Query("UPDATE locations SET is_favorite = CASE WHEN id = :id THEN 1 ELSE 0 END")
    suspend fun setFavorite(id: Long)

    /**
     * Look up a stored location by its Open-Meteo id.
     * Used to recover the Room row id when insert() returns -1 (already present).
     */
    @Query("SELECT * FROM locations WHERE open_meteo_id = :openMeteoId LIMIT 1")
    suspend fun getByOpenMeteoId(openMeteoId: Long): Location?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(location: Location): Long

    @Delete
    suspend fun delete(location: Location)

    @Query("DELETE FROM locations WHERE id = :id")
    suspend fun deleteById(id: Long)
}
