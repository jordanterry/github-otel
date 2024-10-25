package uk.co.jordanterry.otel.github.api.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class PullRequest(
    @SerialName("id")
    val id: Long,
    @SerialName("number")
    val number: Int,
    @SerialName("url")
    val url: String,
    @SerialName("head")
    val head: Head,
    @SerialName("base")
    val base: Base
)
