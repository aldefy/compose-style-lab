# Blog Design: The Compose Styles API — A Hands-On Guide

**Date**: 2026-02-28
**Author**: Adit Lal
**Platform**: Ghost (aditlal.dev)
**Audience**: Intermediate Compose developers
**Framework**: STAR case study
**Estimated length**: ~3500 words
**AI mention**: Light touch (1-2 sentences in closing)

---

## Title

"The Compose Styles API: Building 8 Labs to Master Declarative Styling"

Subtitle: "A hands-on exploration of compose-foundation 1.11.0-alpha06's experimental Styles API"

---

## Structure (STAR Framework)

### 1. Opening Hook (~150 words)

Start with a **before/after** code comparison. No preamble.

**Before** — The classic InteractionSource boilerplate for a pressed button effect:
```kotlin
val interactionSource = remember { MutableInteractionSource() }
val isPressed by interactionSource.collectIsPressedAsState()
val backgroundColor by animateColorAsState(
    if (isPressed) pressedColor else defaultColor
)
val scale by animateFloatAsState(
    if (isPressed) 0.95f else 1f
)
Box(
    modifier = Modifier
        .graphicsLayer { scaleX = scale; scaleY = scale }
        .background(backgroundColor, RoundedCornerShape(16.dp))
        .clickable(interactionSource = interactionSource, indication = null) { }
)
```

**After** — The Styles API equivalent:
```kotlin
val style = Style {
    background(defaultColor)
    shape(RoundedCornerShape(16.dp))
    pressed(Style { animate(Style { background(pressedColor); scale(0.95f) }) })
}
Box(Modifier.styleable(style = style))
```

Punchline: "One declarative definition. No InteractionSource. No animateAsState. No graphicsLayer. This is the Compose Styles API."

---

### 2. Situation (~300 words)

**Section title**: "How Compose Handles Styling Today"

Paint the current landscape for an intermediate dev:

- **InteractionSource is powerful but verbose.** Collecting pressed/hovered/focused state, wiring up animation, applying transforms — it works, but it's ceremony-heavy for something CSS does in 3 lines.

- **No reusable style objects.** If you want the same pressed effect on 5 buttons, you copy-paste the same InteractionSource + animate block. Or extract it into a custom Modifier, but now you're building your own styling system.

- **State-driven visual changes require manual `when` blocks.** Selected? Checked? Disabled? Each one needs its own state tracking + conditional Modifier logic.

- **No composition.** You can chain Modifiers, but you can't say "take this base style + overlay this elevated style + overlay this dark theme override" and have properties merge predictably.

End with: "When I saw `compose-foundation:1.11.0-alpha06` land on Feb 25, 2026 with the `@ExperimentalFoundationStyleApi`, I wanted to explore what it actually delivers."

---

### 3. Task (~200 words)

**Section title**: "Building Compose Style Lab"

Brief intro to the demo app:

- 8 interactive lab screens, each exploring a different facet of the Styles API
- Progressive difficulty: from basic interaction states to custom component architecture
- Every lab includes live demos with real state toggling — not static previews
- Link to the GitHub repo: `github.com/aldefy/ComposeStylingApiDemo` (or final repo URL)

Quick API overview paragraph:
- The Styles API lives in `compose-foundation:1.11.0-alpha06`
- Core concept: `Style { }` block defines visual properties declaratively
- Applied via `Modifier.styleable(style = myStyle)`
- State blocks: `pressed()`, `hovered()`, `focused()`, `selected()`, `checked()`, `disabled()`
- Animation: wrap properties in `animate(Style { ... })` for automatic interpolation
- ~50 style properties covering background, shape, borders, transforms, text, and content color

---

### 4. Action — Lab Walkthrough (~2500 words)

**Section title**: "8 Labs, 8 Lessons"

Each lab follows this mini-template:

#### Lab N: [Title]
> One-sentence summary of what this lab explores.

[Screenshot: Lab N]

```kotlin
// Key code snippet — the Style definition
```

**What you learn:**
- Bullet 1
- Bullet 2
- (Optional) Bullet 3

**Gotcha** (if applicable): Callout box with a tip or pitfall.

---

#### Lab 1: Interaction States Without the Boilerplate

> One Style definition handles pressed, hovered, and focused states — with animation.

[Screenshot: Lab 1]

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

**What you learn:**
- `pressed()`, `hovered()`, `focused()` each take a `Style` — not a lambda
- Wrap state styles in `animate()` for smooth transitions
- One definition replaces InteractionSource + collectAsState + animateColorAsState + graphicsLayer

---

#### Lab 2: Composing Styles Like Modifiers

> Build reusable style layers and compose them with `.then()`.

[Screenshot: Lab 2]

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

// Later styles override earlier ones:
val composed = baseCard.then(elevatedCard).then(darkTheme)
```

**What you learn:**
- `.then()` works like Modifier chaining — later properties override earlier ones
- `Style(s1, s2, s3)` factory is an alternative to chaining
- This enables design tokens: define base styles once, compose variations

---

#### Lab 3: Driving Visual State Declaratively

> `selected()`, `checked()`, and `disabled()` with explicit state driving.

[Screenshot: Lab 3]

```kotlin
val cardStyle = Style {
    background(AccentOrange.copy(alpha = 0.15f))
    shape(RoundedCornerShape(12.dp))
    borderWidth(2.dp)
    borderColor(AccentOrange)
    disabled(Style {
        background(Color(0xFFE0E0E0))
        contentColor(Color(0xFF9E9E9E))
        scale(0.98f)
    })
}

// Explicit state driving:
val styleState = remember { MutableStyleState(interactionSource) }
styleState.isEnabled = enabled
Box(Modifier.styleable(styleState = styleState, style = cardStyle))
```

**What you learn:**
- `selected()`, `checked()`, `disabled()` are state blocks just like `pressed()`
- State is driven explicitly via `MutableStyleState` — set `isChecked`, `isSelected`, `isEnabled`

**Gotcha**: In alpha06, `Modifier.styleable(style = ...)` alone does NOT auto-detect state from `toggleable()` or `clickable()`. You must use `MutableStyleState` and drive state explicitly. This was the single biggest discovery while building the demo.

---

#### Lab 4: Animated Transforms in 3 Lines

> `scale()`, `rotationZ()`, and `translationX/Y()` inside animate blocks.

[Screenshot: Lab 4]

```kotlin
val spinStyle = Style {
    background(Color(0xFF3D5AFE))
    shape(RoundedCornerShape(16.dp))
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
    checked(Style {
        animate(Style {
            translationX(50f)
            translationY(-10f)
        })
    })
}
```

**What you learn:**
- Transform properties (`scale`, `rotationZ`, `translationX/Y`) work inside `animate()`
- No `graphicsLayer` needed — the Style system handles rendering
- Combine transforms with color/background changes in a single state block

---

#### Lab 5: Real-World Micro-Interactions

> Favorite buttons, nav bars, pill toggles — practical patterns.

[Screenshot: Lab 5]

```kotlin
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
```

**What you learn:**
- `contentColor()` propagates to child Text/Icon composables via CompositionLocal
- `CircleShape` + `scale()` creates satisfying micro-interactions
- The same pattern works for nav bar items, toggle pills, notification badges

---

#### Lab 6: Text Properties You Didn't Know You Could Style

> `fontSize()`, `fontWeight()`, `contentBrush()`, `letterSpacing()`, and `textDecoration()`.

[Screenshot: Lab 6]

```kotlin
val pressTextStyle = Style {
    contentColor(Color.Black)
    fontSize(18.sp)
    letterSpacing(0.sp)
    pressed(Style {
        animate(Style {
            contentColor(Color(0xFFFF6D00))
            letterSpacing(4.sp)
            textDecoration(TextDecoration.Underline)
            scale(0.96f)
        })
    })
}

val gradientStyle = Style {
    contentBrush(Brush.linearGradient(listOf(Color.Magenta, Color.Cyan)))
    fontSize(28.sp)
    fontWeight(FontWeight.Bold)
}
```

**What you learn:**
- Text properties are first-class in the Style system
- `contentBrush()` enables gradient text without custom drawing
- `letterSpacing()` and `textDecoration()` can animate on interaction state changes

---

#### Lab 7: Theme-Aware Styles

> Styles read MaterialTheme colors — and auto-update on dark/light toggle.

[Screenshot: Lab 7]

```kotlin
val primary = MaterialTheme.colorScheme.primary
val onPrimary = MaterialTheme.colorScheme.onPrimary
val surface = MaterialTheme.colorScheme.surface

val buttonStyle = Style {
    background(primary)
    contentColor(onPrimary)
    shape(RoundedCornerShape(12.dp))
    pressed(Style {
        animate(Style {
            background(surface)
            contentColor(onSurface)
            scale(0.95f)
        })
    })
}
```

**What you learn:**
- `StyleScope` extends `CompositionLocalAccessorScope` — you can read CompositionLocals
- This means styles automatically react to theme changes
- Dark/light mode toggle updates style colors without any additional wiring

---

#### Lab 8: Custom Components with Style Parameters

> The API guidelines pattern: Defaults object + style parameter + `.then()` override.

[Screenshot: Lab 8]

```kotlin
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
            pressed(Style { animate(Style { scale(0.95f) }) })
        }
    }
}

@Composable
fun StyledChip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: Style = StyledChipDefaults.style(),
    content: @Composable () -> Unit,
)
```

**What you learn:**
- Follow the same pattern as Material3: Defaults object with `@Composable fun style()`
- Callers override with `style = StyledChipDefaults.style().then(Style { ... })`
- This is how the Compose team envisions component APIs using Styles

---

### 5. Result — Key Takeaways (~400 words)

**Section title**: "What I Learned Building 8 Labs"

1. **MutableStyleState is non-negotiable in alpha06.** Auto-detection from `toggleable()` / `clickable()` is broken. Every checked, selected, or pressed state must be driven explicitly. This will likely be fixed in later alphas, but for now, it's the #1 gotcha.

2. **Style composition is the real power.** Individual state blocks are nice, but the ability to layer styles with `.then()` — base + elevation + theme + override — is what makes this a design system tool, not just a convenience wrapper.

3. **What doesn't work yet.** `dropShadow()` has an internal constructor — can't use it from app code. Some properties like `outlineWidth` exist in the API surface but don't render. This is alpha software.

4. **contentColor propagation is elegant.** Set `contentColor()` in a style, and child Text/Icon composables automatically pick it up via CompositionLocal. No need to pass colors down manually.

5. **Theme integration is seamless.** Because `StyleScope` can read CompositionLocals, styles are automatically theme-aware. Dark mode just works.

6. **Where this is headed.** The Styles API looks like Compose's answer to CSS-in-JS — declarative, composable, state-aware. When it stabilizes, it could fundamentally change how we build Compose component libraries.

Closing paragraph: Brief mention that Claude Code and ComposeProof helped accelerate the exploration of this alpha API. Link to the GitHub repo. Invite readers to try the labs and report what they find.

---

## Skill.md Companion

Create a `SKILL.md` in the repo (modeled on `aldefy/compose-skill`):

### Structure:
1. **Header**: Compose Styles API Expert Skill
2. **When to use**: Working with `compose-foundation:1.11.0-alpha06+`, `@ExperimentalFoundationStyleApi`
3. **API Surface**: Verified list of ~50 properties that work
4. **Critical Patterns**: MutableStyleState, shared InteractionSource, Style composition
5. **State Blocks**: pressed, hovered, focused, selected, checked, disabled
6. **Component Pattern**: Defaults object + style parameter
7. **Known Issues**: What doesn't work in alpha06
8. **References**: Link to 8 lab files as learning material

### Source:
Pull from existing `compose-styles-api-skill.md` in memory dir, plus the lab code examples.

---

## Deliverables

1. `docs/blog/2026-02-28-compose-styles-api-blog.md` — The full blog post
2. `SKILL.md` — Compose Styles API skill for AI coding assistants
3. Run through `/humanizer` skill before finalizing
