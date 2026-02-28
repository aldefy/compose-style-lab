package com.example.composestylelab.labs.interactive_buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.style.ExperimentalFoundationStyleApi
import androidx.compose.foundation.style.Style
import androidx.compose.foundation.style.focused
import androidx.compose.foundation.style.hovered
import androidx.compose.foundation.style.pressed
import androidx.compose.foundation.style.styleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composestylelab.components.CodeSnippet
import com.example.composestylelab.components.LabScaffold

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
fun InteractiveButtonsLab(onBack: () -> Unit) {
    LabScaffold(
        title = "Interactive Buttons",
        description = "Demonstrates pressed, hovered, and focused state blocks with animate. " +
            "The Styles API replaces ~20 lines of InteractionSource boilerplate with a " +
            "declarative DSL.",
        onBack = onBack,
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Styled Buttons",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            StyledButton(
                label = "Press Me",
                baseColor = Color(0xFF2196F3),
                modifier = Modifier.weight(1f),
            )
            StyledButton(
                label = "Hover Me",
                baseColor = Color(0xFF00BCD4),
                modifier = Modifier.weight(1f),
            )
            StyledButton(
                label = "Focus Me",
                baseColor = Color(0xFF4CAF50),
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "How It Works",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Each button uses Modifier.styleable() with a Style block that declares " +
                "interactive states. The pressed, hovered, and focused functions each wrap " +
                "an animate call for smooth transitions.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CodeSnippet(
            code = """
                Box(
                    modifier = Modifier
                        .styleable(
                            style = Style {
                                background(baseColor)
                                shape(RoundedCornerShape(12.dp))
                                contentPadding(
                                    horizontal = 24.dp,
                                    vertical = 12.dp,
                                )
                                pressed(Style {
                                    animate(Style {
                                        background(darker)
                                        scale(0.95f)
                                    })
                                })
                                hovered(Style {
                                    animate(Style {
                                        background(lighter)
                                        scale(1.05f)
                                    })
                                })
                                focused(Style {
                                    animate(Style {
                                        borderWidth(2.dp)
                                        borderColor(Color.White)
                                    })
                                })
                            }
                        )
                        .clickable { },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(label, color = Color.White)
                }
            """,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun StyledButton(
    label: String,
    baseColor: Color,
    modifier: Modifier = Modifier,
) {
    val darkerColor = baseColor.copy(alpha = 0.7f)
    val lighterColor = baseColor.copy(alpha = 0.85f)

    Box(
        modifier = modifier
            .styleable(
                style = Style {
                    background(baseColor)
                    shape(RoundedCornerShape(12.dp))
                    contentPadding(horizontal = 24.dp, vertical = 12.dp)
                    pressed(Style {
                        animate(Style {
                            background(darkerColor)
                            scale(0.95f)
                        })
                    })
                    hovered(Style {
                        animate(Style {
                            background(lighterColor)
                            scale(1.05f)
                        })
                    })
                    focused(Style {
                        animate(Style {
                            borderWidth(2.dp)
                            borderColor(Color.White)
                        })
                    })
                },
            )
            .clickable { },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}
