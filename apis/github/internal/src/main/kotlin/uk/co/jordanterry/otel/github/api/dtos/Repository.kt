package uk.co.jordanterry.otel.github.api.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class Repository(
    val id: Long,
    val name: String?,
    val fullName: String? = null,
    val owner: User,
)