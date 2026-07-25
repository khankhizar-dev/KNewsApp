# KNewsApp

A modern, modularized Android news application built with **Jetpack Compose** and **Firebase**. This project demonstrates industry-standard best practices, focusing on a clean architecture, robust security, and a reactive user experience.

## 🚀 Key Features
*   **Authentication:** Fully integrated Firebase Auth supporting both Email/Password and Google Sign-In via Credential Manager.
*   **Persistent Sessions:** Smart session management that keeps users logged in across app restarts, with automatic token refreshing handled by Firebase.
*   **Modular Architecture:** Clean separation of concerns with dedicated modules for `:app`, `:auth`, `:session`, `:security`, `:core-ui`, `:network`, and `:news`.
*   **Dependency Injection:** Powered by **Hilt** for a clean, decoupled architecture across all modules.
*   **Secure Networking:** 
    *   **Retrofit & OkHttp** integration with a centralized `:network` module.
    *   **Secure Request Signing**: Every request is crypographically signed using hardware-backed **EC (Elliptic Curve) Keys**.
    *   **JWT Management**: Automatic injection of Authorization headers using a secure Interceptor.
*   **Rich News Experience:**
    *   **Global Filters**: Filter news by Country, Category, and Language.
    *   **Smart Search**: keyword-based global search across the latest news.
    *   **Sorting**: Organize news by publication date or title.
    *   **Native Reading**: Immersive native detail view with background HTML scraping (Jsoup) to display full stories without WebViews.
*   **Secure Session Management:** 
    *   **Jetpack DataStore** for reactive, persistent storage of user preferences.
    *   **Hardware-Backed Security**: Use of Android KeyStore for cryptographic operations and device-bound security.
*   **Modern UI:** Built entirely with Jetpack Compose using **Material 3** design principles and **Coil** for image loading.

## 🛠 Technical Stack
*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose (Material 3)
*   **Networking:** Retrofit, OkHttp, Jsoup (HTML Scraping)
*   **Dependency Injection:** Dagger Hilt
*   **Asynchronous Logic:** Kotlin Coroutines & StateFlow
*   **Navigation:** Compose Navigation component
*   **Backend Services:** Firebase Authentication, Google Play Services Auth
*   **Security:** Android KeyStore (EC Keys), Jetpack DataStore, Auth0 JWT

## 🏗 Architecture Overview
The project follows a multi-module approach to ensure scalability and testability:
*   **`:app`**: The main entry point, hosting the `MainActivity` and global navigation.
*   **`:auth`**: Contains all authentication UI (Login/Sign-up) and session-aware ViewModels.
*   **`:session`**: Manages the user session lifecycle and persists state reactively.
*   **`:security`**: Provides hardware-backed key management and cryptographic utilities.
*   **`:core-ui`**: Centralized Design System containing theme, dimensions, and reusable UI components.
*   **`:network`**: Centralized network layer with automatic authentication and request signing logic.
*   **`:news`**: Encapsulates all news-related data (Remote API), domain logic, and feature-rich UI.

## 🌿 Branching Strategy
*   **`main`**: The latest stable version with all features integrated.
*   **`feature/modularization`**: Focused on the initial project structure and module separation.
*   **`feature/firebase-auth`**: Implementation of Email/Password and Google Sign-in.
*   **`feature/session-security`**: Advanced session management with DataStore and persistent sessions.
*   **`feature/core-ui`**: Centralization of the UI Design System.
*   **`feature/hilt-di`**: Dependency Injection implementation using Hilt.
*   **`feature/network-security`**: Implementation of secure networking with JWT and EC signing.
*   **`feature/news-feature`**: Full implementation of the native news feed, filtering, and detail view.

## 🚦 Getting Started
1. **Firebase Setup:**
   * Create a project in the [Firebase Console](https://console.firebase.google.com/).
   * Add an Android app with the package name `com.android.knewsapp`.
   * Download `google-services.json` and place it in the `app/` directory.
   * Enable **Email/Password** and **Google** sign-in providers.
2. **Google Sign-In:**
   * Copy your **Web Client ID** from the Firebase Console.
   * Paste it into `app/src/main/res/values/strings.xml` under `default_web_client_id`.
3. **Build:**
   * Sync the project with Gradle files and run the `:app` module.
