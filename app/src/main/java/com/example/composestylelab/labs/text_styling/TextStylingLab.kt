package com.example.composestylelab.labs.text_styling

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.style.ExperimentalFoundationStyleApi
import androidx.compose.foundation.style.Style
import androidx.compose.foundation.style.checked
import androidx.compose.foundation.style.pressed
import androidx.compose.foundation.style.styleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composestylelab.components.CodeSnippet
import com.example.composestylelab.components.LabScaffold

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
fun TextStylingLab(onBack: () -> Unit) {
    LabScaffold(
        title = "Text Styling",
        description = "Demonstrates text-specific style properties: fontSize, fontWeight, " +
            "contentColor, contentBrush, letterSpacing, and textDecoration. " +
            "These properties animate automatically inside state blocks.",
        onBack = onBack,
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // -- Section 1: Weight Shift ------------------------------------------

        Text(
            text = "Weight Shift",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Tap to toggle. Font weight, size, and color animate on checked state.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        WeightShiftDemo()

        Spacer(modifier = Modifier.height(32.dp))

        // -- Section 2: Gradient Text -----------------------------------------

        Text(
            text = "Gradient Text",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Text rendered with a gradient brush via contentBrush().",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        GradientTextDemo()

        Spacer(modifier = Modifier.height(32.dp))

        // -- Section 3: Press-Reactive Text -----------------------------------

        Text(
            text = "Press-Reactive Text",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Press and hold. Letter spacing widens, color changes, " +
                "and underline decoration appears.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        PressReactiveTextDemo()

        Spacer(modifier = Modifier.height(32.dp))

        // -- Code Snippet -----------------------------------------------------

        Text(
            text = "How It Works",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Text properties like fontWeight(), fontSize(), contentColor(), " +
                "contentBrush(), letterSpacing(), and textDecoration() live directly " +
                "in the StyleScope. Wrap them in checked/pressed + animate blocks " +
                "for automatic interpolation.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CodeSnippet(
            code = """
                // Weight shift on toggle
                val weightStyle = Style {
                    contentColor(Color.DarkGray)
                    fontWeight(FontWeight.Normal)
                    fontSize(20.sp)
                    checked(Style {
                        animate(Style {
                            fontWeight(FontWeight.Bold)
                            contentColor(Color(0xFF3D5AFE))
                            fontSize(24.sp)
                        })
                    })
                }

                // Gradient brush text
                val gradientStyle = Style {
                    contentBrush(
                        Brush.linearGradient(
                            listOf(Color.Magenta, Color.Cyan)
                        )
                    )
                    fontSize(28.sp)
                    fontWeight(FontWeight.Bold)
                }

                // Press-reactive letter spacing
                val pressTextStyle = Style {
                    contentColor(Color.Black)
                    fontSize(18.sp)
                    letterSpacing(0.sp)
                    pressed(Style {
                        animate(Style {
                            contentColor(Color(0xFFFF6D00))
                            letterSpacing(4.sp)
                            textDecoration(TextDecoration.Underline)
                        })
                    })
                }
            """,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun WeightShiftDemo(modifier: Modifier = Modifier) {
    var isChecked by remember { mutableStateOf(false) }

    val weightStyle = Style {
        contentColor(Color.DarkGray)
        fontWeight(FontWeight.Normal)
        fontSize(20.sp)
        background(Color(0xFFF5F5F5))
        shape(RoundedCornerShape(16.dp))
        contentPadding(24.dp)
        checked(Style {
            animate(Style {
                fontWeight(FontWeight.Bold)
                contentColor(Color(0xFF3D5AFE))
                fontSize(24.sp)
                background(Color(0xFFE8EAF6))
            })
        })
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .styleable(style = weightStyle)
            .toggleable(
                value = isChecked,
                onValueChange = { isChecked = it },
                role = Role.Checkbox,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isChecked) "Bold & Blue" else "Tap to Shift Weight",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "fontWeight + fontSize + contentColor",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            if (isChecked) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Checked",
                    tint = Color(0xFF3D5AFE),
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun GradientTextDemo(modifier: Modifier = Modifier) {
    val gradientStyle = Style {
        contentBrush(
            Brush.linearGradient(listOf(Color.Magenta, Color.Cyan)),
        )
        fontSize(28.sp)
        fontWeight(FontWeight.Bold)
        background(Color(0xFFFAFAFA))
        shape(RoundedCornerShape(16.dp))
        contentPadding(24.dp)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .styleable(style = gradientStyle),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Gradient Styled Text",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "contentBrush(Brush.linearGradient(...))",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun PressReactiveTextDemo(modifier: Modifier = Modifier) {
    val pressTextStyle = Style {
        contentColor(Color.Black)
        fontSize(18.sp)
        letterSpacing(0.sp)
        background(Color(0xFFFFF3E0))
        shape(RoundedCornerShape(16.dp))
        contentPadding(24.dp)
        pressed(Style {
            animate(Style {
                contentColor(Color(0xFFFF6D00))
                letterSpacing(4.sp)
                textDecoration(TextDecoration.Underline)
                background(Color(0xFFFFE0B2))
            })
        })
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .styleable(style = pressTextStyle)
            .clickable { },
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Press & Hold Me",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "letterSpacing + textDecoration + contentColor",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
