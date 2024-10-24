package uk.co.jordanterry.otel.github.models

@JvmInline
public value class Owner(public val value: String)

@JvmInline
public value class Repo(public val value: String)

@JvmInline
public value class OwnerRepo internal constructor(public val value: String)

public operator fun Owner.div(repo: Repo): OwnerRepo =
    OwnerRepo(value + "/" + repo.value)

@JvmInline
public value class Run(public val value: Long)