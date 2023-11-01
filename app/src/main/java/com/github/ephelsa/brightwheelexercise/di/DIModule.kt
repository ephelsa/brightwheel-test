package com.github.ephelsa.brightwheelexercise.di

import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.ephelsa.brightwheelexercise.MainViewModel

// This is the simplest DI - not really fancy but functional
internal object DIModule {

    // ViewModels initializer factory
    fun viewModelFactory() = viewModelFactory {
        addInitializer(MainViewModel::class) {
            MainViewModel()
        }

        // App other initializers below
    }
}