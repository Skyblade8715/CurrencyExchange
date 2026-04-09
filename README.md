# CurrencyExchange

A modern Android app that displays current currency exchange rates and historical trends based on the NBP API.

## Features

- List of currencies with current exchange rates
- Detailed screen with 2-week historical data
- Smooth and reactive UI built with Jetpack Compose
- Clean architecture with clear separation of concerns

---

## ️ Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Hilt (DI)**
- **Retrofit + Kotlinx Serialization**
- **Coroutines + Flow**
- **MVVM + Clean Architecture**

---

##  Architecture

The project follows a layered architecture:
presentation/
- UI (Compose)
- ViewModels

domain/
- UseCases
- Repository interfaces

data/
- Repository implementations
- API layer

Key principles:
- Unidirectional data flow
- Separation of concerns
- Testable business logic

---

##  State Management

- `StateFlow` used for UI state
- `stateIn` to optimize subscriptions
- `debounce + distinctUntilChanged` for search
- Minimal recompositions via stable UI models

---

##  API

Data is fetched from the **NBP (Narodowy Bank Polski) API**:

- Current rates
- Historical exchange rates (last 2 weeks)
