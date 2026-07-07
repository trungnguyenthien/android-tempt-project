# QuickNote & Todo Android App

Một ứng dụng ghi chú nhanh và quản lý danh sách việc cần làm (Todo) cá nhân hàng ngày trên nền tảng Android. Ứng dụng hoạt động hoàn toàn offline (ngoại tuyến) với cơ sở dữ liệu local Room Database, được xây dựng hoàn toàn bằng Kotlin và Jetpack Compose UI.

---

## 📱 Các tính năng chính (Main Features)

* **Lưu trữ cục bộ (Offline Local Storage):** Toàn bộ ghi chú được lưu trữ an toàn trong thiết bị thông qua Room Database (SQLite).
* **Giao diện Material 3 hiện đại:** Thiết kế gọn gàng, hỗ trợ cử chỉ và hiệu ứng trực quan sinh động.
* **Gạch ngang khi hoàn thành (Strike-through Todo):** Tích hợp Checkbox trên từng thẻ để đánh dấu hoàn thành nhanh công việc với hiệu ứng mờ và gạch ngang chữ.
* **Vuốt để xóa (Swipe to Delete):** Hỗ trợ cử chỉ vuốt thẻ sang trái để xóa nhanh ghi chú.
* **Hội thoại xác nhận thông minh (Discard Warn Dialog):** Hiển thị hộp thoại cảnh báo khi người dùng nhấn nút Quay lại (Back) mà có nội dung soạn thảo chưa được lưu.

---

## 🔍 Luồng màn hình chi tiết (Screens & UI Flow)

Ứng dụng tối giản hóa trải nghiệm người dùng với **2 màn hình chính**:

### 1. Màn hình chính: Danh sách Ghi chú & Todo (Home Screen)
* **Thanh tiêu đề (Top Bar):** Hiển thị tên ứng dụng `"QuickNote & Todo"`.
* **Danh sách dạng thẻ (Card List):**
  - Hiển thị danh sách ghi chú sắp xếp theo thời gian tạo mới nhất lên đầu.
  - Mỗi thẻ hiển thị: Tiêu đề (gạch ngang nếu hoàn thành), Đoạn nội dung ngắn (tối đa 2 dòng), và thời gian cập nhật.
* **Thao tác nhanh (Quick Actions):**
  - **Tích Checkbox:** Thay đổi trạng thái hoàn thành. Khi hoàn thành, thẻ sẽ được chuyển sang màu xám mờ và gạch ngang chữ.
  - **Vuốt trái (Swipe Left):** Thực hiện cử chỉ kéo thẻ từ phải sang trái để xóa nhanh ghi chú khỏi cơ sở dữ liệu.
  - **Chạm vào thẻ:** Mở màn hình Chi tiết để chỉnh sửa nội dung.
* **Nút thêm mới (FAB):** Một nút tròn biểu tượng `+` nổi ở góc dưới bên phải màn hình để mở giao diện viết ghi chú mới.

### 2. Màn hình Chi tiết: Thêm/Sửa ghi chú (Detail Screen)
* **Thanh công cụ soạn thảo:**
  - Nút **Quay lại (Back Arrow):** Quay về màn hình chính.
  - Nút **Lưu (Checkmark Icon):** Lưu toàn bộ thay đổi và quay lại màn hình danh sách (chỉ bật khi có nội dung tiêu đề hoặc văn bản).
* **Vùng soạn thảo:**
  - Trường nhập **Tiêu đề (Title):** Nhập tiêu đề ghi chú (1 dòng).
  - Vùng nhập **Nội dung (Content):** Ô văn bản nhiều dòng chiếm phần còn lại của màn hình giúp nhập ghi chú thoải mái.
* **Logic quay lại an toàn:** Nếu người dùng nhấn nút Quay lại trên màn hình hoặc nút Back hệ thống của điện thoại Android khi có sự thay đổi văn bản so với ban đầu chưa lưu, ứng dụng sẽ hiển thị hộp thoại cảnh báo:
  - **Lưu:** Tiến hành lưu nội dung mới rồi quay lại màn hình chính.
  - **Hủy bỏ:** Bỏ qua thay đổi và quay lại màn hình chính mà không lưu.
  - Nhấp bên ngoài hoặc nút dismiss để tiếp tục soạn thảo tiếp.

---

## 🛠️ Công nghệ sử dụng (Technology Stack)

* **Kotlin:** Ngôn ngữ phát triển ứng dụng chính thức của Android.
* **Jetpack Compose:** Framework phát triển UI khai báo hiện đại, trực quan.
* **Room Database:** Thư viện trừu tượng hóa SQLite để xử lý dữ liệu offline.
* **KSP (Kotlin Symbol Processing):** Bộ phân tích cú pháp mã nguồn giúp sinh code Room DB nhanh hơn so với KAPT truyền thống.
* **Navigation 3:** Giải pháp định tuyến và điều hướng màn hình Compose-first mới nhất của Google.
* **MVVM Architecture:** Mô hình phát triển sạch (Model - View - ViewModel) phân tách rõ ràng trách nhiệm dữ liệu và giao diện.
