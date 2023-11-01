package com.github.ephelsa.brightwheelexercise.domain

/**
 * Helper interface to map models to domain models.
 */
interface DomainMapper<Domain> {
    fun asDomain(): Domain
}