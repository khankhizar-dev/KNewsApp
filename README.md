# KNewsApp

A modern, modularized Android news application built with **Jetpack Compose** and **Firebase**. This project demonstrates industry-standard best practices, focusing on a clean architecture, robust security, and an offline-first user experience with 90%+ test coverage and performance benchmarking.

## 🚀 Key Features
*   **Authentication:** Fully integrated Firebase Auth supporting both Email/Password and Google Sign-In via Credential Manager.
*   **Persistent Sessions:** Smart session management that keeps users logged in across app restarts.
*   **Mobile First & Offline Sync:** 
    *   **Single Source of Truth (SSOT)**: Powered by **Room Database**.
    *   **Background Sync**: Seamlessly updates local cache from NewsAPI.
*   **Rich News Experience:** Global filters, smart search, and fully **native full-story reading** (no WebViews).
*   **Modular Architecture:** Clean separation with dedicated modules: `:app`, `:auth`, `:session`, `:security`, `:core-ui`, `:network`, `:news`, and `:benchmark`.
*   **Performance Optimization**:
    *   **Baseline Profiles**: Automatically generated to improve app startup and frame performance.
    *   **Macrobenchmarking**: Dedicated `:benchmark` module to measure cold/warm startup times.
*   **90%+ Test Coverage:** Robust testing suite including Unit, UI, and Snapshot tests.
*   **Static Code Analysis**: 
    *   **KtLint**: Automated Kotlin linting and formatting.
    *   **Detekt**: Advanced static code analysis for identifying code smells and complexity.

## 🛠 Technical Stack
*   **Language:** Kotlin
*   **UI:** Jetpack Compose (Material 3)
*   **Database:** Room
*   **Networking:** Retrofit, OkHttp, Jsoup
*   **DI:** Dagger Hilt
*   **Benchmarking**: Macrobenchmark, Baseline Profiles
*   **Static Analysis**: KtLint, Detekt
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
*   **`feature/testing-suite`**: Full testing implementation.
*   **`feature/performance-benchmarking`**: Startup time improvements and Baseline Profiles.

## 🚦 Getting Started
1. **Firebase Setup:** Place `google-services.json` in `app/`.
2. **Google Sign-In:** Configure `default_web_client_id` in `strings.xml`.
3. **Run Tests:** Use `./gradlew test` and `./gradlew verifyPaparazziDebug`.
4. **Static Analysis:** Run `./gradlew ktlintCheck detekt`.
    *   **Automated Enforcement**: Run `./gradlew installGitHooks` to automatically run static analysis on every `git commit`.
5. **Generate Baseline Profile:** Run `./gradlew :app:generateBaselineProfile`.
5. **Run Benchmarks:** Run the tests in the `:benchmark` module on a physical device.
6. **Build:** Sync Gradle and run the `:app` module.
