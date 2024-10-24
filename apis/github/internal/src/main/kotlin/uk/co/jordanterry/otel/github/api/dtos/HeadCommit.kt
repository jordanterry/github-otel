package uk.co.jordanterry.otel.github.api.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class HeadCommit(
    val id: String?,
    val treeId: String?,
    val message: String?,
    val timestamp: String?, // Instant or OffsetDateTime depending on your needs
    val author: User?,
    val committer: User?
)
