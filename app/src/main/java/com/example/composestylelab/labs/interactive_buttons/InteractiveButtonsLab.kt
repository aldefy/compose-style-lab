package com.example.composestylelab.labs.interactive_buttons

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
import androidx.compose.foundation.style.focused
import androidx.compose.foundation.style.hovered
import androidx.compose.foundation.style.pressed
import androidx.compose.foundation.style.styleable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composestylelab.components.CodeSnippet
import com.example.composestylelab.components.LabScaffold

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
fun InteractiveButtonsLab(onBack: () -> Unit) {
    LabScaffold(
        title = "Interactive Buttons",
        description = "Toggle the switches below to simulate interaction states. " +
            "Watch the showcase element react in real-time — all driven by a single Style definition.",
        onBack = onBack,
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // State simulation switches
        var isPressed by remember { mutableStateOf(false) }
        var isHovered by remember { mutableStateOf(false) }
        var isFocused by remember { mutableStateOf(false) }

        // Create a MutableStyleState we can drive manually
        val styleState = remember { MutableStyleState(MutableInteractionSource()) }
        styleState.isPressed = isPressed
        styleState.isHovered = isHovered
        styleState.isFocused = isFocused

        Text(
            text = "State Simulation",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        StateToggleRow(label = "Pressed", checked = isPressed, onCheckedChange = { isPressed = it })
        StateToggleRow(label = "Hovered", checked = isHovered, onCheckedChange = { isHovered = it })
        StateToggleRow(label = "Focused", checked = isFocused, onCheckedChange = { isFocused = it })

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Showcase",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        // The single showcase element driven by the simulated state
        val baseColor = Color(0xFF3D5AFE)
        val showcaseStyle = Style {
            background(baseColor)
            shape(RoundedCornerShape(16.dp))
            contentPadding(horizontal = 32.dp, vertical = 24.dp)
            pressed(Style {
                animate(Style {
                    background(Color(0xFF1A237E))
                    scale(0.92f)
                })
            })
            hovered(Style {
                animate(Style {
                    background(Color(0xFF536DFE))
                    scale(1.04f)
                    borderWidth(2.dp)
                    borderColor(Color.White.copy(alpha = 0.5f))
                })
            })
            focused(Style {
                animate(Style {
                    borderWidth(3.dp)
                    borderColor(Color.White)
                    background(Color(0xFF304FFE))
                })
            })
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .styleable(styleState = styleState, style = showcaseStyle),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Styled Element",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = when {
                        isPressed -> "State: Pressed"
                        isHovered -> "State: Hovered"
                        isFocused -> "State: Focused"
                        else -> "State: Default"
                    },
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "How It Works",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "One Style definition handles all interaction states. " +
                "The MutableStyleState is driven by the switches above, " +
                "simulating what normally happens via touch, mouse, or keyboard. " +
                "On a real device, pressed/hovered/focused are driven automatically " +
                "by Modifier.clickable(), selectable(), etc.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CodeSnippet(
            code = """
                val styleState = MutableStyleState(interactionSource)

                val style = Style {
                    background(Color.Blue)
                    shape(RoundedCornerShape(16.dp))

                    pressed(Style {
                        animate(Style {
                            background(Color.DarkBlue)
                            scale(0.92f)
                        })
                    })
                    hovered(Style {
                        animate(Style {
                            background(Color.LightBlue)
                            scale(1.04f)
                        })
                    })
                    focused(Style {
                        animate(Style {
                            borderWidth(3.dp)
                            borderColor(Color.White)
                        })
                    })
                }

                Box(
                    Modifier.styleable(
                        styleState = styleState,
                        style = style,
                    )
                )
            """,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun StateToggleRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
        )
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
