package controllers

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utilities.HouseUtilities.categories
import utilities.HouseUtilities.isValidCategory
import utilities.HouseUtilities.validRange

class HouseUtilitiesTest {
    @Test
    fun validRangeWorksWithPositiveTestData() {
        Assertions.assertTrue(validRange(1, 1, 1))
        Assertions.assertTrue(validRange(1, 1, 2))
        Assertions.assertTrue(validRange(1, 0, 1))
        Assertions.assertTrue(validRange(1, 0, 2))
        Assertions.assertTrue(validRange(-1, -2, -1))
    }

    @Test
    fun validRangeWorksWithNegativeTestData() {
        Assertions.assertFalse(validRange(1, 0, 0))
        Assertions.assertFalse(validRange(1, 1, 0))
        Assertions.assertFalse(validRange(1, 2, 1))
        Assertions.assertFalse(validRange(-1, -1, -2))
    }

//    Errors says Expected 6 but 7. Need to double-check why and if correct
    @Test
    fun categoriesReturnsFullCategoriesSet() {
        Assertions.assertEquals(7, categories.size)
        Assertions.assertTrue(categories.contains("Bungalow"))
        Assertions.assertTrue(categories.contains("Detached"))
        Assertions.assertTrue(categories.contains("Semi-Detached"))
        Assertions.assertTrue(categories.contains("Two-Storey"))
        Assertions.assertTrue(categories.contains("Three-Storey"))
        Assertions.assertTrue(categories.contains("Apartment"))
        Assertions.assertTrue(categories.contains("Studio"))
    }

    @Test
    fun isValidCategoryTrueWhenCategoryExists() {
        Assertions.assertTrue(isValidCategory("BUNGALOW"))
        Assertions.assertTrue(isValidCategory("bungalow"))
        Assertions.assertTrue(isValidCategory("BUNgaLoW"))

        Assertions.assertTrue(isValidCategory("DETACHED"))
        Assertions.assertTrue(isValidCategory("detached"))
        Assertions.assertTrue(isValidCategory("dETAched"))

        Assertions.assertTrue(isValidCategory("SEMI-DETACHED"))
        Assertions.assertTrue(isValidCategory("semi-detached"))
        Assertions.assertTrue(isValidCategory("SEMi-DEtachED"))

        Assertions.assertTrue(isValidCategory("TWO-STOREY"))
        Assertions.assertTrue(isValidCategory("two-storey"))
        Assertions.assertTrue(isValidCategory("TwO-StOrEy"))

        Assertions.assertTrue(isValidCategory("THREE-STOREY"))
        Assertions.assertTrue(isValidCategory("three-storey"))
        Assertions.assertTrue(isValidCategory("ThrEE-StOrEy"))

        Assertions.assertTrue(isValidCategory("APARTMENT"))
        Assertions.assertTrue(isValidCategory("apartment"))
        Assertions.assertTrue(isValidCategory("aPArTmenT"))
    }

    @Test
    fun isValidCategoryFalseWhenCategoryDoesNotExist() {
        Assertions.assertFalse(isValidCategory("bunglow"))
        Assertions.assertFalse(isValidCategory("detachted"))
        Assertions.assertFalse(isValidCategory("semi-detatched"))
        Assertions.assertFalse(isValidCategory("too-storey"))
        Assertions.assertFalse(isValidCategory("too-story"))
        Assertions.assertFalse(isValidCategory("tree-storey"))
        Assertions.assertFalse(isValidCategory("three-story"))
        Assertions.assertFalse(isValidCategory("aparment"))
        Assertions.assertFalse(isValidCategory("aparttment"))
    }
}
