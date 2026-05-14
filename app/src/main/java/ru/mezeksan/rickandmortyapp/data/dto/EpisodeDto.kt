package ru.mezeksan.rickandmortyapp.data.dto

import com.google.gson.annotations.SerializedName

data class EpisodeDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("air_date") val airDate: String?,
    @SerializedName("episode") val episode: String?
)
