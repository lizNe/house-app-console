import controllers.HouseAPI
import models.House
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
//import persistence.YAMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File

//Used to allow you to console log information to the console
private val logger = KotlinLogging.logger {}

//private val houseAPI = HouseAPI(XMLSerializer(File("houses.xml")))

private val houseAPI = HouseAPI(JSONSerializer(File("houses.json")))
//private val houseAPI = HouseAPI(YAMLSerializer(File("houses.yaml")))


//Will run the function runMenu() starting the entire application
fun main(args: Array<String>) {
    runMenu()
}

fun runMenu() {

    do {
        val option = mainMenu()
        when (option) {
            1 -> addHouse()
            2 -> listHouses()
            3 -> updateHouse()
            4 -> deleteHouse()
            5 -> houseToBeSold()
            6 -> searchHouses()
            7 -> save()
            8 -> load()
            0 -> exitApp()
            else -> System.out.println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun mainMenu(): Int {
    return readNextInt(
        """ 
         > ----------------------------------
         > |        Housing Agent App       |
         > ----------------------------------
         > |          HOUSE MENU            |
         > |   1) Add a House               |
         > |   2) List all Houses           |
         > |   3) Update a House            |
         > |   4) Delete a House            |
         > |   5) House to Sell             |
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

fun addHouse() {

    val houseCategory = readNextLine("Enter the category of the house: ")
    val houseCost = readNextDouble("Enter the cost of the house: ")
    val houseLocation = readNextLine("Enter the location of the house: ")
    val isAvailableFrom = readNextLine("Enter the dates in which the house is available for viewing: ")
    val numberOfBedrooms = readNextInt("Enter the number of bedrooms in the house: ")
    val numberOfBathrooms = readNextDouble("Enter the number of bathrooms in the house: ")
    val houseSqFoot = readNextInt("Enter the square footage of the house: ")
    val isAdded = houseAPI.add(House(houseCategory, houseCost, houseLocation, isAvailableFrom, false, numberOfBedrooms,numberOfBathrooms, houseSqFoot ) )

    if (isAdded) {
        println("Add Successful")
    } else {
        println("Add Failed")
    }

}

fun listHouses() {
    if (houseAPI.numberOfHouses() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View All Houses          |
                  > |   2) View Sold Houses         |
                  > |   3) View unSold Houses       |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllHouses();
            2 -> listSoldHouses();
            3 -> listNotSoldHouses();
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No houses stored")
    }
}

fun updateHouse() {
    listHouses()
    if (houseAPI.numberOfHouses() > 0) {
        //only ask the user to choose the house if the house exist
        val indexToUpdate = readNextInt("Enter the index of the house you want to update: ")
        if (houseAPI.isValidIndex(indexToUpdate)) {
            val houseCategory = readNextLine("Enter the category of the house to update: ")
            val houseCost = readNextDouble("Enter the cost of the house to update: ")
            val houseLocation = readNextLine("Enter the location of the house to update: ")
            val isAvailableFrom = readNextLine("Enter the dates in which the house is available for viewing to update: ")
            val numberOfBedrooms = readNextInt("Enter the number of bedrooms in the house to update: ")
            val numberOfBathrooms = readNextDouble("Enter the number of bathrooms in the house to update: ")
            val houseSqFoot = readNextInt("Enter the square footage of the house to update: ")

            //pass the index of the house and the new house details to HouseAPI for updating and check for success.
           // To get the Boolean value in this constructor you have to type either true or false first and then the field will appear as below
            if (houseAPI.updateHouse(indexToUpdate, House(houseCategory, houseCost, houseLocation, isAvailableFrom, false , numberOfBedrooms, numberOfBathrooms, houseSqFoot))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There is no houses for this index number")
        }
    }
}

//only ask the user to choose the note to delete if notes exist
//pass the index of the note to NoteAPI for deleting and check for success.
fun deleteHouse(){
    listHouses()
    if(houseAPI.numberOfHouses()>0){
        val indexToDelete = readNextInt("Enter the index of the house to delete: ")
        val houseToDelete = houseAPI.deleteHouse(indexToDelete)
        if(houseToDelete != null){
            println("Delete Successful! Deleted house: ${houseToDelete.houseCategory}")
        }else{
            println("Delete Not Successful")
        }
    }
}

fun exitApp(){
    println("Exiting the House Agent Application....Goodbye! ")
}

//Tries to save the save function in houseAPI. A throw catch is used so if the save() function doesn't work then an error will be thrown that is caught by the system to display
// "Error writing to file: $e" to the user
fun save() {
    try {
        houseAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

//Tries to load the load function in noteAPI. A throw catch is used so if the load() fucntion doesnt work then an error will be thrown that is caught by the system to display
// "Error reading from file: $e" to the user
fun load() {
    try {
        houseAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

//only ask the user to choose the note to archive if active notes exist
//pass the index of the note to NoteAPI for archiving and check for success.
//like archive note it will say the house is SOLD
fun houseToBeSold(){
    listSoldHouses()
    if(houseAPI.numberOfSoldHouses()>0){
        val indexToSell = readNextInt("Enter the index of the house to be confirmed as Sold: ")
        if(houseAPI.houseToBeSold(indexToSell)){
            println("Sell Successful")
        }else{
            println("Sell Not Successful")
        }
    }
}

fun searchHouses(){
    val searchCategory = readNextLine("Enter the category to search by: ")
    val searchResults = houseAPI.searchByCategory(searchCategory)
    if(searchResults.isEmpty()){
        println("No Houses Found")
    }else{
        println(searchResults)
    }
}

// calls the class houseAPI and calls the function listAllHouses() from this class and will print all the list of houses that are in the system
fun listAllHouses() {
    println(houseAPI.listAllHouses())
}

// calls the class houseAPI and calls the function listSoldHouses() from this class and will print all the archived houses that are in the system
fun listSoldHouses() {
    println(houseAPI.listSoldHouses())
}


fun listNotSoldHouses() {
    println(houseAPI.listNotSoldHouses())
}
