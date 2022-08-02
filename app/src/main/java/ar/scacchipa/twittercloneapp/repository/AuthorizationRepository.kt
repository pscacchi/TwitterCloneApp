package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.data.IMapper
import ar.scacchipa.twittercloneapp.data.ResponseDomain
import ar.scacchipa.twittercloneapp.data.UserAccessTokenData
import ar.scacchipa.twittercloneapp.data.UserAccessTokenDomain
import ar.scacchipa.twittercloneapp.datasource.IAuthDataSource
import ar.scacchipa.twittercloneapp.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthorizationRepository(
    private val genAccessTokenSource: IAuthDataSource,
    private val mapper: IMapper<UserAccessTokenData, UserAccessTokenDomain>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): IAuthorizationRepository {
    override suspend fun requestAccessToken(
        transitoryToken: String,
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        state: String
    ): ResponseDomain {
        return withContext(dispatcher) {
            try {
                val token = genAccessTokenSource.genAccessTokenSourceCode(
                    transitoryToken = transitoryToken,
                    grantType = grantType,
                    clientId = clientId,
                    redirectUri = redirectUri,
                    codeVerifier = codeVerifier,
                    state = state
                )
                if (token.isSuccessful) {
                    token.body()?.let { userAccessToken ->
                        ResponseDomain.Success(
                            mapper.toDomain(userAccessToken)
                        )
                    } ?: ResponseDomain.Error()
                } else {
                    ResponseDomain.Error(
                        error = Constants.ERROR_HOST_LOOKUP_TOKEN,
                        errorDescription = token.body()?.errorDescription ?: ""
                    )
                }
            } catch (e: Exception) {
                ResponseDomain.Exception(message = e.message ?: "")
            }
        }
    }
}
