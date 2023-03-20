package com.example.lab5.data
/**
* Data class that represents the current UI state in terms of [sandwichType],
* [breadType], selected [topping] and [price]
*/
data class OrderUiState (
    /** Selected sandwich type */
    val sandwichType: String = "",
    /** Selected bread type */
    val breadType: String = "",
    /** selected topping */
    val topping: Set<String> = mutableSetOf(),
    /** Total price for the order */
    val price: String = "",
)