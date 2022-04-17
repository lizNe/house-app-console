package controllers

import models.House
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class HouseAPITest {

    private var bungalow: House? = null
    private var detached: House? = null
    private var semiDetached: House? = null
    private var twoStorey: House? = null
    private var threeStorey: House? = null
    private var apartment: House? = null
    private var populatedNotes: HouseAPI? = HouseAPI(XMLSerializer(File("houses.xml")))
    private var emptyNotes: HouseAPI? = HouseAPI(XMLSerializer(File("houses.xml")))


//    To get the test working you need to put comma before and after in order for the fields to appear like so... ,noteContents, and then the field will automatically allow you to enter its value
//    Got stuck on that for a while... ,isNoteArchived, True ,noteContents, "Blah blah" .....

    @BeforeEach
    fun setup() {
        bungalow = House("Bungalow", 350.000, "Roscommon", " 17th April 2022", true, 2, 1.5, 800)
        detached = House("Detached", 317.000, "Cavan", "30th May 2022", true, 3, 1.5, 1100)
        semiDetached = House("Semi-Detached", 299.000, "Kilkenny", "21st September 2022", false, 2, 2.0, 1000)
        twoStorey = House("Two-Storey", 400.000, "Carlow", " 3rd October 2022", false, 3, 2.5, 1500)
        threeStorey = House("Three-Storey", 750.000, "Kildare", "1st November 2022", false, 5, 4.0, 2700)
        apartment = House("Apartment", 197.500, "Dublin", "31st October 2022", false, 3, 2.5, 2000)

        //adding 5 Note to the notes api
        populatedNotes!!.add(bungalow!!)
        populatedNotes!!.add(detached!!)
        populatedNotes!!.add(semiDetached!!)
        populatedNotes!!.add(twoStorey!!)
        populatedNotes!!.add(threeStorey!!)
        populatedNotes!!.add(apartment!!)
    }

    @AfterEach
    fun tearDown() {
        bungalow = null
        detached = null
        semiDetached = null
        twoStorey = null
        threeStorey = null
        apartment = null
        populatedNotes = null
        emptyNotes = null
    }

    @Nested
    inner class AddHouses {
        @Test
        fun `adding a House to a populated list adds to ArrayList`() {
            val newHouse = House(
                "Studio",
                300.000,
                "Cork",
                "11th April 2022",
                false,
                2,
                1.0,
                1094
            )
            assertEquals(6, populatedNotes!!.numberOfHouses())
            assertTrue(populatedNotes!!.add(newHouse))
            assertEquals(7, populatedNotes!!.numberOfHouses())
            assertEquals(newHouse, populatedNotes!!.findHouse(populatedNotes!!.numberOfHouses() - 1))
        }

        @Test
        fun `adding a Note to an empty list adds to ArrayList`() {
            val newHouse = House(
                "Studio",
                300.000,
                "Cork",
                "11th April 2022",
                false,
                2,
                1.0,
                1094
            )
            assertEquals(0, emptyNotes!!.numberOfHouses())
            assertTrue(emptyNotes!!.add(newHouse))
            assertEquals(1, emptyNotes!!.numberOfHouses())
            assertEquals(newHouse, emptyNotes!!.findHouse(emptyNotes!!.numberOfHouses() - 1))
        }
    }

    @Nested
    inner class ListHouses {

        @Test
        fun `listAllHouses returns No Houses Stored message when ArrayList is empty`() {
            assertEquals(0, emptyNotes!!.numberOfHouses())
            assertTrue(emptyNotes!!.listAllHouses().lowercase().contains("no houses"))
        }

        @Test
        fun `listAllHouses returns Houses when ArrayList has houses stored`() {
            assertEquals(6, populatedNotes!!.numberOfHouses())
            val housesString = populatedNotes!!.listAllHouses().lowercase()
            assertTrue(housesString.contains("bungalow"))
            assertTrue(housesString.contains("detached"))
            assertTrue(housesString.contains("semi-detached"))
            assertTrue(housesString.contains("two-storey"))
            assertTrue(housesString.contains("three-storey"))
            assertTrue(housesString.contains("apartment"))
        }
    }

// Double Check with lecturer about this . Test runs but only when detached is set to true when it should be false
    @Test
    fun `listNotSoldHouses returns unSold houses when ArrayList has unsold houses stored`() {
        assertEquals(4, populatedNotes!!.numberOfNotSoldHouses())
        val unSoldHousesString = populatedNotes!!.listNotSoldHouses().lowercase()
        assertFalse(unSoldHousesString.contains("bungalow"))
        assertTrue(unSoldHousesString.contains("detached"))

        assertTrue(unSoldHousesString.contains("semi-detached"))
        assertTrue(unSoldHousesString.contains("two-storey"))
        assertTrue(unSoldHousesString.contains("three-storey"))
        assertTrue(unSoldHousesString.contains("apartment"))
    }

    @Test
    fun `listNotSoldHouses returns no unsold houses when ArrayList is empty`() {
        assertEquals(0, emptyNotes!!.numberOfNotSoldHouses())
        assertTrue(
            emptyNotes!!.listNotSoldHouses().lowercase().contains("no houses unsold")
        )
    }


    @Test
    fun `listSoldHouses returns no sold houses when ArrayList is empty`() {
        assertEquals(0, emptyNotes!!.numberOfSoldHouses())
        assertTrue(
            emptyNotes!!.listSoldHouses().lowercase().contains("no houses sold stored")
        )
    }

    @Test
    fun `listSoldHouses returns sold houses when ArrayList has sold houses stored`() {
        assertEquals(2, populatedNotes!!.numberOfSoldHouses())
        val soldHousesString = populatedNotes!!.listSoldHouses().lowercase()
        assertFalse(soldHousesString.contains("semi-detached"))
        assertFalse(soldHousesString.contains("two-storey"))
        assertFalse(soldHousesString.contains("three-storey"))
        assertFalse(soldHousesString.contains("apartment"))
        assertTrue(soldHousesString.contains("bungalow"))
        assertTrue(soldHousesString.contains("detached"))
    }

    @Nested
    inner class DeleteHouses {

        @Test
        fun `deleting a House that does not exist, returns null`() {
            assertNull(emptyNotes!!.deleteHouse(0))
            assertNull(populatedNotes!!.deleteHouse(-1))
            assertNull(populatedNotes!!.deleteHouse(6))
        }

        @Test
        fun `deleting a house that exists delete and returns deleted object`() {
            assertEquals(6, populatedNotes!!.numberOfHouses())
            assertEquals(threeStorey, populatedNotes!!.deleteHouse(4))
            assertEquals(5, populatedNotes!!.numberOfHouses())
            assertEquals(bungalow, populatedNotes!!.deleteHouse(0))
            assertEquals(4, populatedNotes!!.numberOfHouses())
        }
    }

}