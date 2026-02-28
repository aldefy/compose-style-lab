# Compose Style Lab — Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Build the first public demo app showcasing the new experimental Compose Styles API (`@ExperimentalFoundationStyleApi` from `compose-foundation:1.11.0-alpha06`), organized as an interactive cookbook of 8 lab screens.

**Architecture:** Single-activity Android app with Navigation Compose. Home screen grid navigates to 8 lab screens. Each lab demonstrates one Styles API facet with a live interactive demo and code snippet. All components follow official Compose API Guidelines (modifier contract, slot APIs, defaults objects).

**Tech Stack:** Kotlin 2.1.0, AGP 8.9.0, Compose BOM 2026.02.01 (override foundation to 1.11.0-alpha06), Material3, Navigation Compose, JUnit + Compose Testing

---

### Task 1: Scaffold the Android project

**Files:**
- Create: `settings.gradle.kts`
- Create: `build.gradle.kts` (root)
- Create: `app/build.gradle.kts`
- Create: `gradle/libs.versions.toml`
- Create: `gradle.properties`
- Create: `app/src/main/AndroidManifest.xml`
- Create: `app/src/main/java/com/example/composestylelab/MainActivity.kt`
- Create: `app/src/main/res/values/strings.xml`
- Create: `app/src/main/res/values/themes.xml`

**Step 1: Create gradle version catalog**

```toml
# gradle/libs.versions.toml
[versions]
kotlin = "2.1.0"
agp = "8.9.0"
compose-bom = "2026.02.01"
compose-foundation-alpha = "1.11.0-alpha06"
activity-compose = "1.10.1"
navigation-compose = "2.8.9"

[libraries]
compose-bom = { module = "androidx.compose:compose-bom", version.ref = "compose-bom" }
compose-foundation = { module = "androidx.compose.foundation:foundation", version.ref = "compose-foundation-alpha" }
compose-material3 = { module = "androidx.compose.material3:material3" }
compose-ui = { module = "androidx.compose.ui:ui" }
compose-ui-tooling = { module = "androidx.compose.ui:ui-tooling" }
compose-ui-tooling-preview = { module = "androidx.compose.ui:ui-tooling-preview" }
compose-material-icons = { module = "androidx.compose.material:material-icons-extended" }
activity-compose = { module = "androidx.activity:activity-compose", version.ref = "activity-compose" }
navigation-compose = { module = "androidx.navigation:navigation-compose", version.ref = "navigation-compose" }

# Testing
compose-ui-test = { module = "androidx.compose.ui:ui-test-junit4" }
compose-ui-test-manifest = { module = "androidx.compose.ui:ui-test-manifest" }
junit = { module = "junit:junit", version = "4.13.2" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
compose-compiler = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
```

**Step 2: Create root build.gradle.kts**

```kotlin
// build.gradle.kts (root)
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
}
```

**Step 3: Create app/build.gradle.kts**

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.composestylelab"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.composestylelab"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.foundation) // alpha override for Styles API
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material.icons)
    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test)
}
```

**Step 4: Create settings.gradle.kts**

```kotlin
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolution {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ComposeStyleLab"
include(":app")
```

**Step 5: Create gradle.properties**

```properties
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
android.useAndroidX=true
kotlin.code.style=official
android.nonTransitiveRClass=true
```

**Step 6: Create AndroidManifest.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application
        android:allowBackup="true"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/Theme.ComposeStyleLab">
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:theme="@style/Theme.ComposeStyleLab">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

**Step 7: Create strings.xml and themes.xml**

```xml
<!-- app/src/main/res/values/strings.xml -->
<resources>
    <string name="app_name">Compose Style Lab</string>
</resources>
```

```xml
<!-- app/src/main/res/values/themes.xml -->
<resources>
    <style name="Theme.ComposeStyleLab" parent="android:Theme.Material.Light.NoActionBar" />
</resources>
```

**Step 8: Create minimal MainActivity.kt**

```kotlin
package com.example.composestylelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import com.example.composestylelab.theme.ComposeStyleLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeStyleLabTheme {
                Text("Compose Style Lab")
            }
        }
    }
}
```

**Step 9: Sync and verify build**

Run: `cd /Users/adit/Documents/AndroidProjects/ComposeStylingApiDemo && ./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

**Step 10: Initialize git and commit**

```bash
git init
echo "*.iml\n.gradle\n/local.properties\n/.idea\n/build\n/app/build\n/captures\n.externalNativeBuild\n.cxx\nlocal.properties" > .gitignore
git add -A
git commit -m "chore: scaffold Android project with Compose Styles API alpha dependency"
```

---

### Task 2: Theme and shared components

**Files:**
- Create: `app/src/main/java/com/example/composestylelab/theme/Theme.kt`
- Create: `app/src/main/java/com/example/composestylelab/theme/Color.kt`
- Create: `app/src/main/java/com/example/composestylelab/components/LabScaffold.kt`
- Create: `app/src/main/java/com/example/composestylelab/components/CodeSnippet.kt`

**Step 1: Create Color.kt**

```kotlin
package com.example.composestylelab.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)
val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Lab accent colors
val LabBlue = Color(0xFF3D5AFE)
val LabCyan = Color(0xFF00BCD4)
val LabGreen = Color(0xFF00C853)
val LabOrange = Color(0xFFFF6D00)
val LabPink = Color(0xFFFF1744)
val LabPurple = Color(0xFFAA00FF)
val LabYellow = Color(0xFFFFD600)
val LabTeal = Color(0xFF1DE9B6)
```

**Step 2: Create Theme.kt**

```kotlin
package com.example.composestylelab.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
)

@Composable
fun ComposeStyleLabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
```

**Step 3: Create LabScaffold.kt**

A shared scaffold wrapper for every lab screen. Follows API guidelines: `modifier` as first optional param, `content` slot.

```kotlin
package com.example.composestylelab.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabScaffold(
    title: String,
    description: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        contentWindowInsets = WindowInsets(0.dp),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            content()
        }
    }
}
```

**Step 4: Create CodeSnippet.kt**

```kotlin
package com.example.composestylelab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CodeSnippet(
    code: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = code.trimIndent(),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .horizontalScroll(rememberScrollState())
            .padding(16.dp),
        fontFamily = FontFamily.Monospace,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        color = MaterialTheme.colorScheme.onSurface,
    )
}
```

**Step 5: Verify build**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

**Step 6: Commit**

```bash
git add -A
git commit -m "feat: add theme, LabScaffold, and CodeSnippet shared components"
```

---

### Task 3: Navigation and Home Screen

**Files:**
- Create: `app/src/main/java/com/example/composestylelab/navigation/StyleLabNavigation.kt`
- Create: `app/src/main/java/com/example/composestylelab/navigation/LabRoute.kt`
- Create: `app/src/main/java/com/example/composestylelab/home/HomeScreen.kt`
- Create: `app/src/main/java/com/example/composestylelab/components/LabCard.kt`
- Modify: `app/src/main/java/com/example/composestylelab/MainActivity.kt`

**Step 1: Create LabRoute.kt — route enum for all labs**

```kotlin
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
```

**Step 2: Create LabCard.kt**

This card itself uses the Styles API so the user experiences it immediately on the home screen. Note: Since the Styles API is experimental and the exact surface may differ at runtime, we'll build this with a fallback approach — use the Styles API where available, with standard modifiers as backup.

```kotlin
package com.example.composestylelab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composestylelab.navigation.LabRoute

@Composable
fun LabCard(
    lab: LabRoute,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(lab.accentColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = lab.ordinal.plus(1).toString(),
                style = MaterialTheme.typography.headlineLarge,
                color = lab.accentColor,
            )
        }
        Text(
            text = lab.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 12.dp),
        )
        Text(
            text = lab.subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}
```

**Step 3: Create HomeScreen.kt**

```kotlin
package com.example.composestylelab.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composestylelab.components.LabCard
import com.example.composestylelab.navigation.LabRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLabClick: (LabRoute) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Compose Style Lab",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
            )
        },
        contentWindowInsets = WindowInsets(0.dp),
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalItemSpacing = 12.dp,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(LabRoute.entries) { lab ->
                LabCard(
                    lab = lab,
                    onClick = { onLabClick(lab) },
                )
            }
        }
    }
}
```

**Step 4: Create StyleLabNavigation.kt**

```kotlin
package com.example.composestylelab.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composestylelab.home.HomeScreen

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

        // Lab routes — placeholder screens for now, replaced in subsequent tasks
        LabRoute.entries.forEach { lab ->
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
```

**Step 5: Update MainActivity.kt**

```kotlin
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
```

**Step 6: Build and verify**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

**Step 7: Commit**

```bash
git add -A
git commit -m "feat: add navigation, home screen grid, and lab card components"
```

---

### Task 4: Lab 1 — Interactive Buttons

**Files:**
- Create: `app/src/main/java/com/example/composestylelab/labs/interactive_buttons/InteractiveButtonsLab.kt`
- Modify: `app/src/main/java/com/example/composestylelab/navigation/StyleLabNavigation.kt` (replace placeholder route)

**Step 1: Create InteractiveButtonsLab.kt**

This lab shows `pressed`, `hovered`, `focused` state blocks with `animate`. Since the Styles API is brand-new and experimental, we need to handle the API surface carefully. The core pattern:

```kotlin
package com.example.composestylelab.labs.interactive_buttons

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composestylelab.components.CodeSnippet
import com.example.composestylelab.components.LabScaffold

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InteractiveButtonsLab(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LabScaffold(
        title = "Interactive Buttons",
        description = "The Styles API replaces ~20 lines of InteractionSource boilerplate with declarative state blocks. Press, hover, and focus — all in one style definition.",
        onBack = onBack,
        modifier = modifier,
    ) {
        Spacer(Modifier.height(24.dp))

        Text(
            text = "Tap the buttons below",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(16.dp))

        // Demo: Styled buttons using the Styles API
        // NOTE: The actual Styles API calls (Style { }, pressed { }, animate { })
        // will be wired when we verify the exact API surface compiles against 1.11.0-alpha06.
        // For now, we build the demo layout and code snippet.

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            StyledButton(
                label = "Press Me",
                baseColor = Color(0xFF3D5AFE),
                modifier = Modifier.weight(1f),
            )
            StyledButton(
                label = "Hover Me",
                baseColor = Color(0xFF00BCD4),
                modifier = Modifier.weight(1f),
            )
            StyledButton(
                label = "Focus Me",
                baseColor = Color(0xFF00C853),
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Code",
            style = MaterialTheme.typography.titleSmall,
        )

        Spacer(Modifier.height(8.dp))

        CodeSnippet(
            code = """
                style = {
                    background(Color.Blue)
                    shape(RoundedCornerShape(12.dp))
                    contentPadding(16.dp)

                    pressed {
                        animate {
                            background(Color.Blue.copy(alpha = 0.7f))
                            scale(0.95f)
                        }
                    }

                    hovered {
                        animate {
                            background(Color.Blue.copy(alpha = 0.85f))
                            scale(1.05f)
                        }
                    }
                }
            """,
        )
    }
}
```

**Step 2: Create StyledButton composable in same file**

This is the actual button using the Styles API. Since this API is bleeding-edge, we'll attempt to use `Modifier.styleable` / the Style lambda. If the exact API surface differs at compile time, we adapt.

```kotlin
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StyledButton(
    label: String,
    baseColor: Color,
    modifier: Modifier = Modifier,
) {
    // This will use the experimental Styles API.
    // The pattern is: ClickableStyleableBox or Box with Modifier.styleable()
    // Exact usage depends on what 1.11.0-alpha06 exposes.
    // Fallback: use standard clickable + animateColorAsState for identical visual.

    androidx.compose.foundation.layout.Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(baseColor)
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
```

**Step 3: Wire into navigation**

In `StyleLabNavigation.kt`, replace the `InteractiveButtons` placeholder:

```kotlin
composable(LabRoute.InteractiveButtons.name) {
    InteractiveButtonsLab(onBack = { navController.popBackStack() })
}
```

**Step 4: Build and verify**

Run: `./gradlew assembleDebug`

**Step 5: Commit**

```bash
git add -A
git commit -m "feat: add Lab 1 — Interactive Buttons with Styles API demo"
```

---

### Task 5: Lab 2 — Style Composition

**Files:**
- Create: `app/src/main/java/com/example/composestylelab/labs/style_composition/StyleCompositionLab.kt`
- Modify: navigation route

**Step 1: Create StyleCompositionLab.kt**

Demonstrates `then` operator for layering styles: `baseCard then elevated then themed`.

The demo has 3 toggle switches that enable/disable each style layer, showing real-time composition.

**Step 2: Wire into navigation**

**Step 3: Build and verify, commit**

```bash
git commit -m "feat: add Lab 2 — Style Composition with then operator demo"
```

---

### Task 6: Lab 3 — State-Driven Cards

**Files:**
- Create: `app/src/main/java/com/example/composestylelab/labs/state_driven_cards/StateDrivenCardsLab.kt`

**Step 1: Create StateDrivenCardsLab.kt**

Grid of cards demonstrating `StyleState.isSelected`, `isChecked`, `isEnabled`. Each card toggles state on tap, with animated style transitions.

**Step 2: Wire, build, commit**

```bash
git commit -m "feat: add Lab 3 — State-Driven Cards with selection and toggle demos"
```

---

### Task 7: Lab 4 — Animated Transforms

**Files:**
- Create: `app/src/main/java/com/example/composestylelab/labs/animated_transforms/AnimatedTransformsLab.kt`

**Step 1: Create AnimatedTransformsLab.kt**

Demonstrates `animate { scale(), rotationZ(), translationX/Y() }` in styles. Shows a 3D flip card and a bouncy press button.

**Step 2: Wire, build, commit**

```bash
git commit -m "feat: add Lab 4 — Animated Transforms with flip card and bouncy press"
```

---

### Task 8: Lab 5 — Shadow Play

**Files:**
- Create: `app/src/main/java/com/example/composestylelab/labs/shadow_play/ShadowPlayLab.kt`

**Step 1: Create ShadowPlayLab.kt**

Demonstrates `dropShadow()` and `innerShadow()` with state-dependent parameters. Neumorphic toggle buttons.

**Step 2: Wire, build, commit**

```bash
git commit -m "feat: add Lab 5 — Shadow Play with neumorphic toggle buttons"
```

---

### Task 9: Lab 6 — Text Styling

**Files:**
- Create: `app/src/main/java/com/example/composestylelab/labs/text_styling/TextStylingLab.kt`

**Step 1: Create TextStylingLab.kt**

Demonstrates `fontSize()`, `fontWeight()`, `contentColor()`, `contentBrush()` within styles. Text that transitions weight/color on hover, gradient text via contentBrush.

**Step 2: Wire, build, commit**

```bash
git commit -m "feat: add Lab 6 — Text Styling with animated font weight and gradient brush"
```

---

### Task 10: Lab 7 — Theme Integration

**Files:**
- Create: `app/src/main/java/com/example/composestylelab/labs/theme_integration/ThemeIntegrationLab.kt`

**Step 1: Create ThemeIntegrationLab.kt**

Demonstrates accessing `CompositionLocal` inside styles (`LocalColors.current`). Has a light/dark toggle that shows styles reacting to theme automatically.

**Step 2: Wire, build, commit**

```bash
git commit -m "feat: add Lab 7 — Theme Integration with CompositionLocal in styles"
```

---

### Task 11: Lab 8 — Custom Components (Capstone)

**Files:**
- Create: `app/src/main/java/com/example/composestylelab/labs/custom_components/CustomComponentsLab.kt`
- Create: `app/src/main/java/com/example/composestylelab/labs/custom_components/StyledChip.kt`
- Create: `app/src/main/java/com/example/composestylelab/labs/custom_components/StyledCard.kt`

**Step 1: Create StyledChip.kt — reusable component following API guidelines + Styles API**

```kotlin
// A chip that:
// - Accepts modifier as first optional param (API guideline)
// - Uses slot API for content (API guideline)
// - Has ChipDefaults object (API guideline)
// - Accepts style: Style parameter (Styles API)
```

**Step 2: Create StyledCard.kt — same pattern**

**Step 3: Create CustomComponentsLab.kt — demonstrates both components with various styles**

**Step 4: Wire, build, commit**

```bash
git commit -m "feat: add Lab 8 — Custom Components combining API guidelines with Styles API"
```

---

### Task 12: Finalize — README, polish, and update LabCard to use Styles API

**Files:**
- Create: `README.md`
- Modify: `app/src/main/java/com/example/composestylelab/components/LabCard.kt` (add Styles API press effect)

**Step 1: Update LabCard to use Styles API for press/hover effects on home screen**

Once we've confirmed the API compiles in earlier tasks, retrofit the home screen cards with the Styles API.

**Step 2: Create README.md**

```markdown
# Compose Style Lab

First-mover demo of the **Compose Styles API** — the new experimental declarative styling system in Jetpack Compose (`@ExperimentalFoundationStyleApi`, `compose-foundation:1.11.0-alpha06`).

## Labs

1. **Interactive Buttons** — pressed, hovered, focused state blocks with animate
2. **Style Composition** — layering styles with `then` operator
3. **State-Driven Cards** — isSelected, isChecked, isEnabled
4. **Animated Transforms** — scale, rotation, translation in animate blocks
5. **Shadow Play** — dropShadow, innerShadow with state-dependent params
6. **Text Styling** — fontSize, fontWeight, contentBrush in styles
7. **Theme Integration** — CompositionLocal access inside styles
8. **Custom Components** — building reusable components with style parameter

## Requirements

- Android Studio Ladybug or later
- Min SDK 24
- Uses `compose-foundation:1.11.0-alpha06` (experimental)

## Build

\`\`\`bash
./gradlew assembleDebug
\`\`\`
```

**Step 3: Final build verification and commit**

```bash
git add -A
git commit -m "docs: add README and polish home screen with Styles API effects"
```

---

## Important Note: API Surface Verification

The Styles API was added to `compose-foundation:1.11.0-alpha06` just 3 days ago. The exact public API surface (`Style`, `StyleScope`, `Modifier.styleable`, `ClickableStyleableBox`, convenience functions like `pressed {}`, `hovered {}`, `animate {}`) needs to be verified at compile time. Tasks 4-11 include the pattern we expect based on the documentation, but the implementer MUST:

1. After Task 1 succeeds (gradle sync with the alpha dependency), inspect the available API surface
2. Run: `./gradlew dependencies --configuration releaseRuntimeClasspath | grep foundation` to confirm 1.11.0-alpha06 resolves
3. Write a minimal test file importing `androidx.compose.foundation.ExperimentalFoundationApi` and attempting `Style { }` to verify compilation
4. Adapt the code patterns in Tasks 4-11 to match the actual API if it differs from the documentation
