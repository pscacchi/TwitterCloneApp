package ar.scacchipa.twittercloneapp.viewmodel

import android.webkit.WebResourceError
import android.webkit.WebViewClient
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.scacchipa.twittercloneapp.data.ResponseDomain
import ar.scacchipa.twittercloneapp.domain.AuthorizationUseCase
import ar.scacchipa.twittercloneapp.domain.ConsumableAuthUseCase
import ar.scacchipa.twittercloneapp.utils.Constants
import kotlinx.coroutines.launch
import java.net.URI
import kotlin.collections.set

class AuthWebDialogViewModel (
    private val authorizationUseCase: AuthorizationUseCase,
    private val consumableAuthUseCase: ConsumableAuthUseCase
): ViewModel() {

    private val _responseDomain = MutableLiveData<ResponseDomain?>()
    val responseDomain = _responseDomain as LiveData<ResponseDomain?>

    fun onReceiveUrl(uri: URI) {
        if (uri.host == URI(Constants.REDIRECT_URI).host) {
            val queryParameters = getQueryParameters(uri.query)
            queryParameters[Constants.SERVER_PARAMETER_CODE]?.let { code ->
                this.generateUserAccessToken(code)
            }
            queryParameters[Constants.SERVER_PARAMETER_ERROR]?.let { error ->
                if (error == Constants.ERROR_ACCESS_DENIED) {
                    _responseDomain.value = ResponseDomain.Cancel
                }
            }
        }
    }
    fun onReceivedWebError(error: WebResourceError?) {
        if (error?.errorCode == WebViewClient.ERROR_HOST_LOOKUP) {
            _responseDomain.value = ResponseDomain.Error(error = Constants.ERROR_HOST_LOOKUP_TOKEN)
        }
    }

    private fun generateUserAccessToken(
        transitoryCode: String
    ) {
        viewModelScope.launch {
            _responseDomain.value = authorizationUseCase(
                    transitoryToken = transitoryCode,
            )
        }
    }

    private fun getQueryParameters(query: String): HashMap<String, String> {
        val params = query.split("&")

        val map = hashMapOf<String, String>()

        params.forEach { param ->
            val term = param.split("=")
            val name = term[0]
            val value = term[1]
            map[name] = value
        }
        return map
    }

    fun createTemporaryCodeUrl(): String {
        return consumableAuthUseCase()
    }
}
