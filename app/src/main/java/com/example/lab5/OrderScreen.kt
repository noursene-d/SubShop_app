package com.example.lab5

import android.annotation.SuppressLint
import android.net.eap.EapSessionConfig.EapAkaPrimeConfig
import android.util.Log
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment


import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * @param price the total price of the order
 * @param modifier modifiers to set to this composable
 * @param sandwich_type list containing the sandwich types
 * @param bread_type list containing the bread types
 * @param toppings list containing all kinds of available toppings
 * @param onNextButtonClicked defines the behavior of when we click the "Submit button
 * @param onSelectionChangedBread stores the bread item selected by the user
 * @param onSelectionChangedSandwich stores the sandwich type selected by the user
 * @param onSelectionChangedTopping stores the topping chosen and and the state of the checkbox
 */
@Composable
fun OrderScreen(
    price: String,
    modifier: Modifier = Modifier,
    sandwich_type: List<String>,
    bread_type: List<String>,
    toppings: List<String>,
    onSelectionChangedBread: (String) -> Unit = {},
    onSelectionChangedSandwich: (String) -> Unit = {},
    onSelectionChangedTopping: (String, Boolean) -> Unit,
    onNextButtonClicked: () -> Unit = {},
) {
    var selectedValue1 by rememberSaveable { mutableStateOf("") }
    var selectedValue2 by rememberSaveable { mutableStateOf("") }
    var outlinedTextFieldValue by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(id = R.string.sandwich_type_title),
            fontWeight = FontWeight.Bold,
            modifier = modifier.align(Alignment.CenterHorizontally)
                //.padding(start = 16.dp)
        )
        Text(
            stringResource(id = R.string.sandwich_type_subtitle),
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        //Sandwich Type
       sandwich_type.forEach{item ->
            Row (
                modifier = Modifier
                    .selectable(
                    selected = selectedValue1 == item,
                    onClick = {
                        selectedValue1 = item
                        onSelectionChangedSandwich(item)
                    }
                ),
                verticalAlignment = CenterVertically
            ){
                RadioButton(
                    selected = selectedValue1 == item,
                    onClick = {
                        selectedValue1 = item
                        onSelectionChangedSandwich(item)
                    }
                )
                Text(item)
            }
        }
        //Bread Type
        Text(
            stringResource(R.string.bread_type_title),
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        bread_type.forEach{item ->
            Row (
                modifier = Modifier.selectable(
                    selected = selectedValue2 == item,
                    onClick = {
                        selectedValue2 = item
                        onSelectionChangedBread(item)
                    }
                ),
                verticalAlignment = CenterVertically
            ){
                RadioButton(
                    selected = selectedValue2 == item,
                    onClick = {
                        selectedValue2 = item
                        onSelectionChangedBread(item)
                    }
                )
                Text(item)
            }
        }
        //Toppings
        Text(
            stringResource(R.string.toppings_title),
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            stringResource(R.string.toppings_subtitle),
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        LazyHorizontalGrid(
            modifier = modifier.weight(1f),
            rows = GridCells.Fixed(6),
            horizontalArrangement = Arrangement.spacedBy(80.dp),
            verticalArrangement = Arrangement.spacedBy(10 .dp)

        )
        {
            items(toppings.size) { item ->
                CheckBoxGroup(
                    item = toppings.elementAt(item),
                    ToppingStorage = onSelectionChangedTopping
                )
            }

        }
        //Total Text Field + Submit button
        OutlinedTextField(
            value = outlinedTextFieldValue,
            onValueChange = {value ->
                outlinedTextFieldValue = value
            },
            enabled = false,
            placeholder = {
                Text(
                    text = "Total: $price",
                    modifier = modifier.align(Alignment.CenterHorizontally),
                )
                          },
            modifier = modifier.fillMaxWidth(),
            trailingIcon = { Button(
                onClick = onNextButtonClicked,
                modifier = modifier.offset(x = (-10) .dp)
            ) {
                Text(text = stringResource(R.string.submit))
            } }

        )

    }
}

/**
 * defines the behavior of a checkbox
 * @param item the text displayed in front of the checkbox
 * @param checked the boolean state of the checkbox
 * @param onCheckedChange lambda function defines the behavior of the checkbox when the state changes
 */

@Composable
fun OneCheckBox(
    item: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(item)
    }
}

/**
 * Defines the behavior of multiple checkboxes
 * @param item the item to display in front of the checkbox
 * @param ToppingStorage lambda function that stores the item and the state of the checkbox
 */
@Composable
fun CheckBoxGroup(
    item: String,
    ToppingStorage: (String, Boolean) -> Unit,
){
    var checkedState by rememberSaveable {
        mutableStateOf(false)
    }

    OneCheckBox(
        item = item,
        checked = checkedState,
        onCheckedChange = {
                checkedState = it
                ToppingStorage(item,checkedState)
        }
    )

}



/**
 * Composable that displays what the UI of the Order Screen.
 */

@Composable
@Preview
fun OrderScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

    }

}