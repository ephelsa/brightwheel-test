package com.github.ephelsa.brightwheelexercise.model

/**
 * Helper interface to map models to domain models.
 */
interface ModelMapper<Domain> {
    fun asModel(): Domain
}