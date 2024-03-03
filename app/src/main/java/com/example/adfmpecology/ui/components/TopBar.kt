package com.example.adfmpecology.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.adfmpecology.R
import com.example.adfmpecology.data.DataSource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, modifier: Modifier = Modifier) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        navigationIcon = {
            IconButton(
                colors = IconButtonDefaults.filledIconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                onClick = { navController.navigate(DataSource.TopNavItems[0].route) }
            ) {
                Icon(
                    imageVector = Icons.Filled.GpsFixed,
                    contentDescription = stringResource(R.string.add_object)
                )
            }
        },
        title = {
            TextButton(
                onClick = { navController.navigate(DataSource.TopNavItems[1].route) }
            ) {
                Text(stringResource(R.string.title,), color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        actions = {
            IconButton(
                colors = IconButtonDefaults.filledIconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                onClick = { navController.navigate(DataSource.TopNavItems[2].route) }
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(R.string.settings)
                )
            }
        },
        modifier = modifier
    )
}