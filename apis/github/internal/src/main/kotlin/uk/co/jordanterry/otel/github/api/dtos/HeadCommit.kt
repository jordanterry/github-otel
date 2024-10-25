package uk.co.jordanterry.otel.github.api.dtos

import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class HeadCommit(
    @SerialName("id")
    val id: String? = null,
    @SerialName("tree_id")
    val treeId: String? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("timestamp")
    @Serializable(with = InstantIso8601Serializer::class)
    val timestamp: Instant? = null,
    @SerialName("author")
    val author: User? = null,
    @SerialName("committer")
    val committer: User? = null
)
