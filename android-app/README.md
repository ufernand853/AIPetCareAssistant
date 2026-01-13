# AI Pet Care Assistant Android ऐप

## Overview
Jetpack Compose Android app that connects to the AI Pet Care Assistant backend for pet profiles, photo analysis, reminders, and AI chat.

## Requirements
- Android Studio Hedgehog or later
- JDK 17

## Configure API Base URL
The API base URL is set in `ApiClient.kt`:
```
http://10.0.2.2:4000/api/
```
Use `10.0.2.2` for Android Emulator to reach localhost. Update this for physical devices or different environments.

## Build & Run
1. Open the `/android-app` folder in Android Studio.
2. Sync Gradle and ensure SDK 34 is installed.
3. Run the app on an emulator or device.

## Key Features (MVP)
- Registration and login with JWT.
- Pet profile list and detail screens.
- Photo analysis stub results.
- Reminders with local notifications via WorkManager.
- AI chat screen with disclaimer text.

## Permissions
- Internet access for API calls.
- Camera and media permissions for photo selection.

## Future Extensions
- Replace placeholder UI with real data-bound screens and state flows.
- Add FCM push notifications for reminders.
- Add multi-language support (Spanish translations).
- Add shareable pet health cards via Compose bitmap capture.
