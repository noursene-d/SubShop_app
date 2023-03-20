package com.example.lab5


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * @param modifier modifiers to set to this composable
 * @param onOrderButtonClicked defines the behavior of the button when clicked
 */

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onOrderButtonClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(stringResource(id = R.string.welcome_screen_title), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10 .dp))
        Image(
            painter = painterResource(id = R.drawable.sandwich),
            contentDescription = null,
            modifier = modifier
                .width(200.dp)
                .height(100.dp)
        )
        Spacer(modifier = Modifier.height(400.dp))
        Row {
            Button(onClick = onOrderButtonClicked,
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(stringResource(id = R.string.order_now_button))
            }
            Spacer(modifier = Modifier.width(10 .dp))
            Button(onClick = { /*TODO*/ },
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(stringResource(id = R.string.previous_orders))
            }
        }
        
    }
}

/**
 * Composable that displays what the UI of the Welcome Screen.
 */

@Preview
@Composable
fun WelcomeScreenSPreview(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        //WelcomeScreen()
    }

}


