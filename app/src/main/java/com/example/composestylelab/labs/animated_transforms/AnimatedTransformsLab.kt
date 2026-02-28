package com.example.composestylelab.labs.animated_transforms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.composestylelab.components.CodeSnippet
import com.example.composestylelab.components.LabScaffold

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
fun AnimatedTransformsLab(onBack: () -> Unit) {
    LabScaffold(
        title = "Animated Transforms",
        description = "Demonstrates scale(), rotationZ(), and translationX/Y() inside " +
            "animate blocks. Transform properties let you create rich motion feedback " +
            "with minimal code.",
        onBack = onBack,
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // -- Section 1: Bouncy Press Button ----------------------------------

        Text(
            text = "Bouncy Press",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Tap and hold to see the scale-down spring effect.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        BouncyPressButton()

        Spacer(modifier = Modifier.height(32.dp))

        // -- Section 2: Spin on Select --------------------------------------

        Text(
            text = "Spin on Select",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Tap to toggle. The card rotates 360\u00B0 and changes color when checked.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        SpinOnSelectCard()

        Spacer(modifier = Modifier.height(32.dp))

        // -- Section 3: Translation Slide ------------------------------------

        Text(
            text = "Translation Slide",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Tap to toggle. The card slides right and up when checked.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        TranslationSlideCard()

        Spacer(modifier = Modifier.height(32.dp))

        // -- Code Snippet ----------------------------------------------------

        Text(
            text = "How It Works",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Transform properties like scale(), rotationZ(), and translationX/Y() " +
                "live inside animate blocks within state functions. The Styles API handles " +
                "interpolation automatically -- no AnimationSpec boilerplate needed.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CodeSnippet(
            code = """
                // Bouncy press with scale
                val bouncyStyle = Style {
                    background(Color(0xFFFF6D00))
                    shape(RoundedCornerShape(16.dp))
                    contentPadding(24.dp)
                    pressed(Style {
                        animate(Style { scale(0.85f) })
                    })
                }

                // Spin on select with rotation + color
                val spinStyle = Style {
                    background(Color(0xFF3D5AFE))
                    shape(RoundedCornerShape(16.dp))
                    contentPadding(20.dp)
                    checked(Style {
                        animate(Style {
                            rotationZ(360f)
                            background(Color(0xFF00C853))
                        })
                    })
                }

                // Translation slide
                val slideStyle = Style {
                    background(Color(0xFF00BCD4))
                    shape(RoundedCornerShape(16.dp))
                    contentPadding(20.dp)
                    checked(Style {
                        animate(Style {
                            translationX(50f)
                            translationY(-10f)
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
private fun BouncyPressButton(modifier: Modifier = Modifier) {
    val bouncyStyle = Style {
        background(Color(0xFFFF6D00))
        shape(RoundedCornerShape(16.dp))
        contentPadding(24.dp)
        pressed(Style {
            animate(Style {
                scale(0.85f)
            })
        })
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .styleable(style = bouncyStyle)
            .clickable { },
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Press & Hold",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
            )
            Text(
                text = "Scales to 0.85x on press",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.8f),
            )
        }
    }
}

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun SpinOnSelectCard(modifier: Modifier = Modifier) {
    var isChecked by remember { mutableStateOf(false) }

    val spinStyle = Style {
        background(Color(0xFF3D5AFE))
        shape(RoundedCornerShape(16.dp))
        contentPadding(20.dp)
        checked(Style {
            animate(Style {
                rotationZ(360f)
                background(Color(0xFF00C853))
            })
        })
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .styleable(style = spinStyle)
            .toggleable(
                value = isChecked,
                onValueChange = { isChecked = it },
                role = Role.Checkbox,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = if (isChecked) "Spinning!" else "Tap to Spin",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                )
                Text(
                    text = "rotationZ(360f) + color change",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f),
                )
            }
            if (isChecked) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Checked",
                    tint = Color.White,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun TranslationSlideCard(modifier: Modifier = Modifier) {
    var isChecked by remember { mutableStateOf(false) }

    val slideStyle = Style {
        background(Color(0xFF00BCD4))
        shape(RoundedCornerShape(16.dp))
        contentPadding(20.dp)
        checked(Style {
            animate(Style {
                translationX(50f)
                translationY(-10f)
            })
        })
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .styleable(style = slideStyle)
            .toggleable(
                value = isChecked,
                onValueChange = { isChecked = it },
                role = Role.Checkbox,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = if (isChecked) "Slid!" else "Tap to Slide",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                )
                Text(
                    text = "translationX(50f), translationY(-10f)",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f),
                )
            }
            if (isChecked) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Checked",
                    tint = Color.White,
                )
            }
        }
    }
}
