package ar.scacchipa.twittercloneapp.data

import com.google.gson.annotations.SerializedName

data class UserAccessTokenData (
    @SerializedName("token_type") val tokenType: String = "",
    @SerializedName("expires_in") val expiresIn: Int = 0,
    @SerializedName("access_token") val accessToken: String = "",
    @SerializedName("scope") val scope: String = "",
    @SerializedName("refresh_token") val refreshToken: String = "",
    @SerializedName("error_description") val errorDescription: String = ""
)
