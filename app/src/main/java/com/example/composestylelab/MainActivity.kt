package com.example.composestylelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.composestylelab.navigation.StyleLabNavigation
import com.example.composestylelab.theme.ComposeStyleLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeStyleLabTheme {
                StyleLabNavigation()
            }
        }
    }
}
