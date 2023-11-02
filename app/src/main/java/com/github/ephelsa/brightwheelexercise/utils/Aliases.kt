package com.github.ephelsa.brightwheelexercise.utils

import com.github.ephelsa.brightwheelexercise.domain.RepositoryInformation

// Callbacks
typealias VoidCallback = () -> Unit
typealias TypedCallback<T> = (T) -> Unit
typealias ScopedCallback<T> = T.() -> Unit

// Types
typealias ResultListOfRepositoryInformation = Result<List<RepositoryInformation>>
