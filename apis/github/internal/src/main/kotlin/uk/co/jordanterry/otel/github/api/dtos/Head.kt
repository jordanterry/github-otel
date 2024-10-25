package uk.co.jordanterry.otel.github.api.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Head(
    @SerialName("ref")
    val ref: String,
    @SerialName("sha")
    val sha: String,
    @SerialName("repo")
    val repo: Repository
)
