@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

package com.github.ephelsa.brightwheelexercise.repository

import com.github.ephelsa.brightwheelexercise.datasource.RemoteRepoInfoDatasource
import com.github.ephelsa.brightwheelexercise.domain.Contributor
import com.github.ephelsa.brightwheelexercise.domain.RepositoryInformation
import com.github.ephelsa.brightwheelexercise.testutils.TestException
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

private val testException = TestException()
private val testRepoInfoList = listOf(
    RepositoryInformation(
        id = 1, fullName = "johndoe/repository", stars = 1_000, topContributor = null
    )
)

/**
 * Tests for [RepoInformationRepositoryImpl]
 */
class RepoInformationRepositoryImplTest {
    private val mockRemoteRepoInfoDatasource: RemoteRepoInfoDatasource = mockk()
    private lateinit var repoInformationRepositoryImpl: RepoInformationRepositoryImpl


    @BeforeEach
    fun beforeEach() {
        clearAllMocks()
        repoInformationRepositoryImpl = RepoInformationRepositoryImpl(
            remoteDatasource = mockRemoteRepoInfoDatasource
        )
    }

    @Test
    fun `When an exception is thrown by RemoteRepoInfoDatasource$fetchReposInfoByPage Then a Result$Failure is returned`() =
        runTest {
            // Given
            coEvery { mockRemoteRepoInfoDatasource.fetchReposInfoByPage(any()) } throws testException

            // When
            val got = repoInformationRepositoryImpl.fetchRepositoriesByPages(1)

            // Then
            coVerify { mockRemoteRepoInfoDatasource.fetchReposInfoByPage(any()) }

            got shouldBeFailure testException
        }

    @Test
    fun `When an HttpException is thrown by RemoteRepoInfoDatasource$fetchTopRepositoryContributor Then a Result$Success is returned with null 'topContributor'`() =
        runTest {
            // Given
            coEvery { mockRemoteRepoInfoDatasource.fetchReposInfoByPage(any()) } returns testRepoInfoList
            coEvery {
                mockRemoteRepoInfoDatasource.fetchTopRepositoryContributor(any())
            } throws HttpException(
                Response.error<Any>(HttpURLConnection.HTTP_FORBIDDEN, ResponseBody.create(null, ""))
            )

            // When
            val got = repoInformationRepositoryImpl.fetchRepositoriesByPages(1)

            // Then
            got shouldBeSuccess { it shouldBeEqual testRepoInfoList }
        }

    @Test
    fun `When everything goes OK Then a Result$Success is returned with all the data filled`() =
        runTest {
            // Given
            coEvery { mockRemoteRepoInfoDatasource.fetchReposInfoByPage(any()) } returns testRepoInfoList
            coEvery {
                mockRemoteRepoInfoDatasource.fetchTopRepositoryContributor(any())
            } returns Contributor("johndoe")

            // When
            val got = repoInformationRepositoryImpl.fetchRepositoriesByPages(1)

            // Then
            got shouldBeSuccess { list ->
                list shouldHaveSize testRepoInfoList.size
                list.first().should {
                    val expected = testRepoInfoList.first()

                    it.topContributor shouldNotBe null
                    it.fullName shouldBeEqual expected.fullName
                    it.id shouldBeEqual expected.id
                    it.stars shouldBeEqual expected.stars
                }
            }
        }
}