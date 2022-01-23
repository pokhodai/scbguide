package com.spravochnic.scbguide.models

import com.spravochnic.scbguide.R

data class Main(
    val id: Int,
    val icon: Int,
    val name: String,
    val topic: String,
    val type: Int,
    val viewType: Int
)

fun getMainCategories(): List<Main>{
    return listOf(
        Main(-1, 0, "", "", -1, 1),
        Main(1, R.drawable.ic_lectory, "Лекторий", "14", 1, 2),
        Main(2, R.drawable.ic_test, "Проверь себя", "14", 2, 2),
    )
}

