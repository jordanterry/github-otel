package uk.co.jordanterry.otel.github.api.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Repository(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String? = null,
    @SerialName("full_name")
    val fullName: String? = null,
    @SerialName("owner")
    val owner: User,
)