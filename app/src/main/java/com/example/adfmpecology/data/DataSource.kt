package com.example.adfmpecology.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Satellite
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import com.example.adfmpecology.R
import com.example.adfmpecology.data.types.NavItem


object DataSource {
    val UserNavItems = listOf(
        NavItem(
            label = R.string.map,
            icon = Icons.Filled.Satellite,
            route = "map"
        ),
        NavItem(
            label = R.string.maps_list,
            icon = Icons.Filled.Dashboard,
            route = "maps_list"
        ),
        NavItem(
            label = R.string.objects_list,
            icon = Icons.Filled.Terrain,
            route = "objects_list"
        ),
        NavItem(
            label = R.string.profile,
            icon = Icons.Filled.Person,
            route = "profile"
        )
    )

    val AdminNavItems = listOf(
        NavItem(
            label = R.string.map,
            icon = Icons.Filled.Satellite,
            route = "map"
        ),
        NavItem(
            label = R.string.maps_list,
            icon = Icons.Filled.Dashboard,
            route = "maps_list"
        ),
        NavItem(
            label = R.string.objects_list,
            icon = Icons.Filled.Terrain,
            route = "objects_list"
        ),
        NavItem(
            label = R.string.users_list,
            icon = Icons.Filled.People,
            route = "users_list"
        ),
        NavItem(
            label = R.string.profile,
            icon = Icons.Filled.Person,
            route = "profile"
        )
    )
}