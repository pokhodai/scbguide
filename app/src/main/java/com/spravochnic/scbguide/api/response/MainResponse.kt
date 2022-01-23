package com.spravochnic.scbguide.api.response

data class MainResponse(
    val result: String?,
    val error: String?,
    val main: List<Main>?
) {
    data class Main(
        val id: Int?,
        val icon: String?,
        val name: String?,
        val type: Int?,
        val viewType: Int?
    )
}