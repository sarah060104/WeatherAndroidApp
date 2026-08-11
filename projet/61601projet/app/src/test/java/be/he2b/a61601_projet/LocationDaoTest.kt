package be.he2b.a61601_projet

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import be.he2b.a61601_projet.data.database.Location
import be.he2b.a61601_projet.data.database.LocationDao
import be.he2b.a61601_projet.data.database.WeatherAppDB
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocationDaoTest {

    private lateinit var db: WeatherAppDB
    private lateinit var dao: LocationDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        db = Room.inMemoryDatabaseBuilder(context, WeatherAppDB::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.locationDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    /**
     * succesful insert
     */
    @Test
    fun insertNewLocationReturnsPositiveId() = runTest {
        val id = dao.insert(
            Location(
                openMeteoId = 2988507L,
                name = "Paris",
                latitude = 48.85,
                longitude = 2.35
            )
        )
        assertTrue(id > 0, "insert should return the new row id")
    }

    /**
     * searching the same city with same open meteo id
     */

    @Test
    fun insertSameOpenMeteoIdTwiceReturnsMinusOneAndKeepsOneRow() = runTest {
        val first = dao.insert(
            Location(
                openMeteoId = 2988507L,
                name = "Paris",
                latitude = 48.85,
                longitude = 2.35
            )
        )
        val second = dao.insert(
            Location(
                openMeteoId = 2988507L,
                name = "Paris",
                latitude = 48.85,
                longitude = 2.35
            )
        )

        assertTrue(first > 0, "first insert should succeed")
        assertEquals(-1L, second, "duplicate insert should be ignored and return -1")

        val all = dao.getAllLocations().first()
        assertEquals(1, all.size, "unique index should keep only one row")
    }

    /**
     * making sure two city with the same name have distinct ids
     */

    @Test
    fun insertTwoDifferentOpenMeteoIdsWithSameNameKeepsBothRows() = runTest {

        val parisFr = dao.insert(
            Location(
                openMeteoId = 2988507L,
                name = "Paris",
                latitude = 48.85,
                longitude = 2.35
            )
        )
        val parisTx = dao.insert(
            Location(
                openMeteoId = 4717560L,
                name = "Paris",
                latitude = 33.66,
                longitude = -95.55
            )
        )

        assertTrue(parisFr > 0)
        assertTrue(parisTx > 0)
        assertNotEquals(parisFr, parisTx)

        val all = dao.getAllLocations().first()
        assertEquals(2, all.size, "two distinct open_meteo_ids should both persist")
    }
}
