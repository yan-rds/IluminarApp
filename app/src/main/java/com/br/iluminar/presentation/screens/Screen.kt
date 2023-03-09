package com.br.iluminar.presentation.screens

sealed class Screen(val route: String) {
    object DailyActivitiesScreen : Screen("daily_activities_screen")
    object AddActivityScreen : Screen("add_activity_screen")
    object MessagesScreen : Screen("messages_screen")
}
