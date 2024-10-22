package com.ingresso.challenge.model

data class ApiResponseModel(
    val items: List<MovieModel>,
    val count: Int
)

data class MovieModel(
    val id: String,
    val title: String,
    val originalTitle: String? = null,
    val type: String,
    val movieIdUrl: String = "",
    val ancineId: String? = null,
    val countryOrigin: String? = null,
    val priority: Int? = null,
    val contentRating: String? = null,
    val duration: String? = null,
    val rating: Double? = null,
    val synopsis: String? = null,
    val cast: String? = null,
    val director: String? = null,
    val distributor: String? = null,
    val inPreSale: Boolean? = null,
    val isReexhibition: Boolean? = null,
    val urlKey: String? = null,
    val isPlaying: Boolean? = null,
    val countIsPlaying: Int? = null,
    val premiereDate: PremiereDate? = null,
    val creationDate: String? = null,
    val city: String? = null,
    val siteURL: String? = null,
    val nationalSiteURL: String? = null,
    val images: List<ImageModel> = emptyList(),
    val genres: List<String> = emptyList(),
    val ratingDescriptors: List<String> = emptyList(),
    val accessibilityHubs: List<Any> = emptyList(),
    val completeTags: List<Any> = emptyList(),
    val tags: List<String> = emptyList(),
    val trailers: List<TrailerModel> = emptyList(),
    val partnershipType: String? = null,
    val rottenTomatoe: Any? = null
)

data class ImageModel(
    val url: String,
    val type: String
)

data class TrailerModel(
    val type: String,
    val url: String,
    val embeddedUrl: String
)

data class PremiereDate(
    val localDate: String,
    val isToday: Boolean,
    val dayOfWeek: String,
    val dayAndMonth: String,
    val hour: String,
    val year: String
)