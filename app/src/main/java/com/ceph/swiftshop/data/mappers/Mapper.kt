package com.ceph.swiftshop.data.mappers

import com.ceph.swiftshop.data.local.ProductEntity
import com.ceph.swiftshop.domain.model.Product
import com.ceph.swiftshop.domain.model.Rating

fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        title = this.title,
        image = this.image,
        description = this.description,
        category = this.category,
        price = this.price
    )
}

fun ProductEntity.toProduct(): Product{
    return Product(
        id = this.id,
        title = this.title,
        image = this.image,
        description = this.description,
        category = this.category,
        price = this.price
    )
}