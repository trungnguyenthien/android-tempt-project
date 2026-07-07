#!/bin/bash
# Tự động thoát nếu có lỗi xảy ra
set -e

# Định nghĩa các đường dẫn công cụ
ADB_PATH="/Users/trungnguyen/Library/Android/sdk/platform-tools/adb"
ANDROID_CLI="/Users/trungnguyen/.local/bin/android"
EMULATOR_NAME="Medium_Phone_API_36.0"

# Kiểm tra adb trong hệ thống
if command -v adb &> /dev/null; then
    ADB="adb"
elif [ -f "$ADB_PATH" ]; then
    ADB="$ADB_PATH"
else
    echo "Lỗi: Không tìm thấy adb. Vui lòng kiểm tra Android SDK."
    exit 1
fi

echo "=== 1. Biên dịch ứng dụng (Building App) ==="
./gradlew assembleDebug

echo "=== 2. Kiểm tra và khởi động Emulator ==="
# Kiểm tra xem có máy ảo nào đang chạy không
DEVICE_STATUS=$($ADB devices | grep -v "List of" | grep "device" || true)

if [ -z "$DEVICE_STATUS" ]; then
    echo "Không phát hiện máy ảo đang chạy. Tiến hành khởi động $EMULATOR_NAME..."
    if [ -f "$ANDROID_CLI" ]; then
        $ANDROID_CLI emulator start "$EMULATOR_NAME"
    else
        echo "Lỗi: Không tìm thấy Android CLI tại $ANDROID_CLI."
        exit 1
    fi
else
    echo "Phát hiện máy ảo đang hoạt động."
fi

echo "=== 3. Cài đặt ứng dụng lên máy ảo (Installing App) ==="
./gradlew installDebug

echo "=== 4. Khởi chạy ứng dụng (Launching App) ==="
$ADB shell am start -n com.example.quicknotetodo/com.example.quicknotetodo.MainActivity

echo "=== Hoàn thành! Ứng dụng đã được khởi chạy thành công. ==="
