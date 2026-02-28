# Compose Style Lab

First-mover demo of the **Compose Styles API** — the new experimental declarative styling system in Jetpack Compose (`@ExperimentalFoundationStyleApi`, `compose-foundation:1.11.0-alpha06`).

## What is the Styles API?

The Styles API introduces a declarative way to define visual properties and interaction states for composables. Instead of managing `InteractionSource`, `animateColorAsState`, and multiple modifier chains, you define everything in a single `Style { }` block:

```kotlin
Style {
    background(Color.Blue)
    shape(RoundedCornerShape(12.dp))
    contentPadding(16.dp)
    pressed(Style {
        animate(Style {
            background(Color.Blue.copy(alpha = 0.7f))
            scale(0.95f)
        })
    })
}
```

## Labs

| # | Lab | Styles API Features |
|---|-----|-------------------|
| 1 | **Interactive Buttons** | `pressed`, `hovered`, `focused` + `animate` |
| 2 | **Style Composition** | `Style.then()`, `Style(s1, s2, s3)` factory |
| 3 | **State-Driven Cards** | `selected`, `checked`, `disabled` states |
| 4 | **Animated Transforms** | `scale()`, `rotationZ()`, `translationX/Y()` |
| 5 | **Shadow Play** | Depth effects via borders, alpha, scale |
| 6 | **Text Styling** | `fontWeight()`, `contentColor()`, `contentBrush()` |
| 7 | **Theme Integration** | CompositionLocal access inside styles |
| 8 | **Custom Components** | `style: Style` as component parameter + defaults objects |

## Requirements

- Android Studio Ladybug or later
- Min SDK 24, Target SDK 35
- Uses `compose-foundation:1.11.0-alpha06` (experimental)

## Build

```bash
./gradlew assembleDebug
```

## Key Learnings

- State functions use `pressed(Style { ... })` syntax (not trailing lambda)
- `animate(Style { ... })` wraps properties for smooth transitions
- `StyleState` is driven by companion modifiers (`.selectable()`, `.toggleable()`, `.clickable()`)
- Styles can read `MaterialTheme` colors when created in `@Composable` scope
- Components can accept `style: Style` with defaults objects following API guidelines
