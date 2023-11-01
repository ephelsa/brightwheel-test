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
    private val totalPerPage: Int = 5,
    private val millisecondsDelay: Long = 0,
) : RepoInformationRepository {

    override suspend fun fetchRepositoriesByPages(page: Int): Result<List<RepositoryInformation>> {
        delay(millisecondsDelay)

        if (failureException != null) {
            return Result.failure(failureException)
        }

        return Result.success((0..totalPerPage).map(::generateFakeData))
    }

    private fun generateFakeData(id: Int) = RepositoryInformation(
        id = id,
        owner = "johndoe",
        name = "loremipsum",
        fullName = "johndoe/loremipsum",
        topContributor = if (withoutContributor) null else Contributor(
            username = "pepito",
            avatarUrl = "https://placekitten.com/100/100"
        ),
    )
}