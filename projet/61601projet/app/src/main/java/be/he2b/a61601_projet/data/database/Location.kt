package be.he2b.a61601_projet.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "locations",
    indices = [Index(value = ["open_meteo_id"], unique = true)]
)
data class Location(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "open_meteo_id")
    val openMeteoId: Long,

    val name: String,

    val country: String? = null,

    val admin1: String? = null,

    val latitude: Double,

    val longitude: Double,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,

    @ColumnInfo(name = "added_at")
    val addedAt: Long = System.currentTimeMillis()
)
