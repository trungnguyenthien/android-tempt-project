#!/bin/bash
# Tự động dừng nếu xảy ra lỗi
set -e

KEYSTORE_NAME="upload-key.keystore"
ALIAS="upload"

echo "=== 1. Đang build Android App Bundle (AAB) cho bản phát hành Release ==="
./gradlew bundleRelease

echo "=== 2. Tìm kiếm file AAB đã được tạo ==="
AAB_PATH=$(find app/build/outputs/bundle -name "*.aab" | head -n 1 || true)

if [ -z "$AAB_PATH" ]; then
    echo "❌ Lỗi: Không tìm thấy file AAB sau khi biên dịch."
    exit 1
fi

echo "🎉 Tìm thấy file AAB tại: $AAB_PATH"

echo "=== 3. Ký số file AAB (Signing AAB) ==="
if [ -f "$KEYSTORE_NAME" ]; then
    echo "Phát hiện file $KEYSTORE_NAME trong dự án."
    # Yêu cầu nhập mật khẩu bảo mật không hiển thị trên màn hình
    read -s -p "Nhập mật khẩu Keystore của bạn: " KEYSTORE_PASSWORD
    echo ""
    
    echo "Đang tiến hành ký số file AAB..."
    jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
      -keystore "$KEYSTORE_NAME" \
      -storepass "$KEYSTORE_PASSWORD" \
      "$AAB_PATH" "$ALIAS"
      
    echo "Đang xác minh chữ ký (Verifying signature)..."
    jarsigner -verify "$AAB_PATH"
    
    # Tạo tên file AAB với định dạng release-[YYMMDD-hh:mm:ss].aab
    TIMESTAMP=$(date +%y%m%d-%H%M%S)
    RELEASE_NAME="release-${TIMESTAMP}.aab"
    
    # Copy file AAB đã ký ra thư mục gốc dự án
    cp "$AAB_PATH" "$RELEASE_NAME"
    
    echo "=========================================================="
    echo "🎉 Thành công! File AAB đã được KÝ SỐ và lưu tại thư mục gốc:"
    echo "📍 $(pwd)/$RELEASE_NAME"
    echo "Sẵn sàng để upload lên Google Play Console!"
    echo "=========================================================="
else
    echo "⚠️  Cảnh báo: Không tìm thấy file khóa $KEYSTORE_NAME."
    echo "Hãy chạy script ./gen-upload-key.sh trước để tạo khóa."
    echo "File AAB chưa ký số đã được tạo tại: $AAB_PATH"
    echo "=========================================================="
fi
