package ru.ssau.reviewzor.di

import com.google.gson.GsonBuilder
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.ssau.reviewzor.data.nw.NetworkServiceApi


val networkModule = module {
    factory { provideRetrofit() }
    single { provideNetworkServiceApi( retrofit = get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://reviewzor-api.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create(provideGSON()))
        .build()
}

fun provideNetworkServiceApi(retrofit: Retrofit): NetworkServiceApi =
    retrofit.create(NetworkServiceApi::class.java)

fun provideGSON() = GsonBuilder().setLenient().create()