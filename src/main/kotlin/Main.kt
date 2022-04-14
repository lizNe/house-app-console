import controllers.HouseAPI
import models.House
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
import persistence.YAMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File

//Used to allow you to console log information to the console
private val logger = KotlinLogging.logger{}

//private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))

private val noteAPI = NoteAPI(JSONSerializer(File("notes.json")))
//private val noteAPI = NoteAPI(YAMLSerializer(File("notes.yaml")))


//Will run the function runMenu() starting the entire application
fun main(args: Array<String>){
    runMenu()
}

fun runMenu(){

    do{
        val option = mainMenu()
        when(option){
            1-> addHouse()
            2-> listHouses()
            3 -> updateHouse()
            4 -> deleteHouse()
            5 -> isHouseAvailable()
            6 -> searchHouses()
            7 -> save()
            8 -> load()
            0 -> exitApp()
            else -> System.out.println("Invalid option entered: ${option}")
        }
    }
      while (true)
}

fun mainMenu(): Int {
    return readNextInt(
        """ 
         > ----------------------------------
         > |        Housing Agent App       |
         > ----------------------------------
         > | HOUSE MENU                     |
         > |   1) Add a House               |
         > |   2) List all Houses           |
         > |   3) Update a House            |
         > |   4) Delete a House            |
         > |   5) Sell a House              |
         > |--------------------------------|
         > |   6) Search Houses             |
         > |--------------------------------|
         > |   7) Save Houses               |
         > |   8) Load Houses               |
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">")
    )
}

fun addHouse(){

    val houseCategory = readNextLine("Enter the category of the house: ")
    val houseCost = readNextDouble("Enter the cost of the house: ")
    val houseLocation = readNextLine("Enter the location of the house: ")
    val isAvailableFrom = readNextLine("Enter the dates in which the house is available for viewing: ")
    val numberOfBedrooms = readNextInt("Enter the number of bedrooms in the house: ")
    val numberOfBathrooms = readNextDouble("Enter the number of bathrooms in the house: ")
    val houseSqFoot = readNextInt("Enter the square footage of the house: ")
    val isAdded = houseAPI.add(House(houseCategory, houseCost, houseLocation, isAvailableFrom, isSold, numberOfBedrooms, numberOfBathrooms, houseSqFoot))

    if(isAdded){
        println("Add Successful")
    }else{
        println("Add Failed")
    }

}

fun listHouses(){
    if(houseAPI.numberOfNotes()>0){
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View All Houses          |
                  > |   2) View Sold houses         |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when(option){
            1 -> listAllHouses();
            2 -> listSoldHouses();
            else -> println("Invalid option entered: " + option)
        }
    }else{
        println("Option Invalid - No notes stored")
}
}
