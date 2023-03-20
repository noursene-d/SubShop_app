package com.example.lab5

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lab5.data.DataSource.breadType
import com.example.lab5.data.DataSource.sandwichType
import com.example.lab5.data.DataSource.toppings


/**
 * enum values that represent the screens in the app for the navigation
 */
enum class SubShopScreen(@StringRes val title: Int) {
    Welcome(title = R.string.welcome_screen),
    Order(title = R.string.order_screen),
    Receipt(title = R.string.receipt_screen)
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 * @param canNavigateBack a boolean that verifies if we can navigate back
 * @param navigateUp lambda function that controls the behavior of the navigate back icon
 */
@Composable
fun TopBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
){
    Column {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text("Noursene's Sub Shop")
            },
            backgroundColor =  MaterialTheme.colors.primarySurface,
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            }
            , actions = {
                IconButton(onClick = {/* Do Something*/ }) {
                    Icon(Icons.Filled.Share, null)
                }
                IconButton(onClick = {/* Do Something*/ }) {
                    Icon(Icons.Filled.Settings, null)
                }
            })
    }
}

/***
 * Displays and controls the information of the app
 * @param modifier modifiers to set to this composable
 * @param viewModel an instance of the viewModel
 * @param navController connects the screens for navigation
 */


@Composable
fun SubShopApp(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    //Get the name of the current screen
    val currentScreen = SubShopScreen.valueOf(
        backStackEntry?.destination?.route ?: SubShopScreen.Welcome.name
    )
    
    Scaffold(
        topBar = {
            TopBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigate(SubShopScreen.Welcome.name) }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        
        NavHost(
            navController = navController,
            startDestination = SubShopScreen.Welcome.name,
            modifier = modifier.padding(innerPadding)
        ){
            composable(route = SubShopScreen.Welcome.name) {
                WelcomeScreen(
                    onOrderButtonClicked = { navController.navigate(SubShopScreen.Order.name) }
                )
            }

            composable(route = SubShopScreen.Receipt.name) {
                ReceiptScreen(
                    orderUiState = uiState,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    }
                )
            }

            composable(route = SubShopScreen.Order.name) {
                val context = LocalContext.current
                OrderScreen(
                    price = uiState.price,
                    sandwich_type = sandwichType.map{ id -> context.resources.getString(id)},
                    bread_type = breadType.map { id -> context.resources.getString(id) },
                    toppings = toppings.map { id -> context.resources.getString(id) },
                    onSelectionChangedBread = { viewModel.setBreadType(it)
                                             },
                    onSelectionChangedSandwich = { viewModel.setSandwichType(it)
                        },
                    onSelectionChangedTopping = { topping, checkedState -> viewModel.getCheckedState(topping, checkedState)
                                                },
                    onNextButtonClicked = {
                        //viewModel.setQuantity()
                        navController.navigate(SubShopScreen.Receipt.name)
                    }
                )
            }

        }
        
    }
    

        
    
}

/**
 * Removes the previous data and returns back to the welcome screen
 * @param viewModel instance of the viewModel
 * @param navController connects the receipt screen to the welcome screen
 */


private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(SubShopScreen.Welcome.name, inclusive = false)
}

