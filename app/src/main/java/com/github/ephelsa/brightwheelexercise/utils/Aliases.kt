package com.github.ephelsa.brightwheelexercise.utils

// Callbacks
typealias VoidCallback = () -> Unit
typealias TypedCallback<T> = (T) -> Unit
typealias ScopedCallback<T> = T.() -> Unit
