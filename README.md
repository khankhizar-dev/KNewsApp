# KNewsApp

A modern, modularized Android news application built with **Jetpack Compose** and **Firebase**. This project demonstrates industry-standard best practices, focusing on a clean architecture, robust security, and a reactive user experience.

## 🚀 Key Features
*   **Authentication:** Fully integrated Firebase Auth supporting both Email/Password and Google Sign-In via Credential Manager.
*   **Modular Architecture:** Clean separation of concerns with dedicated modules for `:app`, `:auth`, `:session`, `:security`, `:core-ui`, and `:network`.
*   **Dependency Injection:** Powered by **Hilt** for a clean, decoupled architecture across all modules.
*   **Secure Networking:** 
    *   **Retrofit & OkHttp** integration with a centralized `:network` module.
    *   **Secure Request Signing**: Every request is crypographically signed using hardware-backed **EC (Elliptic Curve) Keys**.
    *   **JWT Management**: Automatic injection of Authorization headers using a secure Interceptor.
*   **Secure Session Management:** 
    *   **Jetpack DataStore** for reactive, persistent storage of user preferences.
    *   **Hardware-Backed Security**: Use of Android KeyStore for cryptographic operations.
*   **Inactivity Security:** Automatic session timeout that logs out the user after 3 minutes of inactivity to protect sensitive data.
*   **Modern UI:** Built entirely with Jetpack Compose using **Material 3** design principles.

## 🛠 Technical Stack
*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose (Material 3)
*   **Networking:** Retrofit, OkHttp
*   **Dependency Injection:** Dagger Hilt
*   **Asynchronous Logic:** Kotlin Coroutines & StateFlow
*   **Navigation:** Compose Navigation component
*   **Backend Services:** Firebase Authentication, Google Play Services Auth
*   **Security:** Android KeyStore (EC Keys), Jetpack DataStore, Auth0 JWT

## 🏗 Architecture Overview
The project follows a multi-module approach to ensure scalability and testability:
*   **`:app`**: The main entry point, hosting the `MainActivity` and global navigation.
*   **`:auth`**: Contains all authentication UI (Login/Sign-up) and the `AuthViewModel`.
*   **`:session`**: Manages the user session lifecycle and persists state across app restarts.
*   **`:security`**: Provides hardware-backed key management and JWT utility functions.
*   **`:core-ui`**: Centralized Design System containing theme, dimensions, and reusable UI components.
*   **`:network`**: Centralized network layer with automatic authentication and request signing logic.

## 🌿 Branching Strategy
This repository is organized into branches to showcase different development scenarios:
*   **`main`**: The latest stable version with all features integrated.
*   **`feature/modularization`**: Focused on the initial project structure and module separation.
*   **`feature/firebase-auth`**: Implementation of Email/Password and Google Sign-in.
*   **`feature/session-security`**: Advanced session management with DataStore and inactivity timeouts.
*   **`feature/core-ui`**: Centralization of the UI Design System.
*   **`feature/hilt-di`**: Dependency Injection implementation using Hilt.
*   **`feature/network-security`**: Implementation of secure networking with JWT and EC signing.

## 🚦 Getting Started
1. **Firebase Setup:**
   * Create a project in the [Firebase Console](https://console.firebase.google.com/).
   * Add an Android app with the package name `com.android.knewsapp`.
   * Download `google-services.json` and place it in the `app/` directory.
   * Enable **Email/Password** and **Google** sign-in providers in the Firebase Authentication settings.
2. **Google Sign-In:**
   * Copy your **Web Client ID** from the Firebase Console.
   * Paste it into `app/src/main/res/values/strings.xml` under `default_web_client_id`.
3. **Build:**
   * Sync the project with Gradle files and run the `:app` module.
