package com.zckj.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zckj.compose.data.Student
import com.zckj.compose.ui.Screen
import com.zckj.compose.ui.dialog.Alert
import com.zckj.compose.ui.theme.ComposeTheme
import com.zckj.compose.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHome()
                }
            }
        }
    }
}

@Composable
fun NavHome() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<MainViewModel>()
    val navigateToScreen: (String) -> Unit = { navController.navigateSingleTopTo(it) }
    LaunchedEffect(Unit) {
        viewModel.screenEvent.collect {
            navigateToScreen(it)
        }
    }
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            Home(viewModel)
        }
        composable(Screen.Author.route) {
            Author(navController)
        }
        composable(Screen.LazyList.route) {
            LazyList(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(vm: MainViewModel) {
    val navController = rememberNavController()
    val item = listOf(Screen.Profile, Screen.Dashboard)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Home", fontWeight = FontWeight.W400) },
                actions = {
                    IconButton(onClick = { vm.openDialog = true }, modifier = Modifier.padding(end = 14.dp)) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = null, Modifier.size(32.dp))
                    }
                }
            )
        },
        bottomBar = { BottomBar(navController = navController, item = item) },
        floatingActionButton = { Fab() }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Profile.route,
            Modifier.padding(it)
        ) {
            composable(Screen.Profile.route) {
                Profile()
            }
            composable(Screen.Dashboard.route) {
                Dashboard {
                    vm.sendScreen(Screen.Author.route)
                }
            }

        }
    }
    if (vm.openDialog) {
        Alert(vm)
    }
}

@Composable
fun Fab() {
    ExtendedFloatingActionButton(
        text = { Text(text = "Add Task") },
        icon = { Icon(Icons.Filled.Add, "Localized Description") },
        onClick = { },
        elevation = FloatingActionButtonDefaults.loweredElevation()
    )
}

@Composable
fun BottomBar(navController: NavHostController, item: List<Screen>) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        item.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigateSingleTopTo(screen.route)
                }
            )
        }
    }
}

@Composable
fun Profile() {
    Text(text = "Profile")
}

@Composable
fun Dashboard(click: () -> Unit = {}) {
    Column(modifier = Modifier.padding(12.dp)) {
        Text(text = "Dashboard")
        Button(onClick = click, modifier = Modifier.padding(top = 12.dp)) {
            Text(text = "open")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Author(navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Author") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                })
        },
    ) {
        Column(Modifier.padding(it)) {
            Text(text = "Author")
        }
    }
}

@Composable
fun LazyList(vm: MainViewModel) {
    ItemTest(vm)
}

@Composable
fun ItemTest(vm: MainViewModel) {
    LazyColumn {
        items(vm.students) { studentState ->
            // val (name, setName) = remember { mutableStateOf(it.name) }
            val student = studentState.value
            Row(Modifier.padding(start = 12.dp, top = 50.dp).clickable {
                studentState.value = student.copy(name = "connor")
            }) {
                Text(text = student.name)
                Text(text = student.age, Modifier.padding(start = 24.dp))
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }