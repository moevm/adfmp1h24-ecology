package com.example.adfmpecology.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseAppLayout(modifier: Modifier = Modifier) {
    //remember navController so it does not get recreated on recomposition
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopBar(navController)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
        modifier = modifier
    ) { innerPadding ->
        NavHostContainer(navController, modifier = Modifier.padding(innerPadding))
    }
}