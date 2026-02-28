package com.example.composestylelab.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composestylelab.home.HomeScreen
import com.example.composestylelab.labs.interactive_buttons.InteractiveButtonsLab
import com.example.composestylelab.labs.state_driven_cards.StateDrivenCardsLab
import com.example.composestylelab.labs.animated_transforms.AnimatedTransformsLab
import com.example.composestylelab.labs.shadow_play.ShadowPlayLab
import com.example.composestylelab.labs.style_composition.StyleCompositionLab
import com.example.composestylelab.labs.text_styling.TextStylingLab

@Composable
fun StyleLabNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier,
    ) {
        composable("home") {
            HomeScreen(
                onLabClick = { lab ->
                    navController.navigate(lab.name)
                },
            )
        }

        composable(LabRoute.InteractiveButtons.name) {
            InteractiveButtonsLab(onBack = { navController.popBackStack() })
        }

        composable(LabRoute.StyleComposition.name) {
            StyleCompositionLab(onBack = { navController.popBackStack() })
        }

        composable(LabRoute.StateDrivenCards.name) {
            StateDrivenCardsLab(onBack = { navController.popBackStack() })
        }

        composable(LabRoute.AnimatedTransforms.name) {
            AnimatedTransformsLab(onBack = { navController.popBackStack() })
        }

        composable(LabRoute.ShadowPlay.name) {
            ShadowPlayLab(onBack = { navController.popBackStack() })
        }

        composable(LabRoute.TextStyling.name) {
            TextStylingLab(onBack = { navController.popBackStack() })
        }

        LabRoute.entries.filter {
            it != LabRoute.InteractiveButtons &&
                it != LabRoute.StyleComposition &&
                it != LabRoute.StateDrivenCards &&
                it != LabRoute.AnimatedTransforms &&
                it != LabRoute.ShadowPlay &&
                it != LabRoute.TextStyling
        }.forEach { lab ->
            composable(lab.name) {
                PlaceholderLabScreen(lab = lab, onBack = { navController.popBackStack() })
            }
        }
    }
}

@Composable
private fun PlaceholderLabScreen(lab: LabRoute, onBack: () -> Unit) {
    com.example.composestylelab.components.LabScaffold(
        title = lab.title,
        description = lab.subtitle,
        onBack = onBack,
    ) {
        androidx.compose.material3.Text("Coming soon...")
    }
}
