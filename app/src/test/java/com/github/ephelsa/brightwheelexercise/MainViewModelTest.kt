@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

package com.github.ephelsa.brightwheelexercise

import app.cash.turbine.turbineScope
import com.github.ephelsa.brightwheelexercise.repository.RepoInformationRepositoryFake
import com.github.ephelsa.brightwheelexercise.utils.DataState
import com.github.ephelsa.brightwheelexercise.utils.ResultListOfRepositoryInformation
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beInstanceOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val repoInformationRepository = RepoInformationRepositoryFake()

    private val viewModel = MainViewModel(
        repoInformationRepository = repoInformationRepository
    )

    @BeforeEach
    fun beforeEach() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterEach
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Verify updates in onRepositories state flow done by the call of fetchRepositoryInformation`() =
        runTest {
            turbineScope {
                // Given
                val onRepositoriesReceiver = viewModel.onRepositories.testIn(
                    scope = backgroundScope,
                    name = "onRepositories"
                )

                // When
                viewModel.fetchRepositoryInformation()

                // Then
                onRepositoriesReceiver.awaitItem() should {
                    beInstanceOf<DataState.Initialized<ResultListOfRepositoryInformation>>()
                }

                onRepositoriesReceiver.awaitItem() should {
                    beInstanceOf<DataState.Loading<ResultListOfRepositoryInformation>>()
                }

                onRepositoriesReceiver.awaitItem() should {
                    beInstanceOf<DataState.ContentReady<ResultListOfRepositoryInformation>>()
                }

                onRepositoriesReceiver.ensureAllEventsConsumed()
            }
        }

    @Test
    fun `Verify updates in onRepositories and nextPage done by fetchNextRepositoryInformationContent`() =
        runTest {
            turbineScope {
                // Given
                viewModel.onRepositories.value = DataState.ContentReady(
                    content = repoInformationRepository.fetchRepositoriesByPages(INITIAL_PAGE)
                )

                val initialContentReady = viewModel.onRepositories.value.contentReady
                val onRepositoriesReceiver = viewModel.onRepositories.testIn(
                    scope = backgroundScope,
                    name = "onRepositories"
                )

                // When
                viewModel.fetchNextRepositoryInformationContent()

                // Then
                onRepositoriesReceiver.awaitItem() should {
                    // This is the initial state - for this particular test we don't really care about
                    // the data that is stored.
                    beInstanceOf<DataState.ContentReady<ResultListOfRepositoryInformation>>()
                }

                onRepositoriesReceiver.awaitItem() should {
                    beInstanceOf<DataState.UpdatingContent<ResultListOfRepositoryInformation>>()
                    it.contentReady shouldBe initialContentReady
                }

                onRepositoriesReceiver.awaitItem() should {
                    beInstanceOf<DataState.ContentReady<ResultListOfRepositoryInformation>>()
                }

                onRepositoriesReceiver.ensureAllEventsConsumed()
            }
        }
}