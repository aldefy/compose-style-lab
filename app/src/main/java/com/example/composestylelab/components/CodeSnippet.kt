package com.example.composestylelab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CodeSnippet(
    code: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = code.trimIndent(),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .horizontalScroll(rememberScrollState())
            .padding(16.dp),
        fontFamily = FontFamily.Monospace,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        color = MaterialTheme.colorScheme.onSurface,
    )
}
