package com.example.composestylelab.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composestylelab.home.HomeScreen
import com.example.composestylelab.labs.interactive_buttons.InteractiveButtonsLab
import com.example.composestylelab.labs.style_composition.StyleCompositionLab

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

        LabRoute.entries.filter { it != LabRoute.InteractiveButtons && it != LabRoute.StyleComposition }.forEach { lab ->
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
