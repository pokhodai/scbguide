package com.spravochnic.scbguide.models

import com.spravochnic.scbguide.*

data class Categories(
    val id: Long,
    val name: String,
    val type: String,
    val topic: String,
    val icon: Int
)

fun getTestCategories(): List<Categories> {
    return listOf(
        Categories(1L, "Легкий уровень", "Beginner", "15", R.drawable.ic_beginner),
        Categories(2L, "Средний уровень", "Expert", "15", R.drawable.ic_expert),
        Categories(3L, "Тяжелый уровень", "Legent", "15", R.drawable.ic_legent)
    )
}

fun getLectoryCategories(): List<Categories> {
    return listOf(
        Categories(1L, "Светофоры", trafficlight, "13", R.drawable.ic_trafficlight),
        Categories(2L, "Стрелочные переводы", arrowtranslation, "4", R.drawable.ic_arrowtranslation),
        Categories(3L, "Муфты и коробки", box, "4", R.drawable.ic_box),
        Categories(4L, "Железнодорожное реле", relay, "16", R.drawable.ic_relay),
        Categories(5L, "Рельсовые цепи", railchain, "17", R.drawable.ic_railchain),
        Categories(6L, "Диспетчерская централизация", centralization, "11", R.drawable.ic_centralization),
        Categories(7L, "АЛСН", alsn, "7", R.drawable.ic_alsn),
        Categories(8L, "Перемычки", bridge, "5", R.drawable.ic_bridge),
        Categories(9L, "Электроприводы", edrive, "8", R.drawable.ic_edrive),
        Categories(10L, "АГЦ", gatc, "4", R.drawable.ic_gatc),
        Categories(11L, "Трансформаторы", transformer, "4", R.drawable.ic_transformer),
        Categories(12L, "АПС", aps, "5", R.drawable.ic_aps),
        Categories(13L, "Железнодорожные станции", station, "6", R.drawable.ic_station),
        Categories(14L,"БМРЦ", bmrc, "7", R.drawable.ic_bmrc)
    )
}