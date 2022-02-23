package com.spravochnic.scbguide.api.response

data class DetailsCategoriesResponse(
    val result: String?,
    val error: String?,
    val detailsCategories: List<DetailCategory>?
) {
    data class DetailCategory(
        val id: Int?,
        val name: String?,
        val type: String?,
        val image: String?,
        val description: String?
    )
}