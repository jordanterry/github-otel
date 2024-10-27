package uk.co.jordanterry.otel.github.api.dtos

import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GithubStep(
    @SerialName("name")
    val name: String,
    @SerialName("started_at")
    @Serializable(
        with = InstantIso8601Serializer::class
    )
    val startedAt: Instant? = null,
    @SerialName("completed_at")
    @Serializable(
        with = InstantIso8601Serializer::class
    )
    val completedAt: Instant? = null,
)