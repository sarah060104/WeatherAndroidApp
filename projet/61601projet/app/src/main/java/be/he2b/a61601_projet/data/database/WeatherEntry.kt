package be.he2b.a61601_projet.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Cached daily weather forecast for a given location.
 * One row = one day's forecast for one location.
 */
@Entity(
    tableName = "weather_entries",
    foreignKeys = [
        ForeignKey(
            entity = Location::class,
            parentColumns = ["id"],
            childColumns = ["location_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["location_id"])]
)
data class WeatherEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "location_id")
    val locationId: Long,

    val date: Long,

    @ColumnInfo(name = "temp_min")
    val tempMin: Double,

    @ColumnInfo(name = "temp_max")
    val tempMax: Double,

    @ColumnInfo(name = "weather_code")
    val weatherCode: Int,

    @ColumnInfo(name = "fetched_at")
    val fetchedAt: Long = System.currentTimeMillis()
)
