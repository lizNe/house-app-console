package controllers

import models.House

//ArrayList called houses
class HouseAPI(serializerType: Serializer) {
    private var houses = ArrayList<House>()
    private var serializer = Serializer = serializerType

    fun add(house: House): Boolean{
        return houses.addHouse(house)
    }

   fun deleteHouse(indexToDelete: Int): House?{
       return if(isValidListIndex(indexToDelete, houses)){
           houses.removeAt(indexToDelete)
       }else null
   }

}
//find the house object by the index number
//if the house exists, use the house details passed as parameters to update the found house in the ArrayList.
//if the house was not found, return false, indicating that the update was not successful
fun updateHouse(indexToUpdate: Int, house: House?): Boolean{
    val foundHouse = findHouse(indexToUpdate)
    if((foundHouse !=null)&& (house !=null)){
        foundHouse.houseCategory = house.houseCategory
        foundHouse.houseCost = house.houseCost
        foundHouse.houseLocation = house.houseLocation
        foundHouse.isAvailableFrom = house.isAvailableFrom
        foundHouse.numberOfBedrooms = house.numberOfBedrooms
        foundHouse.numberOfBathrooms = house.numberOfBathrooms
        foundHouse.houseSqFoot = house.houseSqFoot
        return true
    }
    return false
}

//All listing
fun listAllHouses(): String =
    if  (houses.isEmpty()) "No houses stored"
    else formatListString(houses)

fun listSoldHouses(): String =
    if  (numberOfSoldHouses() == 0)  "No Houses Sold are stored"
    else formatListString(houses.filter { house -> !house.isSold})

//All number functions for the listing functions
fun numberOfSoldHouses(): Int = houses.count { house: House -> house.isSold }


// searches for notes by houseCategory and uses the formatListString to print the house in a clean format
fun searchByCategory (searchString : String) =
    formatListString(
        houses.filter { house -> house.houseCategory.contains(searchString, ignoreCase = true) })
