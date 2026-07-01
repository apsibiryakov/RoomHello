package ru.roomhello

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewmodel.compose.viewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GreetingViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: GreetingRepository = mockk(relaxed = true)
    private lateinit var viewModel: GreetingViewModel

    // UnconfinedTestDispatcher мгновенно запускает корутины на текущем потоке
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun uiStateUpdatesOnRepositoryEmission() = runTest(testDispatcher) {
        val expectedMessage = "test GreetingViewModelTest.uiStateUpdatesOnRepositoryEmission"
        val fakeEntity = GreetingEntity(id = 1, message = expectedMessage)
        coEvery { repository.getGreeting() } returns flowOf(fakeEntity)

        viewModel = GreetingViewModel(repository)

        // Симуляция подписки экрана Jetpack Compose на uiState чтобы мгновенно обработать данные из репозитория
        backgroundScope.launch(testDispatcher) {
            viewModel.uiState.collect {}
        }

        assertEquals(expectedMessage, viewModel.uiState.value)
    }

    @Test
    fun updateGreetingCallsRepository() = runTest(testDispatcher) {
        viewModel = GreetingViewModel(repository)

        val expectedNewText = "test GreetingViewModelTest.updateGreetingCallsRepository"
        viewModel.updateGreetingText(expectedNewText)

        val fakeEntity = GreetingEntity(id = 1, message = expectedNewText)
        coVerify(exactly = 1) { repository.updateGreeting(fakeEntity) }

    }

}
