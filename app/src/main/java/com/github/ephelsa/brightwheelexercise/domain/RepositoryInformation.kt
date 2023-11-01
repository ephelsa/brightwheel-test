package com.github.ephelsa.brightwheelexercise.domain

/**
 * Model to store information about a repository.
 */
data class RepositoryInformation(
    val id: Long,
    val fullName: String,
    val stars: Long,
    val topContributor: Contributor?,
)
