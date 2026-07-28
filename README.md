# KNewsApp

[![Android CI](https://github.com/khankhizar-dev/KNewsApp/actions/workflows/android.yml/badge.svg)](https://github.com/khankhizar-dev/KNewsApp/actions/workflows/android.yml)

A modern, modularized Android news application built with **Jetpack Compose** and **Firebase**. This project demonstrates industry-standard best practices, focusing on a clean architecture, robust security, and an offline-first user experience with 90%+ test coverage and performance benchmarking.

## 🚀 Key Features
*   **Authentication:** Fully integrated Firebase Auth supporting both Email/Password and Google Sign-In via Credential Manager.
*   **Persistent Sessions:** Smart session management using **DataStore** that keeps users logged in across app restarts.
*   **Mobile First & Offline Sync:** 
    *   **Single Source of Truth (SSOT)**: Powered by **Room Database**.
    *   **Background Sync**: Seamlessly updates local cache from NewsAPI.
*   **Rich News Experience:** 
    *   Global filters (Country, Category, Language).
    *   Smart search functionality.
    *   **Shared Element Transitions**: Smooth hero animations between list and detail views.
    *   Fully **native full-story reading** (no WebViews).
*   **Modular Architecture:** 8-module clean architecture: `:app`, `:auth`, `:session`, `:security`, `:core-ui`, `:network`, `:news`, and `:benchmark`.
*   **Performance Optimization**:
    *   **Baseline Profiles**: Automatically generated to improve app startup and frame performance.
    *   **Macrobenchmarking**: Dedicated module to measure cold/warm startup times.
*   **90%+ Test Coverage:** Robust testing suite including Unit tests, Robolectric, and **Paparazzi Snapshot tests**.
*   **Static Code Analysis**: Enforced via **KtLint** and **Detekt** with automatic pre-commit hooks.
*   **Automated CI/CD**: Fully integrated **GitHub Actions** pipeline.

## 🛠 Technical Stack
*   **Language:** Kotlin
*   **UI:** Jetpack Compose (Material 3)
*   **Database:** Room (v3 with Auto-Migrations)
*   **Networking:** Retrofit, OkHttp, Jsoup (Native Content Extraction)
*   **DI:** Dagger Hilt
*   **Benchmarking**: Macrobenchmark, Baseline Profiles
*   **Static Analysis**: KtLint, Detekt
*   **Testing:** JUnit 4, MockK, Google Truth, Turbine, Robolectric, Paparazzi
*   **CI/CD**: GitHub Actions

## 🏗 Architecture Overview
The project follows **Clean Architecture** principles:
*   **Data Layer**: Local (Room) and Remote (Retrofit) data sources.
*   **Domain Layer**: Clean interfaces and models (SSOT).
*   **Presentation Layer**: State-driven UI using ViewModels and Compose.

## 🚦 Getting Started
1. **Firebase Setup:** Place your `google-services.json` in the `app/` directory.
2. **Google Sign-In:** Replace `YOUR_WEB_CLIENT_ID_HERE` in `res/values/strings.xml` with your Firebase Web Client ID.
3. **Run Tests:** Use `./gradlew test` and `./gradlew verifyPaparazziDebug`.
4. **Static Analysis:** Run `./gradlew ktlintCheck detekt`.
    *   **Automated Enforcement**: Run `./gradlew installGitHooks` to automatically run static analysis on every `git commit`.
5. **Generate Baseline Profile:** Run `./gradlew :app:generateBaselineProfile`.
6. **Run Benchmarks:** Run the tests in the `:benchmark` module on a physical device.

## 🔧 Windows Troubleshooting
If you encounter `ClassNotFoundException: VS` or `java.io.IOException: The pipe is being closed` during tests on Windows, this is often due to unquoted spaces in your system `PATH` (e.g., `Microsoft VS Code`).
*   **Workaround Included**: The project includes a `build.gradle.kts` workaround that cleans the `PATH` during test execution. 
*   **Manual Fix**: Ensure your `JAVA_HOME` is correct and avoid having directories with spaces in your system path, or ensure they are properly quoted in Windows Environment Settings.

## 🌿 Branching Strategy
*   **`main`**: Latest stable version.
*   **`feature/offline-sync`**: Mobile-First implementation with Room.
*   **`feature/network-security`**: JWT and EC signing implementation.
*   **`feature/testing-suite`**: Full testing implementation.
*   **`feature/performance-benchmarking`**: Startup time improvements and Baseline Profiles.
