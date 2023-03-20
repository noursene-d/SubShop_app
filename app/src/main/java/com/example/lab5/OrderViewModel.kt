package com.example.lab5

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.lab5.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat

/** Price for a single topping */
private const val PRICE_PER_TOPPING = 0.5


class OrderViewModel : ViewModel(){

    /**
     * App state for this order
     */


    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()


    /**
     * populates the topping set depending on the state of the checkbox
     * @param toppings the topping selected
     * @param state the boolean state of the checkbox
     */
    fun getCheckedState(toppings: String, state: Boolean) {
        
        val toppingSet = _uiState.value.topping.toMutableSet()
        if(state){
            toppingSet.add(toppings)
        } else {
            toppingSet.remove(toppings)
        }
        _uiState.update { currentState ->
            currentState.copy(topping = toppingSet,
            price = CalculatePrice(toppingSet = toppingSet))
        }
    }

    /**
     * Calculates the final price for the order
     * @param quantity is the number of toppings selected
     * @param breadType is the bread type selected
     */
     fun CalculatePrice(
        sandwichType: String = _uiState.value.sandwichType,
        toppingSet: Set<String> = _uiState.value.topping
    ): String {
        /**
         * Bread type is free
         * PriceForTopping = quantity*$0.50
         * Price for Sandwich Type = depends
         */
        var finalPrice: Double
        var priceTopping = toppingSet.size * PRICE_PER_TOPPING

        finalPrice = when (sandwichType) {
            "Philly" -> {
                priceTopping + 7.95
            }
            "BLT" -> {
                priceTopping + 7.45
            }
            "Veggie" -> {
                priceTopping + 6.75
            }
            else -> {
                0.0
            }
        }

        return NumberFormat.getCurrencyInstance().format(finalPrice)

    }

    /**
     * Set the [desiredBreadType] for this order's state.
     * Only 1 type of bread can be selected for the whole order.
     * @param desiredBreadType is the selected bread type
     */
    fun setBreadType(desiredBreadType: String) : String {
        _uiState.update { currentState ->
            currentState.copy(breadType = desiredBreadType)
        }
        return desiredBreadType
    }

    /**
     * Set the [desiredSandwichType] for this order's state.
     * Only 1 sandwich type can be selected for the whole order.
     * @param desiredSandwichType is the desired sandwich type
     */
    fun setSandwichType(desiredSandwichType: String) {
        _uiState.update { currentState ->
            currentState.copy(sandwichType = desiredSandwichType,
                price = CalculatePrice(sandwichType = desiredSandwichType)
            )

        }
    }

    /**
     * Resets the state to its initial values
     */
    fun resetOrder() {
        _uiState.value = OrderUiState()
    }



}