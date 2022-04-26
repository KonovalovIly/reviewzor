package ru.ssau.reviewzor.di

import org.koin.dsl.module
import ru.ssau.reviewzor.data.BookmarkRepository
import ru.ssau.reviewzor.data.NetworkRepository
import ru.ssau.reviewzor.data.TokenRepository

val domainModule = module {

    single<BookmarkRepository> {
        BookmarkRepository(db = get())
    }

    single<NetworkRepository> {
        NetworkRepository(api = get())
    }

    single<TokenRepository> {
        TokenRepository(context = get())
    }

}