package xyz.moevm.ecology.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Satellite
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.ui.res.stringResource
import xyz.moevm.ecology.R
import xyz.moevm.ecology.data.types.NavItem
import xyz.moevm.ecology.data.types.TopNavItem

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
        ),
        NavItem(R.string.user, icon = Icons.Filled.Person, "user/{id}", false)
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
        ),
        NavItem(R.string.user, icon = Icons.Filled.Person, "user/{id}", false)
    )

    val TopNavItems = listOf(
        TopNavItem(
            label = R.string.add_object,
            route = "add_object"
        ),
        TopNavItem(
            label = R.string.share_object,
            route = "share_screen"
        ),
        TopNavItem(
            label = R.string.title,
            route = "title"
        ),
        TopNavItem(
            label = R.string.settings,
            route = "settings"
        )
    )

    val objectTypes = listOf(
        R.string.object_type_1,
        R.string.object_type_2,
        R.string.object_type_3,
        R.string.object_type_4,
    )

    val objectTypesColors = mapOf(
        Pair(R.string.object_type_1, "green"),
        Pair(R.string.object_type_2, "red"),
        Pair(R.string.object_type_3, "aquamarine"),
        Pair(R.string.object_type_4, "darkgrey"),
    )
}