package com.example.composestylelab.labs.text_styling

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.style.ExperimentalFoundationStyleApi
import androidx.compose.foundation.style.MutableStyleState
import androidx.compose.foundation.style.Style
import androidx.compose.foundation.style.checked
import androidx.compose.foundation.style.pressed
import androidx.compose.foundation.style.styleable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composestylelab.components.ActiveStyleProperties
import com.example.composestylelab.components.CodeSnippet
import com.example.composestylelab.components.LabScaffold
import com.example.composestylelab.components.StyleProperty

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
        var isWeightChecked by remember { mutableStateOf(false) }

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

        WeightShiftDemo(
            isChecked = isWeightChecked,
            onToggle = { isWeightChecked = it },
        )

        Spacer(modifier = Modifier.height(8.dp))

        ActiveStyleProperties(
            label = "Base Style",
            properties = listOf(
                StyleProperty("contentColor", "DarkGray", Color.DarkGray),
                StyleProperty("fontWeight", "Normal"),
                StyleProperty("fontSize", "20sp"),
                StyleProperty("background", "#F5F5F5", Color(0xFFF5F5F5)),
                StyleProperty("shape", "RoundedCorner(16dp)"),
            ),
        )

        Spacer(modifier = Modifier.height(6.dp))

        ActiveStyleProperties(
            label = "Checked State",
            properties = listOf(
                StyleProperty("fontWeight", "Bold"),
                StyleProperty("contentColor", "#3D5AFE", Color(0xFF3D5AFE)),
                StyleProperty("fontSize", "24sp"),
                StyleProperty("background", "#E8EAF6", Color(0xFFE8EAF6)),
            ),
            visible = isWeightChecked,
        )

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

        Spacer(modifier = Modifier.height(8.dp))

        ActiveStyleProperties(
            label = "Gradient Style",
            properties = listOf(
                StyleProperty("contentBrush", "linearGradient(Magenta→Cyan)"),
                StyleProperty("fontSize", "28sp"),
                StyleProperty("fontWeight", "Bold"),
                StyleProperty("background", "#FAFAFA", Color(0xFFFAFAFA)),
            ),
        )

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

        Spacer(modifier = Modifier.height(8.dp))

        ActiveStyleProperties(
            label = "Base Style",
            properties = listOf(
                StyleProperty("contentColor", "black", Color.Black),
                StyleProperty("fontSize", "18sp"),
                StyleProperty("letterSpacing", "0sp"),
                StyleProperty("background", "#FFF3E0", Color(0xFFFFF3E0)),
            ),
        )

        Spacer(modifier = Modifier.height(6.dp))

        ActiveStyleProperties(
            label = "Pressed State (press & hold)",
            properties = listOf(
                StyleProperty("contentColor", "#FF6D00", Color(0xFFFF6D00)),
                StyleProperty("letterSpacing", "4sp"),
                StyleProperty("textDecoration", "Underline"),
                StyleProperty("scale", "0.96f"),
                StyleProperty("background", "#FFE0B2", Color(0xFFFFE0B2)),
            ),
        )

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
private fun WeightShiftDemo(
    isChecked: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val styleState = remember { MutableStyleState(MutableInteractionSource()) }
    styleState.isChecked = isChecked

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
            .styleable(styleState = styleState, style = weightStyle)
            .clickable { onToggle(!isChecked) },
        contentAlignment = Alignment.Center,
    ) {
        Text(text = if (isChecked) "Bold & Blue" else "Tap to Shift Weight")
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
        Text(text = "Gradient Styled Text")
    }
}

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun PressReactiveTextDemo(modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val styleState = remember { MutableStyleState(interactionSource) }

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
                scale(0.96f)
            })
        })
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .styleable(styleState = styleState, style = pressTextStyle)
            .clickable(interactionSource = interactionSource, indication = null) { },
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Press & Hold Me")
    }
}
