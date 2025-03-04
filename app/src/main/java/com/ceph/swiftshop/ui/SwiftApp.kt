package com.ceph.swiftshop.ui

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import co.paystack.android.PaystackSdk
import com.ceph.swiftshop.di.databaseModule
import com.ceph.swiftshop.di.networkModule
import com.ceph.swiftshop.di.repositoryModule
import com.ceph.swiftshop.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SwiftApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SwiftApp)
            modules(
                viewModelModule,
                repositoryModule,
                networkModule,
                databaseModule,
            )
        }
        PaystackSdk.initialize(this)


        val channel = NotificationChannel(
            "PAYMENT_CHANNEL",
            "Paystack payment",
            NotificationManager.IMPORTANCE_HIGH
        )


        val notificationManager =
            getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}