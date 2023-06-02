package com.hevanafa.multiplescreendemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import androidx.navigation.NavHostController
import com.hevanafa.multiplescreendemo.ui.theme.MultipleScreenDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultipleScreenDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BaseScaffold()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BaseScaffold() {
        val viewModel: StateViewModel = viewModel()

        // for navigation & in-app routing
        val navController: NavHostController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = ScreenEnum.valueOf(
            backStackEntry?.destination?.route ?: ScreenEnum.Start.name
        )
        val canNavigateBack = navController.previousBackStackEntry != null

        Scaffold (topBar = {
            ExampleAppBar(
                currentScreen = currentScreen,
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() }
            )
        }) { innerPadding ->
            NavHost(navController, ScreenEnum.Start.name) {
                composable(ScreenEnum.Start.name) {
                    LazyColumn (modifier = Modifier.padding(innerPadding)) {
                        (1..100).map {
                            item {
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.setPage(it)
                                        navController.navigate(ScreenEnum.Page1.name)
                                    }) {
                                    Text("" + it)
                                }
                            }
                        }
                    }
                }

                composable(ScreenEnum.Page1.name) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)) {
                        Text("Page: " + viewModel.getPage())
                    }
                }

            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ExampleAppBar(
        currentScreen: ScreenEnum,
        canNavigateBack: Boolean,
        navigateUp: () -> Unit = {},
        modifier: Modifier = Modifier
    ) {
        TopAppBar(
            title = { Text("Example Screen Navigation") },
            modifier = modifier,
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            }
        )
    }
}

