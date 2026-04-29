package ru.mezeksan.rickandmortyapp.presentation.util

import android.content.res.Resources
import org.json.JSONObject
import retrofit2.HttpException
import ru.mezeksan.rickandmortyapp.R
import java.io.IOException

private fun parseApiErrorField(errorBody: String?): String? {
    if (errorBody.isNullOrBlank()) return null
    return try {
        JSONObject(errorBody).optString("error", "").trim().takeIf { it.isNotEmpty() }
    } catch (_: Exception) {
        null
    }
}

fun Throwable.toUserMessage(resources: Resources): String {
    if (this is HttpException) {
        val code = code()
        val apiMessage = parseApiErrorField(response()?.errorBody()?.string())
        when (apiMessage) {
            "There is nothing here" -> return resources.getString(R.string.error_api_nothing_here)
            "Bad Request" -> return resources.getString(R.string.error_api_bad_request)
            else -> return resources.getString(R.string.error_http, code)
        }
    }
    if (this is IOException) {
        return resources.getString(R.string.error_network)
    }
    return resources.getString(R.string.error_unknown)
}
