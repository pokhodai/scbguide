package com.spravochnic.scbguide.api.response

class CategoriesResponse(
    val result: String?,
    val error: String?,
    val categories: List<Categories>?
) {
    data class Categories(
        val id: Int?,
        val name: String?,
        val numbers: String?,
        val type: String?
    )
}