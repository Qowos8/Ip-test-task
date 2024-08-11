package com.example.ip_test_task.utils

import com.example.ip_test_task.domain.entity.ProductItem

object MockData {
    val products = listOf(
        ProductItem(
            id = 1,
            name = "iPhone 13",
            time = 1633046400000,
            tags = listOf("Телефон", "Новый", "Распродажа"),
            amount = 15
        ),
        ProductItem(
            id = 2,
            name = "Samsung Galaxy S21",
            time = 1633132800000,
            tags = listOf("Телефон", "Хит"),
            amount = 30
        ),
        ProductItem(
            id = 3,
            name = "PlayStation 5",
            time = 1633219200000,
            tags = listOf("Игровая приставка", "Акция", "Распродажа"),
            amount = 7
        ),
        ProductItem(
            id = 4,
            name = "LG OLED TV",
            time = 1633056000000,
            tags = listOf("Телевизор", "Эксклюзив", "Ограниченный"),
            amount = 22
        ),
        ProductItem(
            id = 5,
            name = "Apple Watch Series 7",
            time = 1633920000000,
            tags = listOf("Часы", "Новый", "Рекомендуем"),
            amount = 0
        ),
        ProductItem(
            id = 6,
            name = "Xiaomi Mi 11",
            time = 1633478400000,
            tags = listOf("Телефон", "Скидка", "Распродажа"),
            amount = 5
        ),
        ProductItem(
            id = 7,
            name = "MacBook Air M1",
            time = 1633564800000,
            tags = listOf("Ноутбук", "Тренд"),
            amount = 12
        ),
        ProductItem(
            id = 8,
            name = "Amazon Kindle Paperwhite",
            time = 1633651200000,
            tags = listOf("Электронная книга", "Последний шанс", "Ограниченный"),
            amount = 18
        ),
        ProductItem(
            id = 9,
            name = "Fitbit Charge 5",
            time = 1633737600000,
            tags = emptyList(),
            amount = 27
        ),
        ProductItem(
            id = 10,
            name = "GoPro Hero 10",
            time = 1633824000000,
            tags = listOf("Камера", "Эксклюзив"),
            amount = 25
        )
    )
}
