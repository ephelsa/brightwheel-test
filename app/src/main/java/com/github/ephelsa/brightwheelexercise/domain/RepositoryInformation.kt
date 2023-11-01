package com.github.ephelsa.brightwheelexercise.domain

/**
 * Model to store information about a repository.
 */
data class RepositoryInformation(
    private val id: Int,
    private val name: String,
    private val owner: String,
    private val fullName: String,
    private val topContributor: Contributor?,
)
