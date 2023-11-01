package com.github.ephelsa.brightwheelexercise.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.github.ephelsa.brightwheelexercise.MainViewModel
import com.github.ephelsa.brightwheelexercise.ui.template.FeedTemplate
import com.github.ephelsa.brightwheelexercise.ui.theme.BrightwheelExerciseTheme

@Composable
fun FeedScreen(
    mainViewModel: MainViewModel
) {
    val onRepositories by mainViewModel.onRepositories.collectAsState()

    LaunchedEffect(Unit) {
        mainViewModel.fetchRepositoryInformation()
    }

    BrightwheelExerciseTheme {
        FeedTemplate(onRepositories)
    }
}