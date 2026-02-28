package com.example.composestylelab.labs.custom_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.style.ExperimentalFoundationStyleApi
import androidx.compose.foundation.style.Style
import androidx.compose.foundation.style.pressed
import androidx.compose.foundation.style.styleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationStyleApi::class)
object StyledChipDefaults {
    @Composable
    fun style(): Style {
        val bg = MaterialTheme.colorScheme.secondaryContainer
        val fg = MaterialTheme.colorScheme.onSecondaryContainer
        return Style {
            background(bg)
            shape(RoundedCornerShape(8.dp))
            contentPadding(horizontal = 16.dp, vertical = 8.dp)
            contentColor(fg)
            pressed(Style {
                animate(Style {
                    scale(0.95f)
                })
            })
        }
    }
}

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
fun StyledChip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: Style = StyledChipDefaults.style(),
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .styleable(style = style)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}
