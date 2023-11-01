package com.github.ephelsa.brightwheelexercise.di

import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.ephelsa.brightwheelexercise.MainViewModel
import com.github.ephelsa.brightwheelexercise.repository.RepoInformationRepository
import com.github.ephelsa.brightwheelexercise.repository.RepoInformationRepositoryFake

// This is the simplest DI - not really fancy but functional
internal object DIModule {

    // ViewModels initializer factory
    fun viewModelFactory() = viewModelFactory {
        addInitializer(MainViewModel::class) {
            MainViewModel(
                repoInformationRepository = repoInformationRepository()
            )
        }

        // App other initializers below
    }

    /**
     * [RepoInformationRepository] dependency
     */
    fun repoInformationRepository(): RepoInformationRepository = RepoInformationRepositoryFake()
}