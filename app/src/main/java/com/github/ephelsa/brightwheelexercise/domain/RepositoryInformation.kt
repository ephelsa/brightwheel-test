package com.github.ephelsa.brightwheelexercise.domain

/**
 * Model to store information about a repository.
 */
data class RepositoryInformation(
    val id: Int,
    val name: String,
    val owner: String,
    val fullName: String,
    val stars: Long,
    val topContributor: Contributor?,
)
