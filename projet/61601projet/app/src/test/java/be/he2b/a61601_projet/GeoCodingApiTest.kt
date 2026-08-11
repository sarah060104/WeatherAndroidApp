package be.he2b.a61601_projet

import be.he2b.a61601_projet.data.api.GeoCodingApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Tests the GeoCodingApi interface against a local MockWebServer.
 * Goal: prove that success/error/malformed responses are handled without
 * crashing the app (exceptions must be catchable by the ViewModel).
 */
class GeoCodingApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: GeoCodingApi

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GeoCodingApi::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    /**
     * successful request and parsing
     */
    @Test
    fun searchCityValidJsonReturnsParsedResults() = runTest {
        val body = """
            {
              "results": [
                {"id": 2988507, "name": "Paris", "latitude": 48.85, "longitude": 2.35},
                {"id": 4717560, "name": "Paris", "latitude": 33.66, "longitude": -95.55}
              ]
            }
        """.trimIndent()
        server.enqueue(MockResponse().setResponseCode(200).setBody(body))

        val response = api.searchCity(name = "Paris")

        assertEquals(2, response.result.size)
        assertEquals(2988507L, response.result[0].id)
        assertEquals("Paris", response.result[0].name)
        assertEquals(48.85, response.result[0].latitude)
    }

    /**
     * research "succesful" with no result
     */
    @Test
    fun searchCityNoResultsFieldReturnsEmptyListWithoutCrashing() = runTest {
        server.enqueue(MockResponse().setResponseCode(200).setBody("{}"))

        val response = api.searchCity(name = "asdfghjkl")

        assertTrue(response.result.isEmpty(), "missing results field should map to empty list")
    }

    /**
     * dealing with server error
     */
    @Test
    fun searchCityHttp500ThrowsHttpException() = runTest {
        server.enqueue(MockResponse().setResponseCode(500).setBody("server error"))

        val ex = assertThrows(HttpException::class.java) {
            kotlinx.coroutines.runBlocking { api.searchCity(name = "Paris") }
        }
        assertEquals(500, ex.code())
    }


    @Test
    fun searchCityMalformedJsonThrowsCatchableException() = runTest {
        server.enqueue(MockResponse().setResponseCode(200).setBody("{ not valid json"))

        val threw = try {
            api.searchCity(name = "Paris")
            false
        } catch (e: Exception) {
            true
        }
        assertTrue(threw, "malformed JSON should throw a catchable exception, not crash")
    }

    @Test
    fun searchCityEmptyBodyThrowsCatchableException() = runTest {
        server.enqueue(MockResponse().setResponseCode(200).setBody(""))

        val threw = try {
            api.searchCity(name = "Paris")
            false
        } catch (e: Exception) {
            true
        }
        assertTrue(threw, "empty body should throw a catchable exception, not crash")
    }
}
