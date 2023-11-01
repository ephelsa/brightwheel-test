package com.github.ephelsa.brightwheelexercise.utils

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.github.ephelsa.brightwheelexercise.di.DIModule

internal inline fun <reified T : ViewModel> ComponentActivity.appViewModels(): Lazy<T> =
    viewModels {
        DIModule.provideViewModelFactory()
    }
