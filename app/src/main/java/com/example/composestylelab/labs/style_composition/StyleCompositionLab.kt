package com.example.composestylelab.labs.style_composition

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.style.ExperimentalFoundationStyleApi
import androidx.compose.foundation.style.Style
import androidx.compose.foundation.style.pressed
import androidx.compose.foundation.style.styleable
import androidx.compose.foundation.style.then
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composestylelab.components.ActiveStyleProperties
import com.example.composestylelab.components.CodeSnippet
import com.example.composestylelab.components.LabScaffold
import com.example.composestylelab.components.StyleProperty
import com.example.composestylelab.theme.LabCyan

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
fun StyleCompositionLab(onBack: () -> Unit) {
    var baseEnabled by remember { mutableStateOf(true) }
    var elevatedEnabled by remember { mutableStateOf(false) }
    var darkEnabled by remember { mutableStateOf(false) }

    val baseCard = Style {
        background(LabCyan.copy(alpha = 0.15f))
        shape(RoundedCornerShape(16.dp))
        contentPadding(horizontal = 24.dp, vertical = 20.dp)
    }

    val elevatedCard = Style {
        borderWidth(2.dp)
        borderColor(Color(0xFFB0BEC5))
        scale(1.02f)
    }

    val darkTheme = Style {
        background(Color(0xFF1E1E2E))
        contentColor(Color.White)
    }

    val composedStyle = remember(baseEnabled, elevatedEnabled, darkEnabled) {
        var result: Style = Style {}
        if (baseEnabled) result = result.then(baseCard)
        if (elevatedEnabled) result = result.then(elevatedCard)
        if (darkEnabled) result = result.then(darkTheme)
        result
    }

    LabScaffold(
        title = "Style Composition",
        description = "Demonstrates the then operator for layering multiple styles. " +
            "Toggle each layer to see how styles compose together — properties " +
            "from later styles override earlier ones.",
        onBack = onBack,
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Style Layers",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        StyleToggle(label = "Base Card", sublabel = "background, shape, padding", enabled = baseEnabled) { baseEnabled = it }
        StyleToggle(label = "Elevated", sublabel = "border, scale", enabled = elevatedEnabled) { elevatedEnabled = it }
        StyleToggle(label = "Dark Theme", sublabel = "dark background, light text", enabled = darkEnabled) { darkEnabled = it }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Preview",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .styleable(
                    style = composedStyle.then(
                        Style {
                            pressed(Style {
                                animate(Style {
                                    scale(0.97f)
                                })
                            })
                        },
                    ),
                )
                .clickable { },
        ) {
            Column {
                Text(
                    text = "Composed Card",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = buildString {
                        val layers = mutableListOf<String>()
                        if (baseEnabled) layers.add("base")
                        if (elevatedEnabled) layers.add("elevated")
                        if (darkEnabled) layers.add("dark")
                        if (layers.isEmpty()) append("No layers active")
                        else {
                            append("Active: ")
                            append(layers.joinToString(" .then() "))
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Live property readout — shows which Style properties each layer contributes
        ActiveStyleProperties(
            label = "BASE CARD → Style { }",
            properties = listOf(
                StyleProperty("background", "cyan@15%", LabCyan.copy(alpha = 0.15f)),
                StyleProperty("shape", "RoundedCorner(16dp)"),
                StyleProperty("contentPadding", "24×20 dp"),
            ),
            visible = baseEnabled,
        )

        if (baseEnabled && elevatedEnabled) {
            Spacer(modifier = Modifier.height(4.dp))
        }

        ActiveStyleProperties(
            label = "ELEVATED → .then(Style { })",
            properties = listOf(
                StyleProperty("borderWidth", "2dp"),
                StyleProperty("borderColor", "#B0BEC5", Color(0xFFB0BEC5)),
                StyleProperty("scale", "1.02f"),
            ),
            visible = elevatedEnabled,
        )

        if ((baseEnabled || elevatedEnabled) && darkEnabled) {
            Spacer(modifier = Modifier.height(4.dp))
        }

        ActiveStyleProperties(
            label = "DARK → .then(Style { })",
            properties = listOf(
                StyleProperty("background", "#1E1E2E ← overrides base", Color(0xFF1E1E2E)),
                StyleProperty("contentColor", "white", Color.White),
            ),
            visible = darkEnabled,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Factory Syntax",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .styleable(
                    style = Style(baseCard, elevatedCard),
                )
                .clickable { },
        ) {
            Text(
                text = "Style(baseCard, elevatedCard)",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .styleable(
                    style = Style(baseCard, elevatedCard, darkTheme),
                )
                .clickable { },
        ) {
            Text(
                text = "Style(base, elevated, dark)",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "How It Works",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        CodeSnippet(
            code = """
                val baseCard = Style {
                    background(color)
                    shape(RoundedCornerShape(16.dp))
                    contentPadding(horizontal = 24.dp, vertical = 20.dp)
                }
                val elevated = Style {
                    borderWidth(2.dp)
                    borderColor(Color(0xFFB0BEC5))
                    scale(1.02f)
                }
                val dark = Style {
                    background(Color(0xFF1E1E2E))
                    contentColor(Color.White)
                }

                // Chained composition:
                val composed = baseCard.then(elevated).then(dark)

                // Factory composition:
                val composed = Style(baseCard, elevated, dark)
            """,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun StyleToggle(
    label: String,
    sublabel: String,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle(!enabled) }
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = sublabel,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Switch(checked = enabled, onCheckedChange = onToggle)
    }
}
