package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.data.UserAccessTokenMapper
import ar.scacchipa.twittercloneapp.data.datasource.IAuthDataSource
import ar.scacchipa.twittercloneapp.data.model.UserAccessToken
import ar.scacchipa.twittercloneapp.domain.model.Credential
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.utils.Constants
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class AuthorizationRepositoryTest {

    private var subject: IAuthorizationRepository = AuthorizationRepository(
        MockAuthDataSource(),
        UserAccessTokenMapper()
    )

    @Test
    fun repoShouldReturnASuccessToken() = runTest {
        val expected = ResponseDomain.Success(
            Credential(
                accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
                refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE",
            )
        )
        val actual = subject.requestAccessToken(
            transitoryToken = "SGVvLWIyclkweEJudVZWSFFyR3RqQUVadEdlSFZJRk1JLXRacllVb3BxRFhhOjE2NTcxMTQyMDA2ODY6MTowOmFjOjE",
            grantType = "authorization_code",
            clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
            redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
            codeVerifier = "challenge",
            state = "state"
        )
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun repoShouldReturnNoAuthToken() = runTest {
        val actual = subject.requestAccessToken(
            transitoryToken = "incorrect_password",
            grantType = "authorization_code",
            clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
            redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
            codeVerifier = "challenge",
            state = "state")
        val expected = ResponseDomain.Error(error = Constants.ERROR_HOST_LOOKUP_TOKEN)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun whenAuthRepoReceiveRequestWithoutBodyReturnError() = runTest {
        val mockAuthDataSource = mockk<IAuthDataSource>()
        coEvery { mockAuthDataSource.genAccessTokenSourceCode(
            transitoryToken = "incorrect_password",
            grantType = "authorization_code",
            clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
            redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
            codeVerifier = "challenge",
            state = "state"
        ) } returns Response.success(null)

        val subject = AuthorizationRepository(mockAuthDataSource, UserAccessTokenMapper())

        Assert.assertEquals(
            ResponseDomain.Error(), subject.requestAccessToken(
            transitoryToken = "incorrect_password",
            grantType = "authorization_code",
            clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
            redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
            codeVerifier = "challenge",
            state = "state"
        ))
    }

    @Test
    fun whenAuthorizationRepoCatchException() = runTest {
        val subject = AuthorizationRepository(
            genAccessTokenSource = MockExceptionAuthDataSource(),
            mapper = UserAccessTokenMapper()
        )

        Assert.assertEquals(
            ResponseDomain.Exception(), subject.requestAccessToken(
            transitoryToken = "incorrect_password",
            grantType = "authorization_code",
            clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
            redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
            codeVerifier = "challenge",
            state = "state"
        ))
    }
}

class MockAuthDataSource: IAuthDataSource {
    override suspend fun genAccessTokenSourceCode(
        transitoryToken: String,
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        state: String
    ): Response<UserAccessToken> {
        if (transitoryToken == "SGVvLWIyclkweEJudVZWSFFyR3RqQUVadEdlSFZJRk1JLXRacllVb3BxRFhhOjE2NTcxMTQyMDA2ODY6MTowOmFjOjE" &&
            grantType == "authorization_code" &&
            clientId == "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ" &&
            redirectUri == "https://twittercloneendava.firebaseapp.com/__/auth/handler&" &&
            codeVerifier == "challenge" &&
            state == "state"
        ) {
            return Response.success(
                UserAccessToken(
                    tokenType = "bearer",
                    expiresIn = 7200,
                    accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
                    scope = "tweet.read tweet.write",
                    refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE",
                    errorDescription = ""
                )
            )
        } else {
            return Response.error(
                401,
                ResponseBody.create(
                    MediaType.parse("text/plain"), "Some error"
                )
            )
        }
    }
}

class MockExceptionAuthDataSource: IAuthDataSource {
    override suspend fun genAccessTokenSourceCode(
        transitoryToken: String,
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        state: String
    ): Response<UserAccessToken> {
        throw Exception()
    }
}
