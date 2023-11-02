package com.github.ephelsa.brightwheelexercise.ui.screen

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToIndex
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.ephelsa.brightwheelexercise.MainViewModel
import com.github.ephelsa.brightwheelexercise.repository.RepoInformationRepository
import com.github.ephelsa.brightwheelexercise.repository.RepoInformationRepositoryFake
import org.junit.Rule
import org.junit.Test

private typealias MainActivityComposeTestRule = AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>

/**
 * Helper function to setup the whole screen for test
 */
private fun MainActivityComposeTestRule.setupScreen(
    repo: RepoInformationRepository,
    testRuleScope: MainActivityComposeTestRule.() -> Unit
) {
    mainClock.autoAdvance = true

    setContent {
        val viewModel by activity.viewModels<MainViewModel> {
            viewModelFactory {
                addInitializer(MainViewModel::class) {
                    MainViewModel(repoInformationRepository = repo)
                }
            }
        }

        FeedScreen(mainViewModel = viewModel)
    }

    testRuleScope()
}

@OptIn(ExperimentalTestApi::class)
class FeedScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun test_happyPath() {
        val totalPages = 15
        val repoDelay = 100L

        composeTestRule.setupScreen(
            RepoInformationRepositoryFake(
                totalPerPage = totalPages,
                millisecondsDelay = repoDelay
            )
        ) {
            // Splash
            onNodeWithTag("FeedTemplateLoading").assertIsDisplayed()
            waitUntilDoesNotExist(hasTestTag("FeedTemplateLoading"))

            // Feed
            for (iteration in 1..5) {
                onNode(hasScrollAction())
                    .performScrollToIndex(totalPages * iteration)
                    .assertExists()
                onNode(hasTestTag("ListLoader")).assertIsDisplayed()

                // This is due the delay that exists in the Repository
                Thread.sleep(repoDelay)
            }
        }
    }

    @Test
    fun test_content_ErrorState() = composeTestRule.setupScreen(
        RepoInformationRepositoryFake(
            failureException = Exception("Test Content Error"),
        )
    ) {
        // Splash
        waitUntilDoesNotExist(hasTestTag("FeedTemplateLoading"))

        // Feed
        onNode(hasText("Test Content Error")).assertIsDisplayed()
    }

    @Test
    fun test_content_EmptyState() = composeTestRule.setupScreen(
        RepoInformationRepositoryFake(
            totalPerPage = 0
        )
    ) {
        // Splash
        waitUntilDoesNotExist(hasTestTag("FeedTemplateLoading"))

        // Feed
        onNode(hasText(":(", substring = true)).assertIsDisplayed()
    }
}