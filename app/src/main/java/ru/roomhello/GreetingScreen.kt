package ru.roomhello

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GreetingScreen(viewModel: GreetingViewModel) {
    // Безопасное отслеживание Flow с привязкой к ЖЦ Compose
    val dbGreetingText by viewModel.uiState.collectAsStateWithLifecycle()

    // Внутренний стейт для текста в поле ввода TextField
    var inputText by remember { mutableStateOf("") }

    // Как только текст прилетает из БД, синхронизируем поле ввода
    LaunchedEffect(dbGreetingText) {
        if (dbGreetingText != "Загрузка...") {
            inputText = dbGreetingText
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dbGreetingText,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Изменить приветствие") },
            modifier = Modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.updateGreetingText(inputText)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сохранить в бд")
        }

    }
}