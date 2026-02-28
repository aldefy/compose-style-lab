package com.example.composestylelab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.style.ExperimentalFoundationStyleApi
import androidx.compose.foundation.style.Style
import androidx.compose.foundation.style.pressed
import androidx.compose.foundation.style.styleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.composestylelab.navigation.LabRoute

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
fun LabCard(
    lab: LabRoute,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val surfaceColor = MaterialTheme.colorScheme.surfaceContainer
    val pressedColor = MaterialTheme.colorScheme.surfaceContainerHighest

    Column(
        modifier = modifier
            .fillMaxWidth()
            .styleable(
                style = Style {
                    background(surfaceColor)
                    shape(RoundedCornerShape(16.dp))
                    contentPadding(16.dp)
                    pressed(Style {
                        animate(Style {
                            background(pressedColor)
                            scale(0.97f)
                        })
                    })
                },
            )
            .clickable(onClick = onClick),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(lab.accentColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = lab.ordinal.plus(1).toString(),
                style = MaterialTheme.typography.headlineLarge,
                color = lab.accentColor,
            )
        }
        Text(
            text = lab.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 12.dp),
        )
        Text(
            text = lab.subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}
