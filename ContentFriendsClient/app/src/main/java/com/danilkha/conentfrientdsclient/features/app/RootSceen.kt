package com.danilkha.conentfrientdsclient.features.app

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import com.danilkha.conentfrientdsclient.features.users.ui.info.UserInfoScreen
import com.danilkha.conentfrientdsclient.features.users.ui.info.UserInfoViewModel
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
                            navController.navigate(NavDestinations.TopicContent(it.id, it.name))
                        },
                        onContentClick = {
                            navController.navigate(NavDestinations.ReviewList(it.id, it.name))
                        }
                    )
                }
                composable(
                    route = NavDestinations.TopicContent.destination,
                    arguments = listOf(
                        navArgument(NavDestinations.TopicContent.topicIdArg) { type = NavType.LongType },
                        navArgument(NavDestinations.TopicContent.topicNameArg) { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getLong(NavDestinations.TopicContent.topicIdArg)!!
                    val name = backStackEntry.arguments?.getString(NavDestinations.TopicContent.topicNameArg)!!
                    ContentListScreen(
                        viewModel = koinViewModel { parametersOf(id, name) },
                        onContentClick = { navController.navigate(NavDestinations.ReviewList(it.id, it.name)) },
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
                        },
                        onContentClick = { navController.navigate(
                            NavDestinations.ReviewList(
                                it.reviewModel.contentId,
                                it.reviewModel.contentName
                            )
                        ) },
                        onReviewClick = {}
                    )
                }

                composable(
                    route = NavDestinations.MY_ACCOUNT,
                ){
                    UserInfoScreen(
                        viewModel = koinViewModel { parametersOf(UserInfoViewModel.MY_USER_ID) },
                        onBack = {
                            navController.navigateUp()
                        },
                        onContentClick = { navController.navigate(
                            NavDestinations.ReviewList(
                                it.reviewModel.contentId,
                                it.reviewModel.contentName
                            )
                        ) },
                        onReviewClick = {
                            navController.navigate(NavDestinations.ReviewEditor(it.reviewModel.contentId))
                        }
                    )
                }

                composable(
                    route = NavDestinations.ReviewList.destination,
                    arguments = listOf(
                        navArgument(NavDestinations.ReviewList.contentIdArg) { type = NavType.LongType },
                        navArgument(NavDestinations.ReviewList.contentNameArg) { type = NavType.StringType },
                    )
                ){ backStackEntry ->
                    val contentId = backStackEntry.arguments?.getLong(NavDestinations.ReviewList.contentIdArg)!!
                    val contentName = backStackEntry.arguments?.getString(NavDestinations.ReviewList.contentNameArg)!!
                    ReviewListScreen(
                        viewModel = koinViewModel { parametersOf(contentId) },
                        contentName = contentName,
                        onBack = {
                            navController.navigateUp()
                        },
                        onWriteReview = {
                            navController.navigate(NavDestinations.ReviewEditor(contentId))
                        },
                        onCardClick = {
                            navController.navigate(NavDestinations.UserProfile(it.reviewUserInfo.userId))
                        }
                    )
                }

                composable(
                    route = NavDestinations.ReviewEditor.destination,
                    arguments = listOf(navArgument(NavDestinations.ReviewEditor.contentIdArg) { type = NavType.LongType })
                ){ backStackEntry ->
                    val contentId = backStackEntry.arguments?.getLong(NavDestinations.ReviewEditor.contentIdArg)!!
                    ReviewEditorScreen(
                        viewModel = koinViewModel { parametersOf(contentId) },
                        onBack = { navController.navigateUp() },
                        onSave = {  navController.navigate(NavDestinations.MY_ACCOUNT) }
                    )
                }

            }
            var currentNavItem by remember { mutableIntStateOf(0) }

            LaunchedEffect(navController.currentDestination){
                navController.currentDestination?.apply {
                    Log.d("debugg", "RootScreen() called ${label} ${route} ${id}")
                }

                when(navController.currentDestination?.route){
                    NavDestinations.MY_ACCOUNT -> currentNavItem = 2
                    NavDestinations.USER_SEARCH -> currentNavItem = 1
                    NavDestinations.TOPIC_LIST -> currentNavItem = 0
                    NavDestinations.USER_ADMIN_LIST -> currentNavItem = 3
                }
            }
            if(isLoggedIn == true){
                NavigationBar(
                    items = {
                        navigationItem(label = "Главная", icon = Icons.Default.Menu, onClick = {
                            val navOptions = NavOptions.Builder().setPopUpTo(NavDestinations.TOPIC_LIST, false).build()
                            currentNavItem = it
                            navController.navigate(NavDestinations.TOPIC_LIST, navOptions = navOptions)
                        })
                        navigationItem(label = "Пользователи", icon = Icons.Default.Groups, onClick = {
                            val navOptions = NavOptions.Builder().setPopUpTo(NavDestinations.USER_SEARCH, false).build()
                            currentNavItem = it
                            navController.navigate(NavDestinations.USER_SEARCH, navOptions = navOptions)
                        })
                        navigationItem(label = "Я", icon = Icons.Default.AccountCircle, onClick = {
                            val navOptions = NavOptions.Builder().setPopUpTo(NavDestinations.MY_ACCOUNT, false).build()
                            currentNavItem = it
                            navController.navigate(NavDestinations.MY_ACCOUNT, navOptions = navOptions)
                        })
                        if(currentUser?.role == RoleDto.ADMIN){
                            navigationItem(label = "Админ", icon = Icons.Default.AccountCircle, onClick = {
                                val navOptions = NavOptions.Builder().setPopUpTo(NavDestinations.USER_ADMIN_LIST, false).build()
                                currentNavItem = it
                                navController.navigate(NavDestinations.USER_ADMIN_LIST, navOptions = navOptions)
                            })
                        }
                    },
                    selectedItemIndex = currentNavItem
                )
            }
        }
    }
}