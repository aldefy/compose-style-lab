package com.example.composestylelab.labs.shadow_play

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.style.ExperimentalFoundationStyleApi
import androidx.compose.foundation.style.Style
import androidx.compose.foundation.style.checked
import androidx.compose.foundation.style.pressed
import androidx.compose.foundation.style.styleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.composestylelab.components.ActiveStyleProperties
import com.example.composestylelab.components.CodeSnippet
import com.example.composestylelab.components.LabScaffold
import com.example.composestylelab.components.StyleProperty

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
fun ShadowPlayLab(onBack: () -> Unit) {
    LabScaffold(
        title = "Shadow Play",
        description = "Explores depth and neumorphic effects using the Styles API. " +
            "Uses border, background, alpha, and scale to create raised/pressed " +
            "illusions -- the building blocks of neumorphism via declarative styles.",
        onBack = onBack,
    ) {
        var isPressed by remember { mutableStateOf(false) }

        Spacer(modifier = Modifier.height(24.dp))

        // -- Section 1: Raised / Pressed Toggle ----------------------------

        Text(
            text = "Raised / Pressed Toggle",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Tap to toggle between raised and sunken states. Uses border " +
                "colors, background shifts, and scale to simulate depth.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        RaisedPressedToggle(
            isPressed = isPressed,
            onToggle = { isPressed = it },
        )

        Spacer(modifier = Modifier.height(8.dp))

        ActiveStyleProperties(
            label = "RAISED (BASE)",
            properties = listOf(
                StyleProperty("background", "#E0E5EC", Color(0xFFE0E5EC)),
                StyleProperty("shape", "RoundedCorner(20dp)"),
                StyleProperty("contentPadding", "24\u00D728dp"),
                StyleProperty("borderWidth", "2dp"),
                StyleProperty("borderColor", "white@80%", Color.White.copy(alpha = 0.8f)),
                StyleProperty("scale", "1.02f"),
            ),
        )

        Spacer(modifier = Modifier.height(4.dp))

        ActiveStyleProperties(
            label = "SUNKEN \u2192 checked(animate(Style { ... }))",
            properties = listOf(
                StyleProperty("borderColor", "#A3B1C6", Color(0xFFA3B1C6)),
                StyleProperty("background", "#D1D9E6", Color(0xFFD1D9E6)),
                StyleProperty("scale", "0.98f"),
                StyleProperty("alpha", "0.9f"),
            ),
            visible = isPressed,
        )

        Spacer(modifier = Modifier.height(32.dp))

        // -- Section 2: Depth Cards ----------------------------------------

        Text(
            text = "Depth Cards",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Three cards at increasing depth levels. Each uses progressively " +
                "stronger borders and background contrast to create elevation.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        DepthCards()

        Spacer(modifier = Modifier.height(8.dp))

        ActiveStyleProperties(
            label = "DEPTH CARD (BASE)",
            properties = listOf(
                StyleProperty("background", "#E8EDF2", Color(0xFFE8EDF2)),
                StyleProperty("shape", "RoundedCorner(16dp)"),
                StyleProperty("contentPadding", "20dp"),
                StyleProperty("borderWidth", "1dp"),
                StyleProperty("borderColor", "white@50%", Color.White.copy(alpha = 0.5f)),
                StyleProperty("pressed.scale", "0.97f"),
                StyleProperty("pressed.alpha", "0.85f"),
            ),
        )

        Spacer(modifier = Modifier.height(32.dp))

        // -- Section 3: Neumorphic Buttons ---------------------------------

        Text(
            text = "Neumorphic Buttons",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Soft-UI style buttons that depress on press. The raised state " +
                "uses a light top-left border and the pressed state inverts it.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        NeumorphicButtons()

        Spacer(modifier = Modifier.height(8.dp))

        ActiveStyleProperties(
            label = "NEUMORPHIC BUTTON (BASE)",
            properties = listOf(
                StyleProperty("background", "#E0E5EC", Color(0xFFE0E5EC)),
                StyleProperty("shape", "RoundedCorner(14dp)"),
                StyleProperty("contentPadding", "16\u00D714dp"),
                StyleProperty("borderWidth", "1.5dp"),
                StyleProperty("borderColor", "white@70%", Color.White.copy(alpha = 0.7f)),
                StyleProperty("scale", "1.01f"),
                StyleProperty("pressed.scale", "0.96f"),
                StyleProperty("pressed.alpha", "0.85f"),
            ),
        )

        Spacer(modifier = Modifier.height(32.dp))

        // -- Code Snippet --------------------------------------------------

        Text(
            text = "How It Works",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Depth illusions are built by combining border, background, alpha, " +
                "and scale properties in the Styles API. The checked() state function " +
                "toggles between raised and sunken appearances with animated transitions. " +
                "When dropShadow() and innerShadow() become publicly constructible, " +
                "these same patterns will gain true shadow rendering.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CodeSnippet(
            code = """
                // Raised / Pressed toggle via checked()
                val raisedPressedStyle = Style {
                    background(Color(0xFFE0E5EC))
                    shape(RoundedCornerShape(20.dp))
                    contentPadding(24.dp)
                    // "Raised" = light top-left border
                    borderWidth(2.dp)
                    borderColor(Color.White.copy(alpha = 0.8f))
                    scale(1.02f)

                    checked(Style {
                        animate(Style {
                            // "Sunken" = darker border
                            borderColor(Color(0xFFA3B1C6))
                            background(Color(0xFFD1D9E6))
                            scale(0.98f)
                            alpha(0.9f)
                        })
                    })
                }

                // Depth cards with increasing elevation
                val depthStyle = Style {
                    background(Color(0xFFE8EDF2))
                    shape(RoundedCornerShape(16.dp))
                    contentPadding(20.dp)
                    borderWidth(1.dp)
                    borderColor(Color.White.copy(alpha = 0.5f))
                    pressed(Style {
                        animate(Style {
                            scale(0.97f)
                            alpha(0.85f)
                        })
                    })
                }
            """,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// -- Neumorphic color palette --------------------------------------------------

private val NeumorphBg = Color(0xFFE0E5EC)
private val NeumorphLight = Color(0xFFFFFFFF)
private val NeumorphDark = Color(0xFFA3B1C6)
private val NeumorphSunken = Color(0xFFD1D9E6)
private val NeumorphText = Color(0xFF4A5568)

// -- Section 1: Raised / Pressed Toggle ----------------------------------------

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun RaisedPressedToggle(
    isPressed: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val raisedPressedStyle = Style {
        background(NeumorphBg)
        shape(RoundedCornerShape(20.dp))
        contentPadding(horizontal = 24.dp, vertical = 28.dp)
        borderWidth(2.dp)
        borderColor(NeumorphLight.copy(alpha = 0.8f))
        scale(1.02f)

        checked(Style {
            animate(Style {
                borderColor(NeumorphDark)
                background(NeumorphSunken)
                scale(0.98f)
                alpha(0.9f)
            })
        })
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .styleable(style = raisedPressedStyle)
            .toggleable(
                value = isPressed,
                onValueChange = onToggle,
                role = Role.Switch,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = if (isPressed) "Sunken" else "Raised",
                    style = MaterialTheme.typography.titleLarge,
                    color = NeumorphText,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isPressed) {
                        "Darker border, smaller scale, lower alpha"
                    } else {
                        "Light border, larger scale, full alpha"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = NeumorphText.copy(alpha = 0.7f),
                )
            }
            Icon(
                imageVector = if (isPressed) {
                    Icons.Filled.KeyboardArrowDown
                } else {
                    Icons.Filled.KeyboardArrowUp
                },
                contentDescription = if (isPressed) "Sunken" else "Raised",
                tint = NeumorphText,
                modifier = Modifier.size(32.dp),
            )
        }
    }
}

// -- Section 2: Depth Cards ----------------------------------------------------

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun DepthCards(modifier: Modifier = Modifier) {
    val depths = listOf(
        DepthLevel("Flat", "No elevation", 0.dp, 0f, 0.3f),
        DepthLevel("Raised", "Subtle lift", 1.dp, 1.01f, 0.6f),
        DepthLevel("Elevated", "Strong depth", 2.dp, 1.03f, 0.9f),
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        depths.forEach { depth ->
            DepthCard(depth = depth)
        }
    }
}

private data class DepthLevel(
    val label: String,
    val description: String,
    val borderWidth: androidx.compose.ui.unit.Dp,
    val scale: Float,
    val borderAlpha: Float,
)

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun DepthCard(
    depth: DepthLevel,
    modifier: Modifier = Modifier,
) {
    val baseBackground = when (depth.label) {
        "Flat" -> Color(0xFFE8EDF2)
        "Raised" -> Color(0xFFE4E9F0)
        else -> Color(0xFFDEE4ED)
    }

    val style = Style {
        background(baseBackground)
        shape(RoundedCornerShape(16.dp))
        contentPadding(20.dp)

        if (depth.borderWidth > 0.dp) {
            borderWidth(depth.borderWidth)
            borderColor(NeumorphLight.copy(alpha = depth.borderAlpha))
        }

        if (depth.scale > 0f) {
            scale(depth.scale)
        }

        pressed(Style {
            animate(Style {
                scale(if (depth.scale > 0f) depth.scale * 0.96f else 0.97f)
                alpha(0.85f)
                borderColor(NeumorphDark.copy(alpha = 0.5f))
            })
        })
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .styleable(style = style)
            .clickable { },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = depth.label,
                    style = MaterialTheme.typography.titleMedium,
                    color = NeumorphText,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = depth.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = NeumorphText.copy(alpha = 0.6f),
                )
            }
            // Visual depth indicator -- stacked circles
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                val count = when (depth.label) {
                    "Flat" -> 1
                    "Raised" -> 2
                    else -> 3
                }
                repeat(count) {
                    DepthDot()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun DepthDot(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(10.dp)
            .styleable(
                style = Style {
                    background(NeumorphDark.copy(alpha = 0.5f))
                    shape(CircleShape)
                    borderWidth(0.5f.dp)
                    borderColor(NeumorphLight.copy(alpha = 0.5f))
                },
            ),
    )
}

// -- Section 3: Neumorphic Buttons ---------------------------------------------

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun NeumorphicButtons(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        NeumorphicButton(
            label = "Action",
            accentColor = Color(0xFF6C63FF),
            modifier = Modifier.weight(1f),
        )
        NeumorphicButton(
            label = "Confirm",
            accentColor = Color(0xFF00B894),
            modifier = Modifier.weight(1f),
        )
        NeumorphicButton(
            label = "Cancel",
            accentColor = Color(0xFFE17055),
            modifier = Modifier.weight(1f),
        )
    }
}

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun NeumorphicButton(
    label: String,
    accentColor: Color,
    modifier: Modifier = Modifier,
) {
    val style = Style {
        background(NeumorphBg)
        shape(RoundedCornerShape(14.dp))
        contentPadding(horizontal = 16.dp, vertical = 14.dp)
        borderWidth(1.5f.dp)
        borderColor(NeumorphLight.copy(alpha = 0.7f))
        scale(1.01f)

        pressed(Style {
            animate(Style {
                borderColor(NeumorphDark.copy(alpha = 0.6f))
                background(NeumorphSunken)
                scale(0.96f)
                alpha(0.85f)
            })
        })
    }

    Box(
        modifier = modifier
            .styleable(style = style)
            .clickable { },
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .styleable(
                        style = Style {
                            background(accentColor)
                            shape(CircleShape)
                        },
                    ),
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = NeumorphText,
            )
        }
    }
}
