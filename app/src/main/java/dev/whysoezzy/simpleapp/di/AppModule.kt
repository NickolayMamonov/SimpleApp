package dev.whysoezzy.simpleapp.di

import android.content.Context
import dev.whysoezzy.data.network.Api
import dev.whysoezzy.data.repository.CacheRepositoryImpl
import dev.whysoezzy.data.repository.ListElementRepositoryImpl
import dev.whysoezzy.data.repository.LocalStorageRepositoryImpl
import dev.whysoezzy.domain.data.entity.ListElement
import dev.whysoezzy.domain.data.repository.CacheRepository
import dev.whysoezzy.domain.data.repository.ListElementRepository
import dev.whysoezzy.domain.data.repository.LocalStorageRepository
import dev.whysoezzy.domain.entity.ListElementEntity
import dev.whysoezzy.domain.mapper.ListElementMapper
import dev.whysoezzy.domain.mapper.Mapper
import dev.whysoezzy.domain.usecase.ElementByIdFromCacheUseCase
import dev.whysoezzy.domain.usecase.ElementByIdUseCase
import dev.whysoezzy.domain.usecase.ListElementUseCase
import dev.whysoezzy.ui.details.vm.DetailsViewModel
import dev.whysoezzy.ui.main.vm.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://mocki.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
    single {
        get<Context>().getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }
    single<LocalStorageRepository> { LocalStorageRepositoryImpl(get()) }
    single<CacheRepository> { CacheRepositoryImpl() }
    single<ListElementRepository> { ListElementRepositoryImpl() }
    single { ListElementUseCase(get(), get()) }
    single { ElementByIdUseCase(get(), get(), get()) }
    single { ElementByIdFromCacheUseCase(get(), get()) }
    single<Mapper<ListElement, ListElementEntity>> { ListElementMapper(get()) }
    viewModel { MainViewModel(get(), get(),get()) }
    viewModel { DetailsViewModel(get(), get(), get()) }

}