package controllers

import models.House
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertEquals
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


}