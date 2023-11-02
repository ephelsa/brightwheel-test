@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

package com.github.ephelsa.brightwheelexercise.datasource

import com.github.ephelsa.brightwheelexercise.di.DIModule
import com.github.ephelsa.brightwheelexercise.model.RepositoryInformation
import com.github.ephelsa.brightwheelexercise.remote.GithubRepositoryService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import java.net.HttpURLConnection

/**
 * Unit tests for [RemoteRepoInfoDatasourceImpl]
 */
class RemoteRepoInfoDatasourceImplTest {
    private val mockWebServer = MockWebServer()

    private lateinit var githubRepositoryService: GithubRepositoryService
    private lateinit var impl: RemoteRepoInfoDatasourceImpl

    @BeforeEach
    fun setup() {
        mockWebServer.start()

        githubRepositoryService = DIModule.provideGithubRepositoryService(
            retrofit = DIModule.provideRetrofitClient().newBuilder().baseUrl(mockWebServer.url("/"))
                .build()
        )

        impl = RemoteRepoInfoDatasourceImpl(
            dispatcher = UnconfinedTestDispatcher(),
            ghRepositoryService = githubRepositoryService
        )
    }

    @AfterEach
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `fetchReposInfoByPage when received an OK`() = runTest {
        // Given
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(
                """
                {
                    "items": [
                        {
                            "id": 1997,
                            "full_name": "ephelsa/brightwheel-test",
                             "stargazers_count": 1520
                        }
                    ]
                }
            """.trimIndent()
            )

        mockWebServer.enqueue(response)

        // When
        val result = impl.fetchReposInfoByPage(1)

        // Then
        result shouldHaveSize 1
        result.first().shouldBe(
            RepositoryInformation(
                id = 1997,
                fullName = "ephelsa/brightwheel-test",
                stars = 1520,
                topContributor = null
            )
        )
    }

    @Test
    fun `an Exception is thrown by fetchReposInfoByPage due an HTTP error code`() =
        runTest {
            // Given
            val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_FORBIDDEN)
            mockWebServer.enqueue(response)

            shouldThrow<HttpException> { impl.fetchReposInfoByPage(1) }
        }
}