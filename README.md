# KNewsApp

A modern, modularized Android news application built with **Jetpack Compose** and **Firebase**. This project demonstrates industry-standard best practices, focusing on a clean architecture, robust security, and an offline-first user experience with 90%+ test coverage.

## 🚀 Key Features
*   **Authentication:** Fully integrated Firebase Auth supporting both Email/Password and Google Sign-In via Credential Manager.
*   **Persistent Sessions:** Smart session management that keeps users logged in across app restarts.
*   **Mobile First & Offline Sync:** 
    *   **Single Source of Truth (SSOT)**: Powered by **Room Database**.
    *   **Background Sync**: Seamlessly updates local cache from NewsAPI.
*   **Rich News Experience:** Global filters, smart search, and fully **native full-story reading** (no WebViews).
*   **Modular Architecture:** Clean separation with dedicated modules: `:app`, `:auth`, `:session`, `:security`, `:core-ui`, `:network`, and `:news`.
*   **90%+ Test Coverage:** Robust testing suite including:
    *   **Unit Tests**: MockK, Truth, and Turbine for repository and ViewModel logic.
    *   **UI Tests**: Compose Testing library for verifying user interactions.
    *   **Snapshot Tests**: **Paparazzi** for pixel-perfect UI regression testing.
    *   **Network Tests**: MockWebServer for interceptor and API verification.

## 🛠 Technical Stack
*   **Language:** Kotlin
*   **UI:** Jetpack Compose (Material 3)
*   **Database:** Room
*   **Networking:** Retrofit, OkHttp, Jsoup
*   **DI:** Dagger Hilt
*   **Testing:** JUnit 4, MockK, Google Truth, Turbine, Robolectric, Paparazzi

## 🏗 Architecture Overview
The project follows **Clean Architecture** principles:
*   **Data Layer**: Local (Room) and Remote (Retrofit) data sources.
*   **Domain Layer**: Clean interfaces and models (SSOT).
*   **Presentation Layer**: State-driven UI using ViewModels and Compose.

## 🌿 Branching Strategy
*   **`main`**: Latest stable version.
*   **`feature/offline-sync`**: Mobile-First implementation with Room.
*   **`feature/network-security`**: JWT and EC signing implementation.
*   **`feature/testing-suite`**: Full testing implementation with 90% coverage.

## 🚦 Getting Started
1. **Firebase Setup:** Place `google-services.json` in `app/`.
2. **Google Sign-In:** Configure `default_web_client_id` in `strings.xml`.
3. **Run Tests:** Use `./gradlew test` and `./gradlew verifyPaparazziDebug`.
4. **Build:** Sync Gradle and run the `:app` module.
