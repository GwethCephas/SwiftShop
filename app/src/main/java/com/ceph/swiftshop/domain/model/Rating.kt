package com.ceph.swiftshop.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    val rate: Double,
    val count: Int
)
