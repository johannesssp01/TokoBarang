package com.example.myapplication

import android.app.Application
import com.example.myapplication.Database.preferences.CekTokoPreference
import com.example.myapplication.Network.ApiService
import com.example.myapplication.Network.EndPoint
import com.example.myapplication.Network.TokoBarangRepository
import com.example.myapplication.ui.home.HomeViewModelFactory
import com.example.myapplication.ui.login.LoginViewModelFactory
import com.example.myapplication.ui.proccess.AddEditViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import timber.log.Timber

class CekTokoAplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@CekTokoAplication))

        bind() from singleton { CekTokoPreference(instance()) }
        bind() from singleton { HomeViewModelFactory(instance()) }
        bind() from singleton { LoginViewModelFactory(instance()) }
        bind() from singleton { AddEditViewModelFactory(instance()) }
        bind() from singleton { TokoBarangRepository(instance(),instance()) }
        bind<EndPoint>() with singleton { ApiService.getClient() }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}