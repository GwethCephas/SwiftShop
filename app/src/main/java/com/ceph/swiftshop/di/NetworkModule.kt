package com.ceph.swiftshop.di

import com.ceph.swiftshop.data.remote.ApiService
import com.ceph.swiftshop.data.utils.Constants
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {

        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL).build()
    }

    single { get<Retrofit>().create(ApiService::class.java) }

}