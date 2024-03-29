package ar.scacchipa.twittercloneapp.presentation.login

import android.webkit.WebResourceError
import android.webkit.WebViewClient
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.domain.usecase.AuthorizationUseCase
import ar.scacchipa.twittercloneapp.utils.Constants
import ar.scacchipa.twittercloneapp.utils.MainCoroutineTestRule
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.net.URI

@ExperimentalCoroutinesApi
class LoginWebSectionViewModelTest {

    private val mockAuthUseCae = mockk<AuthorizationUseCase>()

    private var subject: LoginWebSectionViewModel = LoginWebSectionViewModel(
        authorizationUseCase = mockAuthUseCae
    )

    @get:Rule
    var mainCoroutineTestRule = MainCoroutineTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun shouldGenerateSuccessTokenResource() = runTest {
        val uri = URI(
            "https://twittercloneendava.firebaseapp.com/__/auth/handler?state=state&" +
                    "code=SGVvLWIyclkweEJudVZWSFFyR3RqQUVadEdlSFZJRk1JLXRacllVb3BxRFhhOjE2NTcxMTQyMDA2ODY6MTowOmFjOjE"
        )

        coEvery {
            mockAuthUseCae.invoke("SGVvLWIyclkweEJudVZWSFFyR3RqQUVadEdlSFZJRk1JLXRacllVb3BxRFhhOjE2NTcxMTQyMDA2ODY6MTowOmFjOjE")
        } returns (
                ResponseDomain.Success(
                    MockTokenProvider.credential1()
                ))

        subject.onReceiveUrl(uri)

        Assert.assertTrue(
            subject.responseDomain.value ==
                    ResponseDomain.Success(
                        MockTokenProvider.credential1()
                    )
        )
    }

    @Test
    fun viewModelShouldGenerateCancelTokenResource() {
        val uri =
            URI("https://twittercloneendava.firebaseapp.com/__/auth/handler?error=access_denied&state=state")

        coEvery {
            mockAuthUseCae.invoke("SGVvLWIyclkweEJudVZWSFFyR3RqQUVadEdlSFZJRk1JLXRacllVb3BxRFhhOjE2NTcxMTQyMDA2ODY6MTowOmFjOjE")
        } returns (ResponseDomain.Success(
            MockTokenProvider.credential1()
        ))

        subject.onReceiveUrl(uri)

        Assert.assertTrue(
            subject.responseDomain.value == ResponseDomain.Cancel
        )
    }

    @Test
    fun webViewSendWebResourceErrorToSubject() {
        val mockedWebResourceError = mockk<WebResourceError>()
        every {
            mockedWebResourceError.errorCode
        } returns (WebViewClient.ERROR_HOST_LOOKUP)
        subject.onReceivedWebError(mockedWebResourceError)
        assertEquals(
            ResponseDomain.Error(error = Constants.ERROR_HOST_LOOKUP_TOKEN),
            subject.responseDomain.value
        )
    }

    @Test
    fun subjectGetsConsumableAuthCode() {
        val scopeExpected = listOf(
            "users.read", "tweet.read", "tweet.write", "offline.access",
            "list.read", "follows.read", "like.read", "like.write",
            "space.read"
        ).joinToString("%20")

        val expected = "https://twitter.com/i/oauth2/authorize?" +
                "response_type=code&" +
                "client_id=${Constants.CLIENT_ID}&" +
                "redirect_uri=${Constants.REDIRECT_URI}&" +
                "scope=${scopeExpected}&" +
                "state=state&" +
                "code_challenge=challenge&" +
                "code_challenge_method=plain"

        assertEquals(
            expected,
            subject.getConsumableAuthCode()
        )
    }

    @Test
    fun subjectHandleTheException() = runTest {
        val uri = URI(
            "https://twittercloneendava.firebaseapp.com/__/auth/handler?state=state&" +
                    "code=throw_exception"
        )

        coEvery {
            mockAuthUseCae.invoke("throw_exception")
        } throws java.lang.RuntimeException("error happened")


        subject.onReceiveUrl( uri )

        assertEquals(
            ResponseDomain.Exception(),
            subject.responseDomain.value
        )
    }
}

