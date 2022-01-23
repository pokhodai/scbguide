package com.spravochnic.scbguide.models

import com.spravochnic.scbguide.api.response.CategoriesResponse

data class Categories(
    val id: Int,
    val name: String,
    val numbers: String,
    val type: String
)

fun List<CategoriesResponse.Categories>.toCategories(): List<Categories> {
    return this.map {
        Categories(
            id = it.id ?: 0,
            name = it.name ?: "",
            numbers = it.numbers ?: "",
            type = it.type ?: ""
        )
    }
}
