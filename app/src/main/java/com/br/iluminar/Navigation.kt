package com.br.iluminar

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.br.iluminar.presentation.AddTaskScreen
import com.br.iluminar.presentation.DailyActivitiesFullScreen
import com.br.iluminar.presentation.MessagesFullScreen
import com.br.iluminar.presentation.screens.Screen
import com.br.iluminar.presentation.viewmodels.AddTaskViewModel
import com.br.iluminar.presentation.viewmodels.DailyTasksViewModel
import com.br.iluminar.presentation.viewmodels.MessagesViewModel

@Composable
fun Navigation(dailyTasksViewModel: DailyTasksViewModel, addTaskViewModel: AddTaskViewModel, messagesViewModel: MessagesViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.DailyActivitiesScreen.route) {
        composable(route = Screen.DailyActivitiesScreen.route) {
            DailyActivitiesFullScreen(navController, dailyTasksViewModel)
        }
        composable(route = Screen.AddActivityScreen.route) {
            AddTaskScreen(navController, addTaskViewModel)
        }

        composable(route = Screen.MessagesScreen.route) {
            MessagesFullScreen(messagesViewModel)
        }
    }
}