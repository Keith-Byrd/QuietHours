# QuietHours



Android utility app that automatically enables Do Not Disturb during configurable quiet hours while still allowing phone calls.

## Features

- Automatically enables and disables Android Do Not Disturb mode during configurable quiet hours
- Preserves incoming phone call notifications while suppressing text and application notifications
- Boot persistence using BroadcastReceiver to restore scheduling behavior after device restart
- Lightweight native Android implementation written in Kotlin
- Real-time enable/disable state management from the application UI
- Android system service integration using NotificationManager and notification policy APIs
- Gradle-based project structure with Git version control and reproducible builds
- Designed and tested on physical Android devices and Android Emulator environments

## Technical Highlights

- Kotlin
- Android SDK
- BroadcastReceiver
- NotificationManager
- AlarmManager foundation
- Gradle Kotlin DSL
- Android permissions handling
- Git / GitHub workflow

## Engineering Goals

This project was developed to solve a real-world usability problem while demonstrating:

- Android application architecture
- Background scheduling concepts
- System-level Android API integration
- Persistent application behavior across device reboot
- Source control and iterative development practices
- Mobile UI and state management fundamentals

## Screenshots

<table>
  <tr>
    <td style="text-align:center; vertical-align:top;">
      <img src="./screenshots/Disabled.png" width="180" alt="Disabled state">
      <br>
      Disabled State
    </td>

    <td style="text-align:center; vertical-align:top;">
      <img src="./screenshots/Enabled.png" width="180" alt="Enabled state">
      <br>
      Enabled State
    </td>
  </tr>

  <tr>
    <td style="text-align:center; vertical-align:top;">
      <img src="./screenshots/QH_Off.png" width="180" alt="QuietHours off">
      <br>
      QuietHours Off
    </td>

    <td style="text-align:center; vertical-align:top;">
      <img src="./screenshots/QH_On.png" width="180" alt="QuietHours on">
      <br>
      QuietHours On
    </td>
  </tr>
</table>

## Purpose

This project was created to solve a real-world usability problem while demonstrating Android development, scheduling, background services, and system-level notification control.

## Technologies

- Kotlin
- Android SDK
- AlarmManager
- BroadcastReceiver
- NotificationManager
- Gradle

## Current Status

Working prototype with:
- DND control
- boot persistence
- scheduling foundation

## Future Improvements

- Configurable scheduling UI
- Material Design polish
- Exact alarm scheduling
- Multiple quiet-hour profiles
- Export/import settings

## Build Instructions

1. Clone repository
2. Open in Android Studio
3. Sync Gradle
4. Run on Android device
5. Grant Do Not Disturb permissions

## Permissions Used

- ACCESS_NOTIFICATION_POLICY
- RECEIVE_BOOT_COMPLETED

## Author

Keith Byrd