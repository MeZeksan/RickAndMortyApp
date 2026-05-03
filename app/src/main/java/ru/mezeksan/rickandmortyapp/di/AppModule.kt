package ru.mezeksan.rickandmortyapp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.mezeksan.rickandmortyapp.data.remote.ApiClient
import ru.mezeksan.rickandmortyapp.data.remote.CharacterApi
import ru.mezeksan.rickandmortyapp.data.repository.CharacterRepositoryImpl
import ru.mezeksan.rickandmortyapp.domain.repository.CharacterRepository
import ru.mezeksan.rickandmortyapp.domain.usecase.GetCharactersUseCase
import ru.mezeksan.rickandmortyapp.presentation.viewmodel.CharacterListViewModel

val appModule = module {

    //API
    single<CharacterApi> {
        ApiClient.retrofit.create(CharacterApi::class.java)
    }

    //Repo
    single<CharacterRepository> {
        CharacterRepositoryImpl(get())
    }

    //UseCase
    factory {
        GetCharactersUseCase(get())
    }

    //ViewModel
    viewModel {
        CharacterListViewModel(get())
    }
}