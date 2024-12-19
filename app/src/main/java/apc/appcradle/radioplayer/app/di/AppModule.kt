package apc.appcradle.radioplayer.app.di

import apc.appcradle.radioplayer.data.Repository
import apc.appcradle.radioplayer.domain.RepositoryInterface
import apc.appcradle.radioplayer.ui.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { MainViewModel(get()) }

    single<RepositoryInterface> { Repository() }
}