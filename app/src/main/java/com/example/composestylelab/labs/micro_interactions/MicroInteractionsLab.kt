package com.example.composestylelab.labs.micro_interactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.style.ExperimentalFoundationStyleApi
import androidx.compose.foundation.style.MutableStyleState
import androidx.compose.foundation.style.Style
import androidx.compose.foundation.style.checked
import androidx.compose.foundation.style.styleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.composestylelab.components.ActiveStyleProperties
import com.example.composestylelab.components.CodeSnippet
import com.example.composestylelab.components.LabScaffold
import com.example.composestylelab.components.StyleProperty

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
fun MicroInteractionsLab(onBack: () -> Unit) {
    LabScaffold(
        title = "Micro-Interactions",
        description = "Real-world UI patterns powered by the Styles API. " +
            "Favorite buttons, nav bars, pill toggles, and notification badges \u2014 " +
            "all using checked() + animate() with MutableStyleState for smooth state transitions.",
        onBack = onBack,
    ) {
        var isFavorited by remember { mutableStateOf(false) }
        var selectedNavIndex by remember { mutableIntStateOf(0) }
        var isPillActive by remember { mutableStateOf(false) }
        var hasNotification by remember { mutableStateOf(true) }

        Spacer(modifier = Modifier.height(24.dp))

        // -- Section 1: Favorite Button -----------------------------------

        Text(
            text = "Favorite Button",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Tap to toggle. Scales up, changes color, and swaps icon on checked.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        FavoriteButton(
            isFavorited = isFavorited,
            onToggle = { isFavorited = it },
        )

        Spacer(modifier = Modifier.height(8.dp))

        ActiveStyleProperties(
            label = "BASE Style { }",
            properties = listOf(
                StyleProperty("background", "#F5F5F5", Color(0xFFF5F5F5)),
                StyleProperty("shape", "CircleShape"),
                StyleProperty("contentPadding", "16dp"),
                StyleProperty("contentColor", "gray", Color.Gray),
                StyleProperty("scale", "1.0f"),
            ),
        )

        Spacer(modifier = Modifier.height(4.dp))

        ActiveStyleProperties(
            label = "CHECKED \u2192 animate(Style { ... })",
            properties = listOf(
                StyleProperty("background", "#FFEBEE", Color(0xFFFFEBEE)),
                StyleProperty("contentColor", "#E53935", Color(0xFFE53935)),
                StyleProperty("scale", "1.2f"),
            ),
            visible = isFavorited,
        )

        Spacer(modifier = Modifier.height(32.dp))

        // -- Section 2: Nav Bar Items ------------------------------------

        Text(
            text = "Navigation Bar",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Tap each item. The selected item gets a colored background, " +
                "changes icon color, scales up, and shows a label.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        NavBar(
            selectedIndex = selectedNavIndex,
            onSelect = { selectedNavIndex = it },
        )

        Spacer(modifier = Modifier.height(8.dp))

        ActiveStyleProperties(
            label = "NAV ITEM \u2192 BASE Style { }",
            properties = listOf(
                StyleProperty("background", "transparent"),
                StyleProperty("contentPadding", "12\u00D78dp"),
                StyleProperty("contentColor", "onSurfaceVariant"),
                StyleProperty("scale", "1.0f"),
            ),
        )

        Spacer(modifier = Modifier.height(4.dp))

        ActiveStyleProperties(
            label = "SELECTED \u2192 checked(animate(Style { }))",
            properties = listOf(
                StyleProperty("background", "primaryContainer"),
                StyleProperty("contentColor", "primary"),
                StyleProperty("scale", "1.1f"),
                StyleProperty("shape", "RoundedCorner(16dp)"),
            ),
        )

        Spacer(modifier = Modifier.height(32.dp))

        // -- Section 3: Pill Toggle ----------------------------------------

        Text(
            text = "Pill Toggle",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Tap to toggle between Active/Inactive. Background, text weight, " +
                "and border animate between states.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        PillToggle(
            isActive = isPillActive,
            onToggle = { isPillActive = it },
        )

        Spacer(modifier = Modifier.height(8.dp))

        ActiveStyleProperties(
            label = "INACTIVE (BASE)",
            properties = listOf(
                StyleProperty("background", "#F0F0F0", Color(0xFFF0F0F0)),
                StyleProperty("shape", "Pill (CircleShape)"),
                StyleProperty("contentPadding", "24\u00D714dp"),
                StyleProperty("contentColor", "gray", Color.Gray),
                StyleProperty("borderWidth", "1dp"),
                StyleProperty("borderColor", "#E0E0E0", Color(0xFFE0E0E0)),
            ),
        )

        Spacer(modifier = Modifier.height(4.dp))

        ActiveStyleProperties(
            label = "ACTIVE \u2192 checked(animate(Style { }))",
            properties = listOf(
                StyleProperty("background", "#E8F5E9", Color(0xFFE8F5E9)),
                StyleProperty("contentColor", "#2E7D32", Color(0xFF2E7D32)),
                StyleProperty("borderColor", "#4CAF50", Color(0xFF4CAF50)),
                StyleProperty("borderWidth", "2dp"),
                StyleProperty("fontWeight", "Bold"),
            ),
            visible = isPillActive,
        )

        Spacer(modifier = Modifier.height(32.dp))

        // -- Section 4: Notification Badge --------------------------------

        Text(
            text = "Notification Badge",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Tap to dismiss. The bell container changes color and scales " +
                "when a notification is active.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        NotificationBell(
            hasNotification = hasNotification,
            onToggle = { hasNotification = it },
        )

        Spacer(modifier = Modifier.height(8.dp))

        ActiveStyleProperties(
            label = "BELL \u2192 BASE Style { }",
            properties = listOf(
                StyleProperty("background", "#F5F5F5", Color(0xFFF5F5F5)),
                StyleProperty("shape", "RoundedCorner(16dp)"),
                StyleProperty("contentPadding", "16dp"),
                StyleProperty("contentColor", "gray", Color.Gray),
            ),
        )

        Spacer(modifier = Modifier.height(4.dp))

        ActiveStyleProperties(
            label = "ACTIVE \u2192 checked(animate(Style { }))",
            properties = listOf(
                StyleProperty("background", "#FFF3E0", Color(0xFFFFF3E0)),
                StyleProperty("contentColor", "#FF6D00", Color(0xFFFF6D00)),
                StyleProperty("scale", "1.05f"),
            ),
            visible = hasNotification,
        )

        Spacer(modifier = Modifier.height(32.dp))

        // -- Code Snippet --------------------------------------------------

        Text(
            text = "How It Works",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Each micro-interaction uses MutableStyleState with isChecked " +
                "driven by component state. The Styles API handles all visual " +
                "changes \u2014 background, contentColor, scale, border \u2014 with " +
                "automatic animation via checked(Style { animate(Style { ... }) }).",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CodeSnippet(
            code = """
                // Drive StyleState explicitly
                val styleState = remember {
                    MutableStyleState(MutableInteractionSource())
                }
                styleState.isChecked = isFavorited

                val favoriteStyle = Style {
                    background(Color(0xFFF5F5F5))
                    shape(CircleShape)
                    contentPadding(16.dp)
                    contentColor(Color.Gray)
                    checked(Style {
                        animate(Style {
                            background(Color(0xFFFFEBEE))
                            contentColor(Color(0xFFE53935))
                            scale(1.2f)
                        })
                    })
                }

                Box(
                    Modifier
                        .styleable(
                            styleState = styleState,
                            style = favoriteStyle,
                        )
                        .clickable { isFavorited = !isFavorited }
                )
            """,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// -- Favorite Button --------------------------------------------------------

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun FavoriteButton(
    isFavorited: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val styleState = remember { MutableStyleState(MutableInteractionSource()) }
    styleState.isChecked = isFavorited

    val style = Style {
        background(Color(0xFFF5F5F5))
        shape(CircleShape)
        contentPadding(16.dp)
        contentColor(Color.Gray)
        checked(Style {
            animate(Style {
                background(Color(0xFFFFEBEE))
                contentColor(Color(0xFFE53935))
                scale(1.2f)
            })
        })
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .styleable(styleState = styleState, style = style)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) { onToggle(!isFavorited) },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = if (isFavorited) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = if (isFavorited) "Unfavorite" else "Favorite",
                modifier = Modifier.size(32.dp),
            )
        }
    }
}

// -- Navigation Bar ---------------------------------------------------------

private data class NavItem(
    val icon: ImageVector,
    val label: String,
)

private val navItems = listOf(
    NavItem(Icons.Filled.Home, "Home"),
    NavItem(Icons.Filled.Search, "Search"),
    NavItem(Icons.Filled.Notifications, "Alerts"),
    NavItem(Icons.Filled.Person, "Profile"),
)

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun NavBar(
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val primary = MaterialTheme.colorScheme.primary
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        navItems.forEachIndexed { index, item ->
            val isSelected = selectedIndex == index

            val styleState = remember { MutableStyleState(MutableInteractionSource()) }
            styleState.isChecked = isSelected

            val navStyle = Style {
                background(Color.Transparent)
                contentPadding(horizontal = 12.dp, vertical = 8.dp)
                contentColor(onSurfaceVariant)
                shape(RoundedCornerShape(16.dp))
                checked(Style {
                    animate(Style {
                        background(primaryContainer)
                        contentColor(primary)
                        scale(1.1f)
                    })
                })
            }

            Box(
                modifier = Modifier
                    .styleable(styleState = styleState, style = navStyle)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) { onSelect(index) },
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp),
                    )
                    if (isSelected) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            }
        }
    }
}

// -- Pill Toggle -----------------------------------------------------------

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun PillToggle(
    isActive: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val styleState = remember { MutableStyleState(MutableInteractionSource()) }
    styleState.isChecked = isActive

    val pillStyle = Style {
        background(Color(0xFFF0F0F0))
        shape(CircleShape)
        contentPadding(horizontal = 24.dp, vertical = 14.dp)
        contentColor(Color.Gray)
        borderWidth(1.dp)
        borderColor(Color(0xFFE0E0E0))
        checked(Style {
            animate(Style {
                background(Color(0xFFE8F5E9))
                contentColor(Color(0xFF2E7D32))
                borderColor(Color(0xFF4CAF50))
                borderWidth(2.dp)
                fontWeight(FontWeight.Bold)
            })
        })
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
    ) {
        Box(
            modifier = Modifier
                .styleable(styleState = styleState, style = pillStyle)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) { onToggle(!isActive) },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = if (isActive) "Active" else "Inactive",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

// -- Notification Bell ------------------------------------------------------

@OptIn(ExperimentalFoundationStyleApi::class)
@Composable
private fun NotificationBell(
    hasNotification: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val styleState = remember { MutableStyleState(MutableInteractionSource()) }
    styleState.isChecked = hasNotification

    val bellStyle = Style {
        background(Color(0xFFF5F5F5))
        shape(RoundedCornerShape(16.dp))
        contentPadding(16.dp)
        contentColor(Color.Gray)
        checked(Style {
            animate(Style {
                background(Color(0xFFFFF3E0))
                contentColor(Color(0xFFFF6D00))
                scale(1.05f)
            })
        })
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .styleable(styleState = styleState, style = bellStyle)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) { onToggle(!hasNotification) },
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Notifications",
                    modifier = Modifier.size(28.dp),
                )
                Column {
                    Text(
                        text = if (hasNotification) "1 new notification" else "No notifications",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = "Tap to ${if (hasNotification) "dismiss" else "restore"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}
