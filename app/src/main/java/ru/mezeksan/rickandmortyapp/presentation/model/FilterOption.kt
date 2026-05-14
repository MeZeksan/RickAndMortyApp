package ru.mezeksan.rickandmortyapp.presentation.model

import androidx.annotation.StringRes

data class FilterOption(
    @param:StringRes val labelResId: Int,
    val apiValue: String?
)
