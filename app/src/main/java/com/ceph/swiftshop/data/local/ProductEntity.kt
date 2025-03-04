package com.ceph.swiftshop.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("products_table")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val image: String,
    val price: Double
)
