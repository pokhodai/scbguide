package com.spravochnic.scbguide.models

import com.spravochnic.scbguide.R

data class TestCategories(
    val id: Int,
    val name: String,
    val type: String,
    val question: String,
    val icon: Int
)

fun getTestCategories(): List<TestCategories> {
    return listOf(
        TestCategories(1, "Легкий уровень", "Beginner", "15", R.drawable.ic_beginner),
        TestCategories(2, "Средний уровень", "Expert", "15", R.drawable.ic_expert),
        TestCategories(3, "Тяжелый уровень", "Legent", "15", R.drawable.ic_legent)
    )
}