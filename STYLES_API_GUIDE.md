# Compose Styles API — Developer Guide

> **What we learned building the first demo app for `@ExperimentalFoundationStyleApi`**
>
> `androidx.compose.foundation:foundation:1.11.0-alpha06` | Feb 2026

---

## Why This API Exists

Before the Styles API, building a button with press feedback looked like this:

```kotlin
// OLD WAY: 15+ lines of imperative state management
val interactionSource = remember { MutableInteractionSource() }
val isPressed by interactionSource.collectIsPressedAsState()
val bgColor by animateColorAsState(
    if (isPressed) Color.DarkBlue else Color.Blue
)
val scale by animateFloatAsState(
    if (isPressed) 0.95f else 1f
)

Box(
    Modifier
        .graphicsLayer { scaleX = scale; scaleY = scale }
        .background(bgColor, RoundedCornerShape(16.dp))
        .clip(RoundedCornerShape(16.dp))
        .clickable(interactionSource = interactionSource, indication = null) { }
        .padding(16.dp)
)
```

Now:

```kotlin
// NEW WAY: Declare what it looks like in each state. Done.
val style = Style {
    background(Color.Blue)
    shape(RoundedCornerShape(16.dp))
    contentPadding(16.dp)
    pressed(Style {
        animate(Style {
            background(Color.DarkBlue)
            scale(0.95f)
        })
    })
}

Box(Modifier.styleable(styleState = styleState, style = style))
```

The mental shift: **stop telling Compose how to animate between states**. Declare what each state looks like — the framework interpolates.

---

## The Three Pieces

### 1. `Style { }` — Declare visual states

A `Style` is a `fun interface` with a single method. You never implement it directly — you use the builder DSL:

```kotlin
val cardStyle = Style {
    // Base properties (always applied)
    background(Color(0xFFF5F5F5))
    shape(RoundedCornerShape(12.dp))
    contentPadding(16.dp)

    // State overrides — only applied when that state is active
    selected(Style {
        animate(Style {
            background(Color.Blue.copy(alpha = 0.15f))
            borderWidth(2.dp)
            borderColor(Color.Blue)
        })
    })

    checked(Style {
        animate(Style {
            background(Color.Green.copy(alpha = 0.15f))
            scale(1.02f)
        })
    })

    disabled(Style {
        background(Color(0xFFE0E0E0))
        contentColor(Color.Gray)
        scale(0.98f)
    })
}
```

Note: `animate(Style { })` wraps properties that should interpolate smoothly. Without `animate`, state changes snap immediately (useful for `disabled()` where you want instant visual feedback).

### 2. `MutableStyleState` — Drive state explicitly

This is where alpha06 gets tricky. See the [Critical Gotcha](#the-critical-alpha06-gotcha) below.

```kotlin
// For checked/selected/enabled — set explicitly
val styleState = remember { MutableStyleState(MutableInteractionSource()) }
styleState.isChecked = isChecked
styleState.isSelected = isSelected
styleState.isEnabled = isEnabled

// For pressed/hovered/focused — share the InteractionSource
val interactionSource = remember { MutableInteractionSource() }
val styleState = remember { MutableStyleState(interactionSource) }
// isPressed auto-updates when the shared interactionSource detects press gestures
```

### 3. `Modifier.styleable()` — Apply to any composable

```kotlin
Box(
    Modifier
        .styleable(styleState = styleState, style = cardStyle)
        .clickable(interactionSource = interactionSource, indication = null) { }
)
```

That's it. Background renders, shape clips, borders draw, transforms apply, text properties propagate to children via CompositionLocal. All animated.

---

## The Critical Alpha06 Gotcha

**`styleable(style = myStyle)` without an explicit `styleState` does NOT detect interaction states from sibling modifiers.**

This is the single biggest trap in alpha06. The API *looks like* it should auto-detect state from `.toggleable()`, `.selectable()`, or `.clickable()` — but it doesn't.

### What DOESN'T work:

```kotlin
// This compiles. This renders the base style. State changes are SILENT.
Box(
    Modifier
        .styleable(style = myStyle)           // no styleState!
        .toggleable(value = isChecked, ...)   // style never sees this
)
```

### What DOES work:

**Pattern A — Toggle states (checked, selected, enabled):**
```kotlin
val styleState = remember { MutableStyleState(MutableInteractionSource()) }
styleState.isChecked = isChecked  // YOU drive the state

Box(
    Modifier
        .styleable(styleState = styleState, style = myStyle)
        .clickable { isChecked = !isChecked }
)
```

**Pattern B — Interaction states (pressed, hovered, focused):**
```kotlin
val interactionSource = remember { MutableInteractionSource() }
val styleState = remember { MutableStyleState(interactionSource) }
// isPressed auto-tracks from the SHARED interactionSource

Box(
    Modifier
        .styleable(styleState = styleState, style = myStyle)
        .clickable(
            interactionSource = interactionSource,  // same instance!
            indication = null,
        ) { }
)
```

**Pattern C — Both (e.g., a toggle button with press feedback):**
```kotlin
val interactionSource = remember { MutableInteractionSource() }
val styleState = remember { MutableStyleState(interactionSource) }
styleState.isChecked = isChecked  // explicit for toggle state
// isPressed auto-tracks from shared interactionSource

Box(
    Modifier
        .styleable(styleState = styleState, style = myStyle)
        .clickable(
            interactionSource = interactionSource,
            indication = null,
        ) { isChecked = !isChecked }
)
```

We spent hours debugging "styles don't render" before discovering this. Save yourself the pain — **always pass `styleState`**.

---

## What Renders in Alpha06 (Verified on Device)

We tested every property category on a Pixel 9 Pro Fold. Here's what actually works:

| Property | Renders? | Notes |
|----------|----------|-------|
| `background(Color)` | Yes | Fills behind content |
| `background(Brush)` | Yes | Gradient backgrounds |
| `shape(Shape)` | Yes | Clips content + background |
| `contentPadding(Dp)` | Yes | Inner padding |
| `borderWidth(Dp) + borderColor(Color)` | Yes | Must set both |
| `scale(Float)` | Yes | graphicsLayer transform |
| `rotationZ(Float)` | Yes | graphicsLayer rotation |
| `translationX/Y(Float)` | Yes | graphicsLayer offset |
| `alpha(Float)` | Yes | Opacity |
| `contentColor(Color)` | Yes | Propagates to child Text/Icon via LocalContentColor |
| `contentBrush(Brush)` | Yes | Gradient text! |
| `fontSize(TextUnit)` | Yes | Propagates to children |
| `fontWeight(FontWeight)` | Yes | Propagates to children |
| `letterSpacing(TextUnit)` | Yes | Propagates to children |
| `textDecoration(TextDecoration)` | Yes | Underline, strikethrough |
| `animate(Style { })` | Yes | Smooth spring interpolation |
| `dropShadow(Shadow)` | No | `Shadow` constructor is internal in alpha06 |

---

## Style Composition

Styles compose with `.then()` — later styles override earlier ones on a per-property basis:

```kotlin
val base = Style {
    background(Color.Blue)
    shape(RoundedCornerShape(12.dp))
    contentPadding(16.dp)
}

val elevated = Style {
    borderWidth(2.dp)
    borderColor(Color.LightGray)
    scale(1.02f)
}

val dark = Style {
    background(Color(0xFF1E1E2E))  // overrides base's background
    contentColor(Color.White)
}

// Chained:
val composed = base.then(elevated).then(dark)

// Factory (equivalent):
val composed = Style(base, elevated, dark)
```

This is powerful for building component libraries — see [Building Reusable Components](#building-reusable-components-with-style).

---

## Building Reusable Components with Style

The Styles API maps naturally to Compose's component API conventions:

```kotlin
// 1. Defaults object — provides theme-aware default style
@OptIn(ExperimentalFoundationStyleApi::class)
object StyledChipDefaults {
    @Composable
    fun style(): Style {
        val bg = MaterialTheme.colorScheme.secondaryContainer
        val fg = MaterialTheme.colorScheme.onSecondaryContainer
        return Style {
            background(bg)
            shape(RoundedCornerShape(8.dp))
            contentPadding(horizontal = 16.dp, vertical = 8.dp)
            contentColor(fg)
            pressed(Style {
                animate(Style { scale(0.95f) })
            })
        }
    }
}

// 2. Component — accepts style as parameter with sensible default
@Composable
fun StyledChip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: Style = StyledChipDefaults.style(),
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val styleState = remember { MutableStyleState(interactionSource) }

    Box(
        modifier = modifier
            .styleable(styleState = styleState, style = style)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

// 3. Usage — default, custom, or composed
StyledChip(onClick = {}) { Text("Default") }

StyledChip(
    onClick = {},
    style = Style {
        background(Color.Teal)
        shape(CircleShape)
        contentColor(Color.Black)
    },
) { Text("Custom") }

StyledChip(
    onClick = {},
    style = StyledChipDefaults.style().then(Style {
        borderWidth(2.dp)
        borderColor(Color.Teal)
    }),
) { Text("Composed") }
```

The key insight: **`style` becomes a first-class component parameter**, just like `modifier`. Callers can override everything, compose on top of defaults, or use defaults as-is.

---

## Theme Integration

`StyleScope` extends `CompositionLocalAccessorScope`, which means Style blocks can read CompositionLocals — including `MaterialTheme`:

```kotlin
@Composable
fun ThemedButton() {
    val primary = MaterialTheme.colorScheme.primary
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    val surface = MaterialTheme.colorScheme.surface

    val style = Style {
        background(primary)
        contentColor(onPrimary)
        shape(RoundedCornerShape(12.dp))
        contentPadding(16.dp)
        pressed(Style {
            animate(Style {
                background(surface)
                contentColor(primary)
                scale(0.95f)
            })
        })
    }
    // When theme changes (dark/light), style re-resolves automatically
}
```

Capture theme colors in a `@Composable` scope, use them in the Style builder. When the theme switches (e.g., dark mode toggle), every styled element updates instantly.

---

## Architecture: How It Works Under the Hood

The Styles API lives in 7 source files under `androidx.compose.foundation.style`:

| File | Purpose |
|------|---------|
| `Style.kt` | `fun interface Style` + composition operators (`then`, factory) |
| `StyleScope.kt` | ~50 property functions (background, scale, fontSize, etc.) |
| `StyleState.kt` | `StyleState` interface + `MutableStyleState` |
| `StyleModifier.kt` | `Modifier.styleable()` implementation |
| `StyleAnimations.kt` | `animate()` blocks |
| `ResolvedStyle.kt` | Internal property resolution with bitset flagging |
| `ExperimentalFoundationStyleApi.kt` | Opt-in annotation |

### The Two-Node System

When you call `Modifier.styleable()`, two modifier nodes are inserted:

- **`StyleOuterNode`** — Handles layout (external padding, sizing), drawing (background, border, shape), and transforms (scale, rotation, translation, alpha). This node can invalidate at the draw layer only when transform/draw properties change — no recomposition needed.

- **`StyleInnerNode`** — Handles content padding and text style propagation. Sets `LocalContentColor`, `LocalTextStyle`, and other CompositionLocals so child `Text` and `Icon` composables automatically pick up style-driven colors and fonts.

### Bitset-Based Property Tracking

`ResolvedStyle` uses bitset flags to track which of the ~50 properties are actually set. When state changes:

1. Only the delta between old and new resolved properties is computed
2. If only drawing properties changed (background, border, alpha) → **draw-only invalidation** (skips layout and composition)
3. If layout properties changed (padding, sizing) → layout invalidation
4. If text properties changed (contentColor, fontSize) → composition invalidation (must update CompositionLocals)

This is why the API is efficient — a press animation that only changes `scale` and `background` never triggers recomposition.

---

## All ~50 StyleScope Properties

### Layout
- `contentPadding(Dp)`, `contentPadding(horizontal, vertical)`, `contentPadding(start, top, end, bottom)`
- `externalPadding(Dp)` and same variants
- `width(Dp)`, `height(Dp)`, `size(Dp)`, `size(width, height)`
- `minWidth/minHeight/maxWidth/maxHeight(Dp)`
- `fillWidth()`, `fillHeight()`, `fillSize()`

### Drawing
- `background(Color)`, `background(Brush)`
- `foreground(Color)`, `foreground(Brush)`
- `shape(Shape)`
- `borderWidth(Dp)`, `borderColor(Color)`, `borderBrush(Brush)`
- `border(width, color)`, `border(width, brush)`

### Transforms
- `scale(Float)`, `scaleX(Float)`, `scaleY(Float)`
- `rotationX(Float)`, `rotationY(Float)`, `rotationZ(Float)`
- `translationX(Float)`, `translationY(Float)`, `translation(x, y)`
- `alpha(Float)`, `clip(Boolean)`, `zIndex(Float)`
- `transformOrigin(TransformOrigin)`

### Text & Content
- `contentColor(Color)`, `contentBrush(Brush)`
- `fontSize(TextUnit)`, `fontWeight(FontWeight)`, `fontStyle(FontStyle)`
- `letterSpacing(TextUnit)`, `lineHeight(TextUnit)`
- `textDecoration(TextDecoration)`, `fontFamily(FontFamily)`
- `textAlign(TextAlign)`, `textDirection(TextDirection)`
- `textStyle(TextStyle)`, `textIndent(TextIndent)`
- `baselineShift(BaselineShift)`, `lineBreak(LineBreak)`
- `hyphens(Hyphens)`, `fontSynthesis(FontSynthesis)`

### Shadows (internal constructor in alpha06)
- `dropShadow(Shadow)`, `innerShadow(Shadow)`

### State Functions
- `pressed(Style)`, `hovered(Style)`, `focused(Style)`
- `selected(Style)`, `checked(Style)`, `disabled(Style)`

### Animation
- `animate(Style)` — default spring
- `animate(spec: AnimationSpec<Float>, Style)` — custom spec
- `animate(toSpec, fromSpec, Style)` — asymmetric enter/exit

### Composition
- `Style.then(other: Style)` — chain (later overrides)
- `Style(style1, style2)` — merge factory
- `Style(vararg styles)` — merge multiple

---

## Source References

- **AOSP source**: [androidx/androidx](https://github.com/androidx/androidx) — branch `androidx-main`, path `compose/foundation/foundation/src/commonMain/kotlin/androidx/compose/foundation/style/`
- **Maven artifact**: `androidx.compose.foundation:foundation:1.11.0-alpha06`
- **Release notes**: [Compose Foundation releases](https://developer.android.com/jetpack/androidx/releases/compose-foundation)

---

## Lab Index (This Repo)

| Lab | What It Demonstrates | Key Styles API Features |
|-----|---------------------|------------------------|
| 1. Interactive Buttons | State simulation via toggle switches | `pressed()`, `hovered()`, `focused()`, `MutableStyleState` |
| 2. Style Composition | Layering styles with `.then()` | `Style.then()`, `Style(a, b, c)` factory |
| 3. State-Driven Cards | Selected, checked, disabled states | `selected()`, `checked()`, `disabled()`, `animate()` |
| 4. Animated Transforms | Scale, rotation, translation | `scale()`, `rotationZ()`, `translationX/Y()` |
| 5. Micro-Interactions | Favorite, nav bar, pill toggle, notification bell | `checked()` with `animate()`, `contentColor()` |
| 6. Text Styling | Font properties and gradient text | `fontSize()`, `fontWeight()`, `contentBrush()`, `letterSpacing()` |
| 7. Theme Integration | CompositionLocal in styles | `MaterialTheme` colors in `Style {}` blocks |
| 8. Custom Components | Style as a component parameter | Defaults object pattern, `.then()` composition |
