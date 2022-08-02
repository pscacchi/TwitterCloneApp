package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.ResponseDomain
import ar.scacchipa.twittercloneapp.repository.IAuthorizationRepository
import ar.scacchipa.twittercloneapp.utils.Constants

open class AuthorizationUseCase(
    private val repository: IAuthorizationRepository
) {
    open suspend operator fun invoke(
        transitoryToken: String
    ): ResponseDomain {
        return repository.requestAccessToken(
            transitoryToken = transitoryToken,
            grantType = Constants.GRANT_TYPE_AUTHORIZATION_CODE,
            clientId = Constants.CLIENT_ID,
            redirectUri = Constants.REDIRECT_URI,
            codeVerifier = Constants.CODE_VERIFIER_CHALLENGE,
            state = Constants.STATE_STATE)
    }
}