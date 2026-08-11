package be.he2b.a61601_projet.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * role : initialisation of the database
 */
@Database(
    entities = [Location::class, WeatherEntry::class],
    version = 4,
    exportSchema = false
)
abstract class WeatherAppDB : RoomDatabase() {

    abstract fun locationDao(): LocationDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        private var sInstance: WeatherAppDB? = null

        fun getInstance(context: Context): WeatherAppDB {

            if (sInstance == null) {
                val dbBuilder = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherAppDB::class.java,
                    "weatherapp_database"
                )
                    .fallbackToDestructiveMigration()
                sInstance = dbBuilder.build()
            }
            return sInstance!!
        }
    }
}
