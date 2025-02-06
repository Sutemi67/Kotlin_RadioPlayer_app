package apc.appcradle.radioplayer.app.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import apc.appcradle.radioplayer.data.Repository
import apc.appcradle.radioplayer.domain.RepositoryInterface
import apc.appcradle.radioplayer.ui.MainViewModel
import apc.appcradle.radioplayer.ui.SettingsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { MainViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }

    single<RepositoryInterface> { Repository() }
    single<SharedPreferences> { androidApplication().getSharedPreferences("colors", MODE_PRIVATE) }
}