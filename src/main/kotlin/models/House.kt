package models

data class House (
    var houseCategory: String,
    var houseCost: Double,
    var houseLocation: String,
    var isAvailableFrom: String,
    var isSold: Boolean,
    var numberOfBedrooms: Int,
    var numberOfBathrooms: Double,
    var houseSqFoot:Int

)
