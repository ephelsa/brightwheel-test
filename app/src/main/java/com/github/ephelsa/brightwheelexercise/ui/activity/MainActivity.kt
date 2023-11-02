package com.github.ephelsa.brightwheelexercise.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.ephelsa.brightwheelexercise.ui.screen.FeedScreen
import com.github.ephelsa.brightwheelexercise.utils.appViewModels

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by appViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeedScreen(viewModel)
        }
    }
}
