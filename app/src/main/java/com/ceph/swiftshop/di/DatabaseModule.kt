package com.ceph.swiftshop.di

import androidx.room.Room
import com.ceph.swiftshop.data.local.ProductDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            ProductDatabase::class.java,
            "product_database"
        ).build()
    }

    single { get<ProductDatabase>().dao }

}