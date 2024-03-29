package ar.scacchipa.twittercloneapp.presentation.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.scacchipa.twittercloneapp.domain.usecase.StarterUseCase
import ar.scacchipa.twittercloneapp.utils.MainCoroutineTestRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    @get:Rule
    var mainCoroutineTestRule = MainCoroutineTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mockCheckInUseCase = mockk<StarterUseCase>()

    private val subject = SplashViewModel(
        starterUseCase = mockCheckInUseCase
    )

    @Test
    fun checkCredentialReturnTrue() = runTest {
        coEvery {
            mockCheckInUseCase.invoke()
        } returns true
        subject.spendSplash()
        assertTrue(
            subject.mustLogin.value == true
        )
    }

    @Test
    fun checkSkipSplash() = runTest {
        subject.skipSplash()

        assertEquals(subject.mustLogin.value, true)
    }
}
