package com.example.composestylelab.labs.custom_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.style.ExperimentalFoundationStyleApi
import androidx.compose.foundation.style.Style
import androidx.compose.foundation.style.pressed
import androidx.compose.foundation.style.styleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationStyleApi::class)
object StyledCardDefaults {
    @Composable
    fun style(): Style {
        val bg = MaterialTheme.colorScheme.surfaceContainer
        return Style {
            background(bg)
            shape(RoundedCornerShape(16.dp))
            contentPadding(20.dp)
            pressed(Style {
                animate(Style {
                    scale(0.98f)
                })
            })
        }
    }
}

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
fun StyledCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: Style = StyledCardDefaults.style(),
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .styleable(style = style)
            .clickable(onClick = onClick),
    ) {
        content()
    }
}
