# LocalCam Pro

A local-first Android camera/monitor foundation. It requests camera and microphone permissions visibly, keeps all planned transport local, and never records silently.

## Build

Open in Android Studio Ladybug or newer, or run `./gradlew :app:assembleDebug`. The APK is produced at `app/build/outputs/apk/debug/app-debug.apk`.

## Current implementation

Phase 1 foundation is implemented: Kotlin/Compose Material 3 project, role selection, permission request flow, camera/monitor dashboards, visible streaming/recording state, and testable contracts for camera, WebRTC, pairing, signaling, recording, and alerts. CameraX and DataStore dependencies are configured.

The WebRTC/local signaling, QR pairing, CameraX binding, MediaStore recording, Room persistence, and alert detectors are intentionally not represented as fake functionality yet; they are the next integration phase and must be completed and tested on two physical Android devices before release. The current dashboard does not claim to transmit video.

Privacy: no analytics, ads, cloud service, account, stealth capture, SMS/call access, or unrelated-device control.
