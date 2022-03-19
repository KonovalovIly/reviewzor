package ru.ssau.reviewzor.di

import org.koin.dsl.module
import ru.ssau.reviewzor.data.BookmarkRepository

val domainModule = module {

    single<BookmarkRepository> {
        BookmarkRepository(db = get())
    }

}