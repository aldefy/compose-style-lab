---
title: "Compose's new Styles API: what you need to know"
published: true
tags: android, kotlin, jetpackcompose, mobile
canonical_url: https://aditlal.dev/compose-styles/
cover_image:
series: Compose Styles API
---

`compose-foundation:1.11.0-alpha06` dropped last week with a new declarative Styles API. I spent three days building 8 labs to figure out what it can do and where it breaks.

[GitHub repo with all 8 labs](https://github.com/aldefy/compose-style-lab)

## The boilerplate problem

If you've built interactive Compose components, this code probably looks familiar:

```kotlin
val interactionSource = remember { MutableInteractionSource() }
val isPressed by interactionSource.collectIsPressedAsState()
val backgroundColor by animateColorAsState(
    if (isPressed) pressedColor else defaultColor
)
val scale by animateFloatAsState(if (isPressed) 0.95f else 1f)

Box(
    modifier = Modifier
        .graphicsLayer { scaleX = scale; scaleY = scale }
        .background(backgroundColor, RoundedCornerShape(16.dp))
        .clickable(interactionSource = interactionSource, indication = null) { }
)
```

That's five declarations for one pressed state. Add hovered, focused, disabled, and you're looking at 15-20 lines of state wiring before you've written any actual UI logic. It works, but it doesn't scale well when you have a design system with dozens of interactive components.

## The new API in 30 seconds

The Styles API replaces all of that with a single `Style {}` block:

```kotlin
val myStyle = Style {
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

Box(Modifier.styleable(style = myStyle))
```

You declare what each state looks like. The framework handles detection, animation, and property changes. That's the idea, anyway.

Everything sits behind `@OptIn(ExperimentalFoundationStyleApi::class)`.

## Lab 1: Interaction states without boilerplate

The first thing I tried was a single Style that handles pressed, hovered, and focused:

```kotlin
val showcaseStyle = Style {
    background(baseColor)
    shape(RoundedCornerShape(16.dp))
    contentPadding(horizontal = 32.dp, vertical = 24.dp)

    pressed(Style {
        animate(Style {
            background(Color(0xFF1A237E))
            scale(0.92f)
        })
    })
    hovered(Style {
        animate(Style {
            background(Color(0xFF536DFE))
            scale(1.04f)
            borderWidth(2.dp)
            borderColor(Color.White.copy(alpha = 0.5f))
        })
    })
    focused(Style {
        animate(Style {
            borderWidth(3.dp)
            borderColor(Color.White)
            background(Color(0xFF304FFE))
        })
    })
}
```

Three interaction states, one block. No `collectIsPressedAsState()`, no `animateColorAsState()`, no `graphicsLayer`. The `animate()` wrapper tells the framework to interpolate between states automatically.

One thing worth noting: `Style` is a `fun interface`, so `pressed(Style { ... })` and `pressed { ... }` are equivalent — Kotlin's SAM conversion handles it. I used the explicit form in these examples for readability when nesting.

## Lab 2: Style composition

This is where it gets interesting for design systems. Styles compose with `.then()`, just like modifiers:

```kotlin
val baseCard = Style {
    background(LabCyan.copy(alpha = 0.15f))
    shape(RoundedCornerShape(16.dp))
    contentPadding(horizontal = 24.dp, vertical = 20.dp)
}

val elevatedCard = Style {
    borderWidth(2.dp)
    borderColor(Color(0xFFB0BEC5))
    scale(1.02f)
}

val darkTheme = Style {
    background(Color(0xFF1E1E2E))
    contentColor(Color.White)
}

// Layer them:
val composed = baseCard.then(elevatedCard).then(darkTheme)
```

Later styles override earlier ones, same as CSS specificity. You can also use the factory syntax: `Style(baseCard, elevatedCard, darkTheme)`.

I can see this being useful for design tokens. Define a `baseButton` style, then layer on `primary`, `secondary`, `destructive` variants. Each variant only specifies what changes. The approach feels familiar if you've worked with CSS-in-JS or Tailwind's `@apply`.

## Lab 3: The gotcha you'll definitely hit

This is the one that cost me half a day.

I set up cards with `selected()`, `checked()`, and `disabled()` state blocks. Applied `styleable(style = cardStyle)` and chained `clickable()` on the same modifier. Tapped the cards. Nothing happened. The state blocks just silently did nothing.

Turns out, **auto-detection of interaction states is broken in alpha06**. `styleable(style = ...)` without an explicit `styleState` parameter does not pick up state from sibling modifiers like `clickable()` or `toggleable()`.

You have to wire it yourself:

```kotlin
val interactionSource = remember { MutableInteractionSource() }
val styleState = remember { MutableStyleState(interactionSource) }

// For pressed — auto-tracked via shared interactionSource
Box(
    Modifier
        .styleable(styleState = styleState, style = cardStyle)
        .clickable(
            interactionSource = interactionSource,
            indication = null
        ) { }
)

// For checked/selected/disabled — set explicitly
styleState.isChecked = isChecked
styleState.isSelected = isSelected
styleState.isEnabled = isEnabled
```

This is roughly the same amount of wiring as the old approach. The benefit is that your visual states still live in one `Style {}` block instead of scattered across multiple `animateXAsState()` calls, but the ergonomic promise of "just add `styleable()` and it works" isn't there yet.

I [filed a bug](https://issuetracker.google.com/issues/488585495) for this. It's alpha, so hopefully it gets addressed before beta.

## Lab 4: Animated transforms

One thing that does work nicely: transform properties inside styles. No more `graphicsLayer {}` blocks.

```kotlin
val spinStyle = Style {
    background(Color(0xFF3D5AFE))
    shape(RoundedCornerShape(16.dp))
    contentPadding(20.dp)
    checked(Style {
        animate(Style {
            rotationZ(360f)
            background(Color(0xFF00C853))
        })
    })
}

val slideStyle = Style {
    background(Color(0xFF00BCD4))
    shape(RoundedCornerShape(16.dp))
    contentPadding(20.dp)
    checked(Style {
        animate(Style {
            translationX(50f)
            translationY(-10f)
        })
    })
}
```

`scale()`, `rotationZ()`, `translationX()`, `translationY()` — they all live alongside color and shape properties in the same block. Toggle a boolean and the framework animates between states. Having color, shape, and transform in the same block is something I didn't realize I wanted until I used it.

## What's next

In Part 2, I'll cover the remaining four labs: micro-interactions with `contentColor()` propagation, text property animations, theme integration with `MaterialTheme`, and a pattern for building custom components with style parameters.

If you can't wait, the [full writeup with all 8 labs is on my blog](https://aditlal.dev/compose-styles/).

---

*Originally published at [aditlal.dev](https://aditlal.dev/compose-styles/)*
