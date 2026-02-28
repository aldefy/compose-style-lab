package com.example.composestylelab.labs.state_driven_cards

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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.style.ExperimentalFoundationStyleApi
import androidx.compose.foundation.style.Style
import androidx.compose.foundation.style.checked
import androidx.compose.foundation.style.disabled
import androidx.compose.foundation.style.pressed
import androidx.compose.foundation.style.selected
import androidx.compose.foundation.style.styleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

private val AccentBlue = Color(0xFF3D5AFE)
private val AccentGreen = Color(0xFF00C853)
private val AccentOrange = Color(0xFFFF6D00)

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
fun StateDrivenCardsLab(onBack: () -> Unit) {
    LabScaffold(
        title = "State-Driven Cards",
        description = "Demonstrates selected, checked, and disabled state blocks. " +
            "The Styles API lets you declare visual states alongside the base style " +
            "in a single DSL block.",
        onBack = onBack,
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // ── Section 1: Selectable Cards ──────────────────────

        Text(
            text = "Selectable Cards",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Tap to select one (radio-style). Uses selected() state block.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        var selectedIndex by remember { mutableIntStateOf(0) }
        val options = listOf("Option A", "Option B", "Option C")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            options.forEachIndexed { index, label ->
                SelectableCard(
                    label = label,
                    isSelected = selectedIndex == index,
                    onSelect = { selectedIndex = index },
                    modifier = Modifier.weight(1f),
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ActiveStyleProperties(
            label = "Style { } — base properties",
            properties = listOf(
                StyleProperty("background", "#F5F5F5", Color(0xFFF5F5F5)),
                StyleProperty("shape", "RoundedCorner(12dp)"),
                StyleProperty("contentPadding", "16dp"),
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        ActiveStyleProperties(
            label = "selected() — active on \"${options[selectedIndex]}\"",
            properties = listOf(
                StyleProperty("background", "blue@15%", AccentBlue.copy(alpha = 0.15f)),
                StyleProperty("borderWidth", "2dp"),
                StyleProperty("borderColor", "#3D5AFE", AccentBlue),
            ),
            visible = true,
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ── Section 2: Toggleable Cards ──────────────────────

        Text(
            text = "Toggleable Cards",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Tap to toggle each card. Uses checked() state block.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        val toggleLabels = listOf("Notifications", "Dark Mode", "Auto-Save")
        val toggleStates = remember {
            List(toggleLabels.size) { mutableStateOf(false) }
        }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            toggleLabels.forEachIndexed { index, label ->
                ToggleableCard(
                    label = label,
                    isChecked = toggleStates[index].value,
                    onToggle = { toggleStates[index].value = !toggleStates[index].value },
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ActiveStyleProperties(
            label = "Style { } — base properties",
            properties = listOf(
                StyleProperty("background", "#F5F5F5", Color(0xFFF5F5F5)),
                StyleProperty("shape", "RoundedCorner(12dp)"),
                StyleProperty("contentPadding", "20×16dp"),
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        val anyChecked = toggleStates.any { it.value }
        ActiveStyleProperties(
            label = "checked() — active on toggled cards",
            properties = listOf(
                StyleProperty("background", "green@15%", AccentGreen.copy(alpha = 0.15f)),
                StyleProperty("borderWidth", "2dp"),
                StyleProperty("borderColor", "#00C853", AccentGreen),
            ),
            visible = anyChecked,
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ── Section 3: Disabled State ────────────────────────

        Text(
            text = "Disabled State",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Toggle the switch to enable/disable the card. Uses disabled() state block.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        var cardEnabled by remember { mutableStateOf(true) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { cardEnabled = !cardEnabled }
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = if (cardEnabled) "Card is enabled" else "Card is disabled",
                style = MaterialTheme.typography.bodyLarge,
            )
            Switch(checked = cardEnabled, onCheckedChange = { cardEnabled = it })
        }

        Spacer(modifier = Modifier.height(8.dp))

        DisabledDemoCard(enabled = cardEnabled)

        Spacer(modifier = Modifier.height(12.dp))

        ActiveStyleProperties(
            label = "Style { } — base properties",
            properties = listOf(
                StyleProperty("background", "orange@15%", AccentOrange.copy(alpha = 0.15f)),
                StyleProperty("shape", "RoundedCorner(12dp)"),
                StyleProperty("borderWidth", "2dp"),
                StyleProperty("borderColor", "#FF6D00", AccentOrange),
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        ActiveStyleProperties(
            label = "disabled() — overrides when card is disabled",
            properties = listOf(
                StyleProperty("background", "#E0E0E0", Color(0xFFE0E0E0)),
                StyleProperty("contentColor", "#9E9E9E", Color(0xFF9E9E9E)),
                StyleProperty("scale", "0.98f"),
                StyleProperty("borderColor", "#BDBDBD", Color(0xFFBDBDBD)),
            ),
            visible = !cardEnabled,
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ── Code Snippet ─────────────────────────────────────

        Text(
            text = "How It Works",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Each card uses Modifier.styleable() with state blocks " +
                "like selected(), checked(), and disabled(). The animate wrapper " +
                "inside each state block provides smooth transitions.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CodeSnippet(
            code = """
                val cardStyle = Style {
                    background(Color(0xFFF5F5F5))
                    shape(RoundedCornerShape(12.dp))
                    contentPadding(16.dp)
                    selected(Style {
                        animate(Style {
                            background(accentColor.copy(alpha = 0.15f))
                            borderWidth(2.dp)
                            borderColor(accentColor)
                        })
                    })
                }
                Box(
                    modifier = Modifier
                        .styleable(style = cardStyle)
                        .selectable(
                            selected = isSelected,
                            onClick = onSelect,
                            role = Role.RadioButton,
                        ),
                )

                // Checked state:
                checked(Style {
                    animate(Style {
                        background(Color(0xFF00C853).copy(0.15f))
                        borderWidth(2.dp)
                        borderColor(Color(0xFF00C853))
                    })
                })

                // Disabled state:
                disabled(Style {
                    background(Color(0xFFE0E0E0))
                    contentColor(Color(0xFF9E9E9E))
                    scale(0.98f)
                })
            """,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun SelectableCard(
    label: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cardStyle = Style {
        background(Color(0xFFF5F5F5))
        shape(RoundedCornerShape(12.dp))
        contentPadding(16.dp)
        selected(Style {
            animate(Style {
                background(AccentBlue.copy(alpha = 0.15f))
                borderWidth(2.dp)
                borderColor(AccentBlue)
            })
        })
        pressed(Style {
            animate(Style {
                scale(0.95f)
            })
        })
    }

    Box(
        modifier = modifier
            .styleable(style = cardStyle)
            .selectable(
                selected = isSelected,
                onClick = onSelect,
                role = Role.RadioButton,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) AccentBlue else Color(0xFF616161),
            )
            if (isSelected) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier.size(8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Selected",
                        tint = AccentBlue,
                        modifier = Modifier.size(8.dp),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun ToggleableCard(
    label: String,
    isChecked: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cardStyle = Style {
        background(Color(0xFFF5F5F5))
        shape(RoundedCornerShape(12.dp))
        contentPadding(horizontal = 20.dp, vertical = 16.dp)
        checked(Style {
            animate(Style {
                background(AccentGreen.copy(alpha = 0.15f))
                borderWidth(2.dp)
                borderColor(AccentGreen)
            })
        })
        pressed(Style {
            animate(Style {
                scale(0.97f)
            })
        })
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .styleable(style = cardStyle)
            .toggleable(
                value = isChecked,
                onValueChange = { onToggle() },
                role = Role.Checkbox,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isChecked) AccentGreen else Color(0xFF616161),
            )
            Box(
                modifier = Modifier
                    .size(24.dp),
                contentAlignment = Alignment.Center,
            ) {
                if (isChecked) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Checked",
                        tint = AccentGreen,
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun DisabledDemoCard(
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val cardStyle = Style {
        background(AccentOrange.copy(alpha = 0.15f))
        shape(RoundedCornerShape(12.dp))
        contentPadding(horizontal = 20.dp, vertical = 16.dp)
        borderWidth(2.dp)
        borderColor(AccentOrange)
        disabled(Style {
            background(Color(0xFFE0E0E0))
            contentColor(Color(0xFF9E9E9E))
            scale(0.98f)
            borderWidth(1.dp)
            borderColor(Color(0xFFBDBDBD))
        })
        pressed(Style {
            animate(Style {
                scale(0.95f)
            })
        })
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .styleable(style = cardStyle)
            .clickable(enabled = enabled) { },
    ) {
        Column {
            Text(
                text = "Action Card",
                style = MaterialTheme.typography.titleSmall,
                color = if (enabled) AccentOrange else Color(0xFF9E9E9E),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (enabled) "Tap to interact" else "This card is disabled",
                style = MaterialTheme.typography.bodySmall,
                color = if (enabled) Color(0xFF616161) else Color(0xFFBDBDBD),
            )
        }
    }
}
