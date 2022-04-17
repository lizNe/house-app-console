package controllers

import models.House
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
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


    @Nested
    inner class UpdateHouses {
        @Test
        fun `updating a house that does not exist returns false`() {
            assertFalse(
                populatedNotes!!.updateHouse(
                    6,
                    House("Studio", 200.000, "Donegal", "17th July 2022", false, 3, 2.5, 900)
                )
            )
            assertFalse(
                populatedNotes!!.updateHouse(
                    -1,
                    House("Detached", 370.000, "Louth", "17th April 2022", true, 3, 2.0, 2000)
                )
            )
            assertFalse(
                emptyNotes!!.updateHouse(
                    0,
                    House("Three-Storey", 1.000000, "Waterford", "18th April 2022", true, 6, 6.0, 3000)
                )
            )
        }

        @Test
        fun `updating a house that exists returns true and updates`() {
            //check note 6 exists and check the contents
            assertEquals(apartment, populatedNotes!!.findHouse(5))
            assertEquals("Apartment", populatedNotes!!.findHouse(5)!!.houseCategory)
            assertEquals(197.500, populatedNotes!!.findHouse(5)!!.houseCost)
            assertEquals("Dublin", populatedNotes!!.findHouse(5)!!.houseLocation)
            assertEquals("31st October 2022", populatedNotes!!.findHouse(5)!!.isAvailableFrom)
            assertEquals(false, populatedNotes!!.findHouse(5)!!.isSold)
            assertEquals(3, populatedNotes!!.findHouse(5)!!.numberOfBedrooms)
            assertEquals(2.5, populatedNotes!!.findHouse(5)!!.numberOfBathrooms)
            assertEquals(2000, populatedNotes!!.findHouse(5)!!.houseSqFoot)

            //update note 6 with new information and ensure contents updated successfully
            assertTrue(
                populatedNotes!!.updateHouse(
                    5,
                    House("Apartment", 200.000, "Tipperary", "31st October 2022", false, 3, 2.5, 1980)
                )
            )
            assertEquals(200.000, populatedNotes!!.findHouse(5)!!.houseCost)
            assertEquals("Tipperary", populatedNotes!!.findHouse(5)!!.houseLocation)
            assertEquals(1980, populatedNotes!!.findHouse(5)!!.houseSqFoot)


        }
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            // Saving an empty houses.XML file.
            val storingHouses = HouseAPI(XMLSerializer(File("houses.xml")))
            storingHouses.store()

            //Loading the empty houses.xml file into a new object
            val loadedHouses = HouseAPI(XMLSerializer(File("houses.xml")))
            loadedHouses.load()

            //Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(0, storingHouses.numberOfHouses())
            assertEquals(0, loadedHouses.numberOfHouses())
            assertEquals(storingHouses.numberOfHouses(), loadedHouses.numberOfHouses())
        }

        @Test
        fun `saving and loading an loaded collection in XML doesn't loose data`() {
            // Storing 3 notes to the houses.XML file.
            val storingHouses = HouseAPI(XMLSerializer(File("houses.xml")))
            storingHouses.add(bungalow!!)
            storingHouses.add(twoStorey!!)
            storingHouses.add(threeStorey!!)
            storingHouses.store()

            //Loading notes.xml into a different collection
            val loadedHouses = HouseAPI(XMLSerializer(File("houses.xml")))
            loadedHouses.load()

            //Comparing the source of the Houses (storingHouses) with the XML loaded Houses (loadedHouses)
            assertEquals(3, storingHouses.numberOfHouses())
            assertEquals(3, loadedHouses.numberOfHouses())
            assertEquals(storingHouses.numberOfHouses(), loadedHouses.numberOfHouses())
            assertEquals(storingHouses.findHouse(0), loadedHouses.findHouse(0))
            assertEquals(storingHouses.findHouse(1), loadedHouses.findHouse(1))
            assertEquals(storingHouses.findHouse(2), loadedHouses.findHouse(2))
        }

        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app`() {
            // Saving an empty notes.json file.
            val storingHouses = HouseAPI(JSONSerializer(File("houses.json")))
            storingHouses.store()

            //Loading the empty houses.json file into a new object
            val loadedHouses = HouseAPI(JSONSerializer(File("houses.json")))
            loadedHouses.load()

            //Comparing the source of the Houses (storingHouses) with the json loaded Houses (loadedHouses)
            assertEquals(0, storingHouses.numberOfHouses())
            assertEquals(0, loadedHouses.numberOfHouses())
            assertEquals(storingHouses.numberOfHouses(), loadedHouses.numberOfHouses())
        }

        @Test
        fun `saving and loading an loaded collection in JSON doesn't loose data`() {
            // Storing 3 notes to the houses.json file.
            val storingHouses = HouseAPI(JSONSerializer(File("houses.json")))
            storingHouses.add(bungalow!!)
            storingHouses.add(twoStorey!!)
            storingHouses.add(threeStorey!!)
            storingHouses.store()

            //Loading Houses.json into a different collection
            val loadedHouses = HouseAPI(JSONSerializer(File("houses.json")))
            loadedHouses.load()

            //Comparing the source of the Houses (storingHouses) with the json loaded Houses (loadedHouses)
            assertEquals(3, storingHouses.numberOfHouses())
            assertEquals(3, loadedHouses.numberOfHouses())
            assertEquals(storingHouses.numberOfHouses(), loadedHouses.numberOfHouses())
            assertEquals(storingHouses.findHouse(0), loadedHouses.findHouse(0))
            assertEquals(storingHouses.findHouse(1), loadedHouses.findHouse(1))
            assertEquals(storingHouses.findHouse(2), loadedHouses.findHouse(2))
        }
    }

    @Nested
    inner class HouseToBeSold {
        @Test
        fun `selling a house that does not exist returns false`() {
            assertFalse(populatedNotes!!.houseToBeSold(6))
            assertFalse(populatedNotes!!.houseToBeSold(-1))
            assertFalse(emptyNotes!!.houseToBeSold(0))
        }

        @Test
        fun `selling an already sold house returns false`() {
            assertTrue(populatedNotes!!.findHouse(2)!!.isSold)
            assertFalse(populatedNotes!!.houseToBeSold(2))
        }

        @Test
        fun `selling an unsold house that exists returns true and sells`() {
            assertFalse(populatedNotes!!.findHouse(2)!!.isSold)
            assertTrue(populatedNotes!!.houseToBeSold(2))
            assertTrue(populatedNotes!!.findHouse(2)!!.isSold)
        }
    }

    @Nested
    inner class SearchMethods {

        @Test
        fun `search notes by category returns no houses when no houses with that category exist`() {
            //Searching a populated collection for a category that doesn't exist.
            assertEquals(6, populatedNotes!!.numberOfHouses())
            val searchResults = populatedNotes!!.searchByCategory("no houses found")
            assertTrue(searchResults.isEmpty())

            //Searching an empty collection
            assertEquals(0, emptyNotes!!.numberOfHouses())
            assertTrue(emptyNotes!!.searchByCategory("").isEmpty())
        }

        @Test
        fun `search houses by category returns Houses when Houses with that category exist`() {
            assertEquals(6, populatedNotes!!.numberOfHouses())

            //Searching a populated collection for a full category that exists (case matches exactly)
            var searchResults = populatedNotes!!.searchByCategory("Semi-Detached")
            assertTrue(searchResults.contains("Semi-Detached"))
            assertFalse(searchResults.contains("Studio"))

            //Searching a populated collection for a partial category that exists (case matches exactly)
            searchResults = populatedNotes!!.searchByCategory("Storey")
            assertTrue(searchResults.contains("Two-Storey"))
            assertTrue(searchResults.contains("Three-Storey"))
            assertFalse(searchResults.contains("Apartment"))

            //Searching a populated collection for a partial category that exists (case doesn't match)
            searchResults = populatedNotes!!.searchByCategory("sToReY")
            assertTrue(searchResults.contains("Two-Storey"))
            assertTrue(searchResults.contains("Three-Storey"))
            assertFalse(searchResults.contains("Apartment"))
        }
    }
























}


