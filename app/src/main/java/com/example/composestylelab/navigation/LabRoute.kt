package com.example.composestylelab.navigation

import androidx.compose.ui.graphics.Color
import com.example.composestylelab.theme.*

enum class LabRoute(
    val title: String,
    val subtitle: String,
    val accentColor: Color,
) {
    InteractiveButtons(
        title = "Interactive Buttons",
        subtitle = "pressed, hovered, focused states",
        accentColor = LabBlue,
    ),
    StyleComposition(
        title = "Style Composition",
        subtitle = "layering styles with then",
        accentColor = LabCyan,
    ),
    StateDrivenCards(
        title = "State-Driven Cards",
        subtitle = "selected, checked, enabled",
        accentColor = LabGreen,
    ),
    AnimatedTransforms(
        title = "Animated Transforms",
        subtitle = "scale, rotation, translation",
        accentColor = LabOrange,
    ),
    ShadowPlay(
        title = "Shadow Play",
        subtitle = "dropShadow, innerShadow",
        accentColor = LabPink,
    ),
    TextStyling(
        title = "Text Styling",
        subtitle = "fontSize, fontWeight, contentBrush",
        accentColor = LabPurple,
    ),
    ThemeIntegration(
        title = "Theme Integration",
        subtitle = "CompositionLocal in styles",
        accentColor = LabYellow,
    ),
    CustomComponents(
        title = "Custom Components",
        subtitle = "style as a component parameter",
        accentColor = LabTeal,
    ),
}
