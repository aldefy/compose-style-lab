package com.example.composestylelab.labs.custom_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.style.ExperimentalFoundationStyleApi
import androidx.compose.foundation.style.Style
import androidx.compose.foundation.style.pressed
import androidx.compose.foundation.style.then
import androidx.compose.foundation.style.styleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composestylelab.components.ActiveStyleProperties
import com.example.composestylelab.components.CodeSnippet
import com.example.composestylelab.components.LabScaffold
import com.example.composestylelab.components.StyleProperty
import com.example.composestylelab.theme.LabTeal

@OptIn(ExperimentalFoundationStyleApi::class, ExperimentalLayoutApi::class)
@Composable
fun CustomComponentsLab(onBack: () -> Unit) {
    LabScaffold(
        title = "Custom Components",
        description = "Building reusable components that accept style as a parameter — " +
            "following Compose API guidelines (defaults object, modifier contract, slot APIs) " +
            "combined with the Styles API.",
        onBack = onBack,
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // --- Section 1: StyledChip — Default ---
        Text(
            text = "StyledChip — Default",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            StyledChip(onClick = {}) { Text("Kotlin") }
            StyledChip(onClick = {}) { Text("Compose") }
            StyledChip(onClick = {}) { Text("Styles API") }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ActiveStyleProperties(
            label = "StyledChipDefaults.style()",
            properties = listOf(
                StyleProperty("background", "secondaryContainer", MaterialTheme.colorScheme.secondaryContainer),
                StyleProperty("shape", "RoundedCorner(8dp)"),
                StyleProperty("contentPadding", "16×8dp"),
                StyleProperty("contentColor", "onSecondaryContainer", MaterialTheme.colorScheme.onSecondaryContainer),
            ),
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Section 2: StyledChip — Custom ---
        Text(
            text = "StyledChip — Custom Styles",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            StyledChip(
                onClick = {},
                style = Style {
                    background(LabTeal)
                    shape(CircleShape)
                    contentPadding(horizontal = 20.dp, vertical = 10.dp)
                    contentColor(Color.Black)
                    pressed(Style {
                        animate(Style { scale(0.9f) })
                    })
                },
            ) {
                Text("Pill Shape")
            }

            StyledChip(
                onClick = {},
                style = Style {
                    background(Color(0xFFFF6D00))
                    shape(CutCornerShape(6.dp))
                    contentPadding(horizontal = 16.dp, vertical = 8.dp)
                    contentColor(Color.White)
                    pressed(Style {
                        animate(Style { scale(0.92f) })
                    })
                },
            ) {
                Text("Cut Corners")
            }

            StyledChip(
                onClick = {},
                style = Style {
                    background(Color(0xFF6200EA))
                    shape(RoundedCornerShape(4.dp))
                    contentPadding(horizontal = 14.dp, vertical = 6.dp)
                    contentColor(Color.White)
                    borderWidth(1.dp)
                    borderColor(Color(0xFFBB86FC))
                    pressed(Style {
                        animate(Style { scale(0.95f) })
                    })
                },
            ) {
                Text("Bordered")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ActiveStyleProperties(
            label = "CUSTOM → Style { } overrides defaults entirely",
            properties = listOf(
                StyleProperty("background", "varies"),
                StyleProperty("shape", "varies"),
                StyleProperty("contentColor", "varies"),
            ),
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Section 3: StyledCard — Default ---
        Text(
            text = "StyledCard — Default",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        StyledCard(onClick = {}) {
            Column {
                Text(
                    text = "Default Card",
                    style = MaterialTheme.typography.titleSmall,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Uses StyledCardDefaults.style() — surfaceContainer background, " +
                        "16dp corners, 20dp padding, press-to-shrink.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ActiveStyleProperties(
            label = "StyledCardDefaults.style()",
            properties = listOf(
                StyleProperty("background", "surfaceContainer", MaterialTheme.colorScheme.surfaceContainer),
                StyleProperty("shape", "RoundedCorner(16dp)"),
                StyleProperty("contentPadding", "20dp"),
                StyleProperty("contentColor", "onSurface", MaterialTheme.colorScheme.onSurface),
            ),
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Section 4: StyledCard — Custom ---
        Text(
            text = "StyledCard — Custom Styles",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        StyledCard(
            onClick = {},
            style = Style {
                background(Color(0xFF1E1E2E))
                shape(RoundedCornerShape(24.dp))
                contentPadding(24.dp)
                contentColor(Color.White)
                pressed(Style {
                    animate(Style { scale(0.97f) })
                })
            },
        ) {
            Column {
                Text(
                    text = "Dark Card",
                    style = MaterialTheme.typography.titleSmall,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Fully overridden style — dark background, 24dp corners, white content.",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        StyledCard(
            onClick = {},
            style = Style {
                background(Color(0xFFFFF3E0))
                shape(CutCornerShape(topStart = 16.dp, bottomEnd = 16.dp))
                contentPadding(20.dp)
                contentColor(Color(0xFF4E342E))
                borderWidth(1.dp)
                borderColor(Color(0xFFFFCC80))
                pressed(Style {
                    animate(Style { scale(0.98f) })
                })
            },
        ) {
            Column {
                Text(
                    text = "Warm Card",
                    style = MaterialTheme.typography.titleSmall,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Asymmetric cut corners, warm palette, bordered.",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Section 5: Composition — defaults.then(override) ---
        Text(
            text = "Composition — Defaults + Override",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Uses StyledCardDefaults.style().then(accentStyle) to layer an accent " +
                "on top of the default card style.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        val accentOverlay = Style {
            borderWidth(2.dp)
            borderColor(LabTeal)
            scale(1.01f)
        }

        StyledCard(
            onClick = {},
            style = StyledCardDefaults.style().then(accentOverlay),
        ) {
            Column {
                Text(
                    text = "Composed Card",
                    style = MaterialTheme.typography.titleSmall,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Default style + teal border accent via .then()",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ActiveStyleProperties(
            label = "StyledCardDefaults.style().then(accentOverlay)",
            properties = listOf(
                StyleProperty("background", "surfaceContainer", MaterialTheme.colorScheme.surfaceContainer),
                StyleProperty("shape", "RoundedCorner(16dp)"),
                StyleProperty("contentPadding", "20dp"),
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        ActiveStyleProperties(
            label = "ACCENT OVERLAY → .then(Style { })",
            properties = listOf(
                StyleProperty("borderWidth", "2dp"),
                StyleProperty("borderColor", "teal", LabTeal),
                StyleProperty("scale", "1.01f"),
            ),
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Section 6: Code Snippet ---
        Text(
            text = "Component API Pattern",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        CodeSnippet(
            code = """
                // 1. Defaults object with @Composable style()
                object StyledChipDefaults {
                    @Composable
                    fun style(): Style = Style {
                        background(MaterialTheme.colorScheme.secondaryContainer)
                        shape(RoundedCornerShape(8.dp))
                        contentPadding(horizontal = 16.dp, vertical = 8.dp)
                        contentColor(MaterialTheme.colorScheme.onSecondaryContainer)
                        pressed(Style { animate(Style { scale(0.95f) }) })
                    }
                }

                // 2. Component with style parameter + modifier contract
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

                // 3. Usage — default, custom, or composed
                StyledChip(onClick = {}) { Text("Default") }

                StyledChip(
                    onClick = {},
                    style = Style { background(teal) },
                ) { Text("Custom") }

                StyledChip(
                    onClick = {},
                    style = StyledChipDefaults.style().then(accent),
                ) { Text("Composed") }
            """,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}
