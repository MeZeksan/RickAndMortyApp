package ru.mezeksan.rickandmortyapp.presentation.util

import java.io.IOException
import retrofit2.HttpException
import ru.mezeksan.rickandmortyapp.presentation.state.UserErrorKind

fun Throwable.toUserErrorKind(): UserErrorKind = when {
    this is HttpException && code() in 500..599 -> UserErrorKind.Server
    this is IOException -> UserErrorKind.Network
    else -> UserErrorKind.Generic
}
