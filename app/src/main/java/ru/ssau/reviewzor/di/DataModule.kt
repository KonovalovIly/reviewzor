package ru.ssau.reviewzor.di

import org.koin.dsl.module
import ru.ssau.reviewzor.data.db.PlaceBookDatabase

val dataModule = module {

    single<PlaceBookDatabase> {
        PlaceBookDatabase.getInstance(context = get())
    }

}