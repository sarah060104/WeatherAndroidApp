package be.he2b.a61601_projet.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    /**
     * getitng all forecast entries for a location, ordered by date (oldest first).
     *
     */
    @Query("SELECT * FROM weather_entries WHERE location_id = :locationId ORDER BY date ASC")
    fun getForLocation(locationId: Long): Flow<List<WeatherEntry>>

    /**
     * Get the most recent fetch timestamp for a location.
     */
    @Query("SELECT MAX(fetched_at) FROM weather_entries WHERE location_id = :locationId")
    suspend fun getLatestFetchedAt(locationId: Long): Long?

    /**
     * Inserting a batch of forecast entries (typically 7 days at once from the API).
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<WeatherEntry>): List<Long>

    /**
     * Delete all forecast entries whose date is before the given cutoff.
     * Used to clean up past days automatically.
     */
    @Query("DELETE FROM weather_entries WHERE date < :cutoff")
    suspend fun deletePastEntries(cutoff: Long)

    /**
     * Delete all forecast entries for a specific location.
     */
    @Query("DELETE FROM weather_entries WHERE location_id = :locationId")
    suspend fun deleteForLocation(locationId: Long)
}
