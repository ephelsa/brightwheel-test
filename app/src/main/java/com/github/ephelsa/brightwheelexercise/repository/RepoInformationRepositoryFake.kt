package com.github.ephelsa.brightwheelexercise.repository

import com.github.ephelsa.brightwheelexercise.domain.Contributor
import com.github.ephelsa.brightwheelexercise.domain.RepositoryInformation
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

    override suspend fun fetchRepositoriesByPages(page: Int): Result<List<RepositoryInformation>> {
        delay(millisecondsDelay)

        if (failureException != null) {
            return Result.failure(failureException)
        }

        val start = (page - 1) * totalPerPage + 1
        val end = page * totalPerPage

        return Result.success((start..end).map(::generateFakeData))
    }

    private fun generateFakeData(id: Int) = RepositoryInformation(
        id = id,
        owner = "johndoe",
        name = "loremipsum",
        fullName = "johndoe/loremipsum",
        stars = (id * 1_000).toLong(),
        topContributor = if (withoutContributor) null else Contributor(
            username = "pepito",
            avatarUrl = "https://placekitten.com/100/100"
        ),
    )
}