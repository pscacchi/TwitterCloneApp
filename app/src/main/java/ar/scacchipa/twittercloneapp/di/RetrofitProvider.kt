package ar.scacchipa.twittercloneapp.di

import ar.scacchipa.twittercloneapp.data.datasource.IAuthExternalSource
import ar.scacchipa.twittercloneapp.data.datasource.IOwnerUserExternalSource
import ar.scacchipa.twittercloneapp.data.datasource.ITweetExternalSource
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.twitter.com/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
}

fun provideAuthSourceDataApi(retrofit: Retrofit): IAuthExternalSource =
    retrofit.create(IAuthExternalSource::class.java)

fun provideTweetDataApi(retrofit: Retrofit): ITweetExternalSource =
    retrofit.create(ITweetExternalSource::class.java)

fun provideOwnerUserDataApi(retrofit: Retrofit): IOwnerUserExternalSource =
    retrofit.create(IOwnerUserExternalSource::class.java)