package com.example.ip_test_task.ui.theme

import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.TopAppBarColors
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val dialogColor = Color(0xFF496696)
val appBarColor = Color(0xFFB0DBFA)
@OptIn(ExperimentalMaterial3Api::class)
val topAppBarColor = TopAppBarColors(
    containerColor = Color(0xFFB0DBFA),
    scrolledContainerColor = Color(0xFF59C2E2),
    actionIconContentColor = Color(0xFF59C2E2),
    navigationIconContentColor = Color(0xFF59C2E2),
    titleContentColor = Color.Black
)

val cardColors = CardColors(
    containerColor = Color.White,
    contentColor = Color.Black,
    disabledContainerColor = Color.White,
    disabledContentColor = Color.Black
)

val editButtonColors = IconButtonColors(
    containerColor = Color(0xFF650CE8),
    contentColor = Color.Black,
    disabledContainerColor = Color(0xFF650CE8),
    disabledContentColor = Color.Black
)

val deleteButtonColors = IconButtonColors(
    containerColor = Color(0xFFC94400),
    contentColor = Color.Black,
    disabledContainerColor = Color(0xFFC94400),
    disabledContentColor = Color.Black
)