package com.spravochnic.scbguide.models

import com.spravochnic.scbguide.api.response.DetailsCategoriesResponse

data class DetailCategory(
    val id: Int,
    val name: String,
    val type: String,
    val image: String,
    val description: String
)

fun List<DetailsCategoriesResponse.DetailCategory>.toDetailCategories() : List<DetailCategory> {
    return this.map { detailCategory ->
        DetailCategory(
            id = detailCategory.id ?: 0,
            name = detailCategory.name ?: "",
            type = detailCategory.type ?: "",
            image = detailCategory.image ?: "",
            description = detailCategory.description ?: ""
        )
    }
}