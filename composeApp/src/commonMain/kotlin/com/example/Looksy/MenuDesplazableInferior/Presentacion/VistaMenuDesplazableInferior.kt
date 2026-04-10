package com.example.Looksy.MenuDesplazableInferior.Presentacion

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun VistaMenuDesplazableInferior(
    onDismiss: () -> Unit, // Nuevo parámetro para el callback
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {    // 1. Un Box que ocupa toda la pantalla
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // Sin efecto visual de clic en el fondo
            ) { onDismiss() }
    ) {
        // 2. El contenido real del menú (tu LazyColumn)
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    Color.Gray,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { /* No hace nada, evita que el clic pase al fondo */ },
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    content = content
                )
            }
        }
    }
}