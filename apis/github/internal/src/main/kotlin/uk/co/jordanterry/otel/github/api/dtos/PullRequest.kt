package uk.co.jordanterry.otel.github.api.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class PullRequest(
    val id: Long,
    val number: Int,
    val url: String,
    val head: Head,
    val base: Base
)
