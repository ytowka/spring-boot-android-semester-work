package com.danilkha.conentfrientdsclient.features.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.danilkha.conentfrientdsclient.core.ui.NavigationBar
import com.danilkha.conentfrientdsclient.features.users.ui.edit.EditUserScreen
import com.danilkha.conentfrientdsclient.features.users.ui.list.UserListScreen
import com.danilkha.conentfrientdsclient.features.auth.ui.AuthScreen
import com.danilkha.conentfrientdsclient.features.content.ui.ContentListScreen
import com.danilkha.conentfrientdsclient.features.review.ui.edit.ReviewEditorScreen
import com.danilkha.conentfrientdsclient.features.review.ui.list.ReviewListScreen
import com.danilkha.conentfrientdsclient.features.topics.ui.TopicScreen
import com.danilkha.conentfrientdsclient.features.users.domain.dto.RoleDto
import com.danilkha.conentfrientdsclient.features.users.ui.UserRoleModel
import com.danilkha.conentfrientdsclient.features.users.ui.info.UserInfoScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun RootScreen(
    viewModel: RootViewModel = koinViewModel()
){
    val navController = rememberNavController()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    LaunchedEffect(isLoggedIn){
        if(isLoggedIn == false){
            try {
                navController.navigate(
                    route = NavDestinations.AUTH,
                    navOptions = NavOptions
                        .Builder()
                        .setPopUpTo(NavDestinations.AUTH, inclusive = false, saveState = false)
                        .build(),
                    navigatorExtras = null,
                )
            }catch (_: Exception){}
        }
    }

    isLoggedIn?.let {
        Column(modifier = Modifier.fillMaxSize()) {
            NavHost(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .weight(1f),
                startDestination = if(it) NavDestinations.TOPIC_LIST else NavDestinations.AUTH,
                navController = navController,
            ){
                composable(NavDestinations.AUTH) {
                    AuthScreen{
                        navController.navigate(
                            route = NavDestinations.TOPIC_LIST,
                            navOptions = NavOptions
                                .Builder()
                                .setPopUpTo(NavDestinations.AUTH, inclusive = true, saveState = false)
                                .build(),
                            navigatorExtras = null,
                        )
                    }
                }
                composable(NavDestinations.TOPIC_LIST) {
                    TopicScreen (
                        onTopicClick = {
                            navController.navigate(NavDestinations.TopicContent(it))
                        },
                        onContentClick = {
                            navController.navigate(NavDestinations.ReviewList(it))
                        }
                    )
                }
                composable(
                    route = NavDestinations.TopicContent.destination,
                    arguments = listOf(navArgument(NavDestinations.TopicContent.topicIdArg) { type = NavType.LongType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getLong(NavDestinations.TopicContent.topicIdArg)!!
                    ContentListScreen(
                        viewModel = koinViewModel { parametersOf(id) },
                        onContentClick = { navController.navigate(NavDestinations.ReviewList(it)) },
                        onBack = { navController.navigateUp() }
                    )
                }
                composable(NavDestinations.USER_ADMIN_LIST){
                    UserListScreen(
                        onUserSelected = {
                            navController.navigate(NavDestinations.UserDetails(it.id.toString()))
                        }
                    )
                }
                composable(NavDestinations.USER_SEARCH){
                    UserListScreen(
                        onUserSelected = {
                            navController.navigate(NavDestinations.UserProfile(it.id.toString()))
                        }
                    )
                }
                composable(
                    route = NavDestinations.UserDetails.destination,
                    arguments = listOf(navArgument(NavDestinations.UserDetails.userIdArg) { type = NavType.StringType })
                ){ backStackEntry ->
                    EditUserScreen(
                        viewModel = koinViewModel { parametersOf(backStackEntry.arguments?.getString(NavDestinations.UserDetails.userIdArg)!!) },
                        onBack = {
                            navController.navigateUp()
                        }
                    )
                }

                composable(
                    route = NavDestinations.UserProfile.destination,
                    arguments = listOf(navArgument(NavDestinations.UserProfile.userIdArg) { type = NavType.StringType })
                ){ backStackEntry ->
                    UserInfoScreen(
                        viewModel = koinViewModel { parametersOf(backStackEntry.arguments?.getString(NavDestinations.UserDetails.userIdArg)!!) },
                        onBack = {
                            navController.navigateUp()
                        }
                    )
                }

                composable(
                    route = NavDestinations.ReviewList.destination,
                    arguments = listOf(navArgument(NavDestinations.ReviewList.contentIdArg) { type = NavType.LongType })
                ){ backStackEntry ->
                    val contentId = backStackEntry.arguments?.getLong(NavDestinations.ReviewList.contentIdArg)!!
                    ReviewListScreen(
                        viewModel = koinViewModel { parametersOf(contentId) },
                    )
                }

                composable(
                    route = NavDestinations.ReviewEditor.destination,
                    arguments = listOf(navArgument(NavDestinations.ReviewEditor.contentIdArg) { type = NavType.LongType })
                ){ backStackEntry ->
                    val contentId = backStackEntry.arguments?.getLong(NavDestinations.ReviewEditor.contentIdArg)!!
                    ReviewEditorScreen(
                        viewModel = koinViewModel { parametersOf(contentId) },
                    )
                }

            }
            var currentNavItem by remember { mutableIntStateOf(0) }
            if(isLoggedIn == true){
                NavigationBar(
                    items = {
                        if(currentUser?.role == RoleDto.ADMIN){
                            navigationItem(label = "admin", icon = Icons.Default.AccountCircle, onClick = {
                                val navOptions = NavOptions.Builder().setPopUpTo(NavDestinations.USER_ADMIN_LIST, false).build()
                                currentNavItem = it
                                navController.navigate(NavDestinations.USER_ADMIN_LIST, navOptions = navOptions)
                            })
                        }
                        navigationItem(label = "topics", icon = Icons.Default.Menu, onClick = {
                            val navOptions = NavOptions.Builder().setPopUpTo(NavDestinations.TOPIC_LIST, false).build()
                            currentNavItem = it
                            navController.navigate(NavDestinations.TOPIC_LIST, navOptions = navOptions)
                        })
                        navigationItem(label = "users", icon = Icons.Default.AccountCircle, onClick = {
                            val navOptions = NavOptions.Builder().setPopUpTo(NavDestinations.USER_SEARCH, false).build()
                            currentNavItem = it
                            navController.navigate(NavDestinations.USER_SEARCH, navOptions = navOptions)
                        })

                    },
                    selectedItemIndex = currentNavItem
                )
            }
        }
    }
}