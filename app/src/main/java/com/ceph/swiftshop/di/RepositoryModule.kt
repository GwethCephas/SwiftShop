package com.ceph.swiftshop.di

import com.ceph.swiftshop.data.repository.SwiftRepositoryImpl
import com.ceph.swiftshop.domain.repository.SwiftRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<SwiftRepository> { SwiftRepositoryImpl(get(), get()) }
}