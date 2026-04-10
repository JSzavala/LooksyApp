package com.example.Looksy.MenuDesplazableInferior.Presentacion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun VistaMenuDesplazableInferior(
    modifier: Modifier=Modifier,
    content: @Composable ColumnScope.()->Unit
)   {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                content=content
            )
        }
    }
}