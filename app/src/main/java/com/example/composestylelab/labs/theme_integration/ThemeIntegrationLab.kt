package com.example.composestylelab.labs.theme_integration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.style.MutableStyleState
import androidx.compose.foundation.style.Style
import androidx.compose.foundation.style.pressed
import androidx.compose.foundation.style.styleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composestylelab.components.ActiveStyleProperties
import com.example.composestylelab.components.CodeSnippet
import com.example.composestylelab.components.LabScaffold
import com.example.composestylelab.components.StyleProperty
import com.example.composestylelab.theme.ComposeStyleLabTheme

@Composable
fun ThemeIntegrationLab(onBack: () -> Unit) {
    LabScaffold(
        title = "Theme Integration",
        description = "Styles can read CompositionLocals at resolution time. " +
            "Toggle dark/light mode below and watch every styled element " +
            "update instantly — the Style blocks capture MaterialTheme colors.",
        onBack = onBack,
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        var isDark by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isDark = !isDark }
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = if (isDark) "Dark Mode" else "Light Mode",
                style = MaterialTheme.typography.titleMedium,
            )
            Switch(
                checked = isDark,
                onCheckedChange = { isDark = it },
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Wrap the demo area in its own theme so toggling only affects
        // the styled elements, not the LabScaffold chrome.
        ComposeStyleLabTheme(darkTheme = isDark, dynamicColor = false) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                tonalElevation = 1.dp,
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ThemedCardDemo()

                    Spacer(modifier = Modifier.height(8.dp))

                    ActiveStyleProperties(
                        label = "THEMED CARD \u2192 Style { }",
                        properties = listOf(
                            StyleProperty("background", "surface (theme-aware)"),
                            StyleProperty("contentColor", "onSurface"),
                            StyleProperty("shape", "RoundedCorner(16dp)"),
                            StyleProperty("contentPadding", "20dp"),
                            StyleProperty("borderWidth", "2dp"),
                            StyleProperty("borderColor", "primary"),
                        ),
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ThemedPressButtonDemo()

                    Spacer(modifier = Modifier.height(8.dp))

                    ActiveStyleProperties(
                        label = "THEMED BUTTON \u2192 Style { }",
                        properties = listOf(
                            StyleProperty("background", "primary (theme-aware)"),
                            StyleProperty("contentColor", "onPrimary"),
                            StyleProperty("shape", "RoundedCorner(12dp)"),
                            StyleProperty("contentPadding", "16dp"),
                        ),
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    ActiveStyleProperties(
                        label = "PRESSED \u2192 animate(Style { ... })",
                        properties = listOf(
                            StyleProperty("background", "surface"),
                            StyleProperty("contentColor", "onSurface"),
                            StyleProperty("scale", "0.95f"),
                        ),
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ThemedOutlineDemo()

                    Spacer(modifier = Modifier.height(8.dp))

                    ActiveStyleProperties(
                        label = "THEMED OUTLINE \u2192 Style { }",
                        properties = listOf(
                            StyleProperty("background", "surfaceVariant"),
                            StyleProperty("contentColor", "onSurfaceVariant"),
                            StyleProperty("shape", "RoundedCorner(12dp)"),
                            StyleProperty("borderWidth", "1dp"),
                            StyleProperty("borderColor", "secondary"),
                        ),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // -- Explanation --
        Text(
            text = "How It Works",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "StyleScope extends CompositionLocalAccessorScope, which means " +
                "Style {} blocks can read CompositionLocals at resolution time. " +
                "In practice, you capture MaterialTheme colors inside a @Composable " +
                "function and use them in the Style builder. When the theme changes " +
                "(e.g. dark/light toggle), the styles automatically re-resolve with " +
                "the new color values.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CodeSnippet(
            code = """
                @Composable
                fun ThemedDemo(isDark: Boolean) {
                    ComposeStyleLabTheme(darkTheme = isDark) {
                        val primary = MaterialTheme.colorScheme.primary
                        val surface = MaterialTheme.colorScheme.surface
                        val onSurface = MaterialTheme.colorScheme.onSurface

                        val themedStyle = Style {
                            background(surface)
                            contentColor(onSurface)
                            shape(RoundedCornerShape(16.dp))
                            contentPadding(20.dp)
                            borderWidth(2.dp)
                            borderColor(primary)
                            pressed(Style {
                                animate(Style {
                                    background(primary)
                                    contentColor(surface)
                                })
                            })
                        }

                        Box(
                            Modifier
                                .styleable(style = themedStyle)
                                .clickable { }
                        ) {
                            Text("I react to theme changes!")
                        }
                    }
                }
            """,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// ---------------------------------------------------------------------------
// Section 1: Themed Card — border + background from MaterialTheme
// ---------------------------------------------------------------------------

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun ThemedCardDemo(modifier: Modifier = Modifier) {
    val primary = MaterialTheme.colorScheme.primary
    val surface = MaterialTheme.colorScheme.surface
    val onSurface = MaterialTheme.colorScheme.onSurface

    val cardStyle = Style {
        background(surface)
        contentColor(onSurface)
        shape(RoundedCornerShape(16.dp))
        contentPadding(20.dp)
        borderWidth(2.dp)
        borderColor(primary)
    }

    Text(
        text = "Themed Card",
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface,
    )

    Spacer(modifier = Modifier.height(8.dp))

    Box(
        modifier = modifier
            .fillMaxWidth()
            .styleable(style = cardStyle),
    ) {
        Column {
            Text(
                text = "Surface background + primary border",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Toggle the switch to see colors change",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Section 2: Themed Press Button — press swaps primary/surface colors
// ---------------------------------------------------------------------------

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun ThemedPressButtonDemo(modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val styleState = remember { MutableStyleState(interactionSource) }

    val primary = MaterialTheme.colorScheme.primary
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    val surface = MaterialTheme.colorScheme.surface
    val onSurface = MaterialTheme.colorScheme.onSurface

    val buttonStyle = Style {
        background(primary)
        contentColor(onPrimary)
        shape(RoundedCornerShape(12.dp))
        contentPadding(16.dp)
        pressed(Style {
            animate(Style {
                background(surface)
                contentColor(onSurface)
                scale(0.95f)
            })
        })
    }

    Text(
        text = "Themed Press Button",
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface,
    )

    Spacer(modifier = Modifier.height(8.dp))

    Box(
        modifier = modifier
            .fillMaxWidth()
            .styleable(styleState = styleState, style = buttonStyle)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) { },
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Press & Hold",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Swaps primary \u2194 surface on press",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Section 3: Themed Outline Element — tertiary + secondary colors
// ---------------------------------------------------------------------------

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun ThemedOutlineDemo(modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val styleState = remember { MutableStyleState(interactionSource) }

    val secondary = MaterialTheme.colorScheme.secondary
    val tertiary = MaterialTheme.colorScheme.tertiary
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    val outlineStyle = Style {
        background(surfaceVariant)
        contentColor(onSurfaceVariant)
        shape(RoundedCornerShape(12.dp))
        contentPadding(16.dp)
        borderWidth(1.dp)
        borderColor(secondary)
        pressed(Style {
            animate(Style {
                borderWidth(3.dp)
                borderColor(tertiary)
                background(tertiary.copy(alpha = 0.15f))
            })
        })
    }

    Text(
        text = "Themed Outline",
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface,
    )

    Spacer(modifier = Modifier.height(8.dp))

    Box(
        modifier = modifier
            .fillMaxWidth()
            .styleable(styleState = styleState, style = outlineStyle)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) { },
    ) {
        Column {
            Text(
                text = "Secondary border + surfaceVariant fill",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Press to see tertiary accent",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}
