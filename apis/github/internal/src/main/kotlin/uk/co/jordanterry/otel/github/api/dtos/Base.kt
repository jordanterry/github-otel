package uk.co.jordanterry.otel.github.api.dtos

import kotlinx.serialization.Serializable

@Serializable
public data class Base(
    val ref: String,
    val sha: String,
    val repo: Repository
)
