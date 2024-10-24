package uk.co.jordanterry.otel.github.api.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class User(
    val login: String?,
)
