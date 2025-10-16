# Debugging Guide for iStudy Android App

## Issue: "Executing... Execution finished" with no app launch

This indicates the app build/run process completed but the app didn't start. Here's how to troubleshoot:

## Step 1: Check Run Configuration
Based on memory requirements, ensure your Android Studio run configuration has:
- **Module**: iStudy.app
- **Launch Activity**: com.seecolab.istudy.MainActivity
- **Android Studio**: Hedgehog (2023.1.1) or later
- **Gradle**: 8.2
- **Android Gradle Plugin**: 8.1.4
- **Kotlin**: 1.9.10

## Step 2: Verify Device Configuration
According to memory, you need:
- **Physical Device**: Android 7.0 (API 24) or higher with USB debugging enabled
- **OR AVD**: API 24 or higher (recommended API 34), x86_64/arm64-v8a, 4GB RAM min, 16GB storage min

## Step 3: Check Android Studio Logcat
1. Open Android Studio
2. Go to View → Tool Windows → Logcat
3. Look for error messages during app launch
4. Filter by your app package: `com.seecolab.istudy`

## Step 4: Manual Verification Steps

### Check Build Success:
```bash
cd "d:\Working\Seecolab\iStudy"
.\gradlew assembleDebug
```

### Check Device Connection:
```bash
adb devices
```

### Install APK Manually:
```bash
.\gradlew installDebug
```

### Launch App Manually:
```bash
adb shell am start -n com.seecolab.istudy/.MainActivity
```

## Step 5: Common Issues & Solutions

### 1. Missing SDK Components
- Open SDK Manager (Tools → SDK Manager)
- Ensure API 34 is installed
- Install Android SDK Build-Tools
- Install Android SDK Platform-Tools

### 2. Device Not Detected
- Enable Developer Options on device
- Enable USB Debugging
- Trust computer when prompted
- Try different USB cable/port

### 3. Gradle Issues
```bash
# Clean and rebuild
.\gradlew clean
.\gradlew build
```

### 4. Android Studio Cache Issues
- File → Invalidate Caches and Restart
- Choose "Invalidate and Restart"

## Step 6: Check App Installation
After running, check if app is installed:
- Look for "iStudy" app icon on device
- If installed but not launching, check Logcat for runtime errors

## Step 7: Alternative Run Methods

### Method 1: Use Android Studio Run Button
1. Select "app" configuration
2. Choose target device
3. Click Run (▶️)

### Method 2: Debug Mode
1. Click Debug (🐛) instead of Run
2. Set breakpoint in MainActivity.onCreate()
3. Check if breakpoint is hit

### Method 3: Command Line
```bash
# Build and install
.\gradlew installDebug

# Launch activity
adb shell am start -n com.seecolab.istudy/.MainActivity
```

## Expected Behavior
When successful, you should see:
1. "iStudy" app icon appears on device
2. App opens showing Student Registration screen
3. Logcat shows successful activity launch

## Next Steps if Still Failing
1. Share Logcat output for error analysis
2. Check "Build" tab in Android Studio for build errors
3. Verify all required SDK components are installed
4. Try creating a new empty Android project to test environment