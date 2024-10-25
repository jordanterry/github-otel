package uk.co.jordanterry.otel.github.api.dtos

import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GithubJob(
    @SerialName("name")
    val name: String,
    @SerialName("steps")
    val steps: List<GithubStep>,
    @SerialName("started_at")
    @Serializable(
        with = InstantIso8601Serializer::class
    )
    val startedAt: Instant,
    @SerialName("created_at")
    @Serializable(
        with = InstantIso8601Serializer::class
    )
    val completedAt: Instant,
)
