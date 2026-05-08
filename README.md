# Mobile Kotlin RemoteState

A Kotlin Multiplatform library for managing remote state in your Android, iOS, and Web applications.

## Overview

Moko State provides a simple and efficient way to handle remote data states with Kotlin's coroutines and Flow.
It's designed for multiplatform projects, allowing you to share state management logic across iOS, Android, and Web platforms.

## Features

- **Multiplatform support**: Works on Android, iOS, and Web
- **Coroutines & Flow**: Built on Kotlin coroutines and Flow for reactive programming
- **Simple API**: Easy to use with intuitive state representation
- **Type-safe**: Compile-time safe state transitions
- **Zero dependencies**: Pure Kotlin implementation

## Getting Started

### Gradle Setup

Add the dependency to your common source set:

```kotlin
implementation("dev.icerock.moko:state:[latestVersion]")
```

### Usage

```kotlin
import dev.icerock.moko.state.RemoteState
import dev.icerock.moko.state.mapSuccess

// Define your state
val stateFlow = MutableStateFlow<RemoteState<String, Exception>>(
    value = RemoteState.Loading
)

// Update state
stateFlow.value = RemoteState.Success("Hello, World!")

// Map success value
val mappedState = stateFlow.value.mapSuccess { it.uppercase() }

// Check state
if (mappedState.isSuccess()) {
    println(mappedState.data)
}
```

## API Reference

### RemoteState

A sealed class representing the state of remote data.

- `Loading` - Represents an ongoing operation
- `Success<T>` - Contains successfully loaded data
- `Error<E>` - Contains an error occurred during operation

### Functions

- `mapSuccess` - Transform data in Success state
- `mapError` - Transform error in Error state
- `isLoading` - Check if state is loading
- `isSuccess` - Check if state is successful
- `tryUpdateSuccess` - Atomically update data in Success state

## Contributing
All development (both new features and bug fixes) is performed in the `develop` branch. This way `master` always contains the sources of the most recently released version. Please send PRs with bug fixes to the `develop` branch. Documentation fixes in the markdown files are an exception to this rule. They are updated directly in `master`.

The `develop` branch is pushed to `master` on release.

For more details on contributing please see the [contributing guide](CONTRIBUTING.md).

## License

Apache 2.0 License - see [LICENSE](LICENSE) file for details.
