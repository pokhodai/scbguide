package com.spravochnic.scbguide.models

import com.spravochnic.scbguide.api.response.MainResponse

data class Main(
    val id: Int,
    val icon: String,
    val name: String,
    val type: Int,
    val viewType: Int
)

fun List<MainResponse.Main>?.toMain(): List<Main> {
    return this?.map { main ->
        Main(-1, "", "", -1, 1)
        Main(
            id = main.id ?: 0,
            icon = main.icon ?: "",
            name = main.name ?: "",
            type = main.type ?: 0,
            viewType = main.viewType ?: 2
        )
    } ?: listOf()
}

