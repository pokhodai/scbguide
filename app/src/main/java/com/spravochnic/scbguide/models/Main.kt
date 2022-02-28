package com.spravochnic.scbguide.models

import com.spravochnic.scbguide.R

data class Main(
    val id: Long,
    val icon: Int,
    val name: String,
    val topic: String,
    val type: Int,
)

fun getMainCategories(): List<Main> {
    return listOf(
        Main(1L, R.drawable.ic_home, "Лекторий", "14", 1),
        Main(2L, R.drawable.ic_test, "Проверь себя", "3", 2)
    )
}

