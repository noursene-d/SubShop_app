package com.example.lab5

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab5.data.OrderUiState

/**
* Displays the UI elements of the Receipt screen
* @param modifier modifiers to set to this composable
* @param onCancelButtonClicked a lambda function that controls the "Done" button
 * @param orderUiState contains the data of the current order summary
*/


@Composable
fun ReceiptScreen(
    modifier: Modifier = Modifier,
    onCancelButtonClicked: () -> Unit,
    orderUiState: OrderUiState,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("${orderUiState.sandwichType} on ${orderUiState.breadType}")
        Text("with")
        orderUiState.topping.forEach {item ->
            Text(item)
        }
        Spacer(modifier = Modifier.height(50 .dp))
        Text("Total: ${orderUiState.price}")

        Spacer(modifier = Modifier.height(300 .dp))
        Button(onClick = onCancelButtonClicked) {
            Text(text = stringResource(R.string.done))
        }
    }

}


/**
 * Composable that displays what the UI of the Receipt Screen.
 */
@Composable
@Preview
fun ReceiptScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        //ReceiptScreen()
    }

}