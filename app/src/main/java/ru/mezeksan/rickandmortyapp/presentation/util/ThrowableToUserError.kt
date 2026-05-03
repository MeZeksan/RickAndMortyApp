package ru.mezeksan.rickandmortyapp.presentation.util

import retrofit2.HttpException
import ru.mezeksan.rickandmortyapp.presentation.state.UserErrorKind
import java.io.IOException

fun Throwable.toUserErrorKind(): UserErrorKind = when {
    this is HttpException && code() in 500..599 -> UserErrorKind.Server
    this is IOException -> UserErrorKind.Network
    else -> UserErrorKind.Generic
}
