# Compose Style Lab — Design Document

## Overview

First-mover demo app for the **Compose Styles API** (`@ExperimentalFoundationStyleApi`), introduced in `compose-foundation:1.11.0-alpha06` (Feb 25, 2026). A cookbook of 8 interactive lab screens, each demonstrating a facet of the new Styles API with visually compelling examples.

## Platform & Dependencies

- Android-only (single module)
- `compileSdk = 35`, `minSdk = 24`, `targetSdk = 35`
- `compose-foundation:1.11.0-alpha06` (Styles API)
- Latest Compose BOM for material3, navigation, animation
- Kotlin 2.1.x, AGP 8.9.x

## Architecture

```
com.example.composestylelab/
├── MainActivity.kt
├── navigation/
│   └── StyleLabNavigation.kt
├── home/
│   └── HomeScreen.kt
├── labs/
│   ├── interactive_buttons/
│   │   └── InteractiveButtonsLab.kt
│   ├── style_composition/
│   │   └── StyleCompositionLab.kt
│   ├── state_driven_cards/
│   │   └── StateDrivenCardsLab.kt
│   ├── animated_transforms/
│   │   └── AnimatedTransformsLab.kt
│   ├── shadow_play/
│   │   └── ShadowPlayLab.kt
│   ├── text_styling/
│   │   └── TextStylingLab.kt
│   ├── theme_integration/
│   │   └── ThemeIntegrationLab.kt
│   └── custom_components/
│       └── CustomComponentsLab.kt
├── components/
│   ├── LabScaffold.kt
│   ├── LabCard.kt
│   └── CodeSnippet.kt
└── theme/
    └── Theme.kt
```

## The 8 Labs

### Lab 1: Interactive Buttons
- **Styles API:** `pressed { animate { } }`, `hovered { animate { } }`, `background()`, `scale()`, `shape()`
- **Demo:** Row of buttons showing hover glow, press shrink, focus ring — all via Styles API
- **Contrast:** Shows how this replaces ~20 lines of InteractionSource boilerplate

### Lab 2: Style Composition
- **Styles API:** `then` operator for layering styles
- **Demo:** Build a style system live: `baseCard then elevatedCard then darkTheme`. Toggle each layer on/off to see composition in action.

### Lab 3: State-Driven Cards
- **Styles API:** `StyleState.isSelected`, `isChecked`, `isEnabled`
- **Demo:** Grid of selectable/toggleable cards with automatic cross-fade animations between states

### Lab 4: Animated Transforms
- **Styles API:** `animate { scale(), rotationZ(), translationX/Y() }` on state changes
- **Demo:** 3D flip card, bouncy press effect, magnetic hover — all driven by style state + animate blocks

### Lab 5: Shadow Play
- **Styles API:** `dropShadow()`, `innerShadow()` with state-dependent parameters
- **Demo:** Neumorphic raised/pressed toggle buttons using only the Styles API shadow system

### Lab 6: Text Styling
- **Styles API:** `fontSize()`, `fontWeight()`, `contentColor()`, `contentBrush()` within styles
- **Demo:** Text that changes weight and color on hover, gradient text via contentBrush

### Lab 7: Theme Integration
- **Styles API:** `CompositionLocal` access inside styles (`LocalColors.current`)
- **Demo:** Light/dark mode toggle where styles automatically react to theme changes. No manual recomposition needed.

### Lab 8: Custom Components
- **Styles API:** Accepting `style: Style` as a component parameter
- **Demo:** Reusable `StyledChip` and `StyledCard` components that follow Compose API guidelines (modifier contract, slot APIs, defaults objects) AND accept styles

## Screen Layout (each lab)

```
┌─────────────────────────┐
│ ← Lab Title             │
│ One-line description    │
├─────────────────────────┤
│                         │
│   [Live Interactive     │
│    Demo Area]           │
│                         │
├─────────────────────────┤
│ Key Code Snippet        │
│ (the Styles API code    │
│  powering the demo)     │
└─────────────────────────┘
```

## Design Decisions

1. Every component follows Compose API guidelines (modifier as first optional param, slot APIs, defaults objects)
2. Home screen cards use the Styles API themselves (pressed/hover effects) — the user experiences the API immediately
3. Each lab shows a concise code snippet of the Styles API code powering the live demo
4. Lab 8 ties API guidelines + Styles API together as the capstone
5. No "wrong way" toggle — clean, focused demos
