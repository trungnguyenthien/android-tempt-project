#!/bin/bash
# Tự động dừng nếu có lỗi
set -e

KEYSTORE_NAME="upload-key.keystore"
ALIAS="upload"

echo "=== Tạo Keystore Ký số (Generating Upload Key Store) ==="
echo "File keystore sẽ được tạo: $KEYSTORE_NAME"
echo "Alias của khóa: $ALIAS"
echo "----------------------------------------------------------"
echo "Vui lòng nhập mật khẩu và các thông tin định danh của bạn."
echo "⚠️  QUAN TRỌNG: Hãy ghi nhớ mật khẩu Keystore này để dùng khi ký file AAB!"
echo "----------------------------------------------------------"

# Chạy keytool để tạo khóa
keytool -genkey -v \
  -keystore "$KEYSTORE_NAME" \
  -alias "$ALIAS" \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000

echo "----------------------------------------------------------"
echo "🎉 Thành công! File keystore phát hành đã được tạo tại:"
echo "📍 $(pwd)/$KEYSTORE_NAME"
echo "=========================================================="
