package com.github.ephelsa.brightwheelexercise.repository

import com.github.ephelsa.brightwheelexercise.model.Contributor
import com.github.ephelsa.brightwheelexercise.model.RepositoryInformation
import com.github.ephelsa.brightwheelexercise.utils.ResultListOfRepositoryInformation
import kotlinx.coroutines.delay

/**
 * Fake [RepoInformationRepository] implementation useful for testing.
 */
internal class RepoInformationRepositoryFake(
    private val failureException: Exception? = null,
    private val withoutContributor: Boolean = false,
    private val totalPerPage: Int = 15,
    private val millisecondsDelay: Long = 0,
) : RepoInformationRepository {

    override suspend fun fetchRepositoriesByPages(page: Int): ResultListOfRepositoryInformation {
        delay(millisecondsDelay)

        if (failureException != null) {
            return Result.failure(failureException)
        }

        val start = (page - 1) * totalPerPage + 1
        val end = page * totalPerPage

        return Result.success((start..end).map(::generateFakeData))
    }

    private fun generateFakeData(id: Int) = RepositoryInformation(
        id = id.toLong(),
        fullName = "johndoe/loremipsum",
        stars = (id * 1_000).toLong(),
        topContributor = if (withoutContributor) null else Contributor(
            username = "pepito",
        ),
    )
}