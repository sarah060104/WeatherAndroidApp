package be.he2b.a61601_projet.data.model

import android.content.Context
import be.he2b.a61601_projet.data.database.Location
import be.he2b.a61601_projet.data.database.LocationDao
import be.he2b.a61601_projet.data.database.WeatherAppDB
import be.he2b.a61601_projet.data.database.WeatherDao
import be.he2b.a61601_projet.data.database.WeatherEntry
import be.he2b.a61601_projet.data.response.savedCity
import kotlinx.coroutines.flow.Flow

/**
 * Single access point for data (local DB for now, HTTP later).
 * Must be initialized once at app startup via init(context).
 */
object Repository {

    // Cache TTL: 24h in milliseconds
    private const val CACHE_TTL_MS = 24L * 60 * 60 * 1000

    private lateinit var locationDao: LocationDao
    private lateinit var weatherDao: WeatherDao

    fun init(context: Context) {
        if (::locationDao.isInitialized) return
        val db = WeatherAppDB.getInstance(context)
        locationDao = db.locationDao()
        weatherDao = db.weatherDao()
    }

    // ========== LOCATIONS ==========

    fun observeAllLocations(): Flow<List<Location>> = locationDao.getAllLocations()

    fun observeFavorites(): Flow<List<Location>> = locationDao.SearchFavorite()

    suspend fun saveLocation(location: Location): Long =
        locationDao.insert(location)

    /**
     * Insert a city picked from the search results into Room.
     * Deduplication is handled by the unique index on open_meteo_id
     * combined with OnConflictStrategy.IGNORE (returns -1 if already present).
     */
    suspend fun saveLocationFromSearch(city: savedCity): Long =
        locationDao.insert(
            Location(
                openMeteoId = city.id,
                name = city.name,
                country = city.country,
                admin1 = city.admin1,
                latitude = city.latitude,
                longitude = city.longitude
            )
        )

    suspend fun setFavorite(id: Long) =
        locationDao.setFavorite(id)

    /**
     * Save the searched city (or reuse the existing row) then mark it as the
     * current favorite. Returns the Room row id of the favorite location.
     */
    suspend fun saveAndFavorite(city: savedCity): Long {
        val inserted = saveLocationFromSearch(city)
        val id = if (inserted != -1L) {
            inserted
        } else {
            locationDao.getByOpenMeteoId(city.id)?.id
                ?: error("City with open_meteo_id=${city.id} not found after insert conflict")
        }
        locationDao.setFavorite(id)
        return id
    }

    suspend fun deleteLocation(location: Location) =
        locationDao.delete(location)

    suspend fun deleteLocationById(id: Long) =
        locationDao.deleteById(id)

    /**
     * Reactive flow of cached forecast entries for a location.
     * The UI can collect this to update automatically when the cache changes.
     */
    fun observeWeatherForLocation(locationId: Long): Flow<List<WeatherEntry>> =
        weatherDao.getForLocation(locationId)

    /**
     * True if fresh (< 24h) cached data exists for this location.
     * When false, the caller should fetch from the API then call saveWeatherForLocation.
     */
    suspend fun isCacheFresh(locationId: Long): Boolean {
        val latest = weatherDao.getLatestFetchedAt(locationId) ?: return false
        return (System.currentTimeMillis() - latest) < CACHE_TTL_MS
    }

    /**
     * Refresh the cache for a location: delete existing rows, insert the new ones.
     */
    suspend fun saveWeatherForLocation(locationId: Long, entries: List<WeatherEntry>) {
        weatherDao.deleteForLocation(locationId)
        weatherDao.insertAll(entries)
    }

    /**
     * Remove forecast entries whose date is already in the past.
     * Call at app startup or when opening a weather screen.
     */
    suspend fun cleanupPastWeather() {
        weatherDao.deletePastEntries(System.currentTimeMillis())
    }
}
