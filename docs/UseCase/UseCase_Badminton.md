# Use Case Diagram - ShopBadminton

**Phiên bản:** 1.0
**Ngày:** 18/07/2026
**Người thực hiện:** thnquang0203

---
## 1. Mô tả kịch bản chi tiết (Use Case Description)

### UC-01: Đặt sân cầu lông

| Trường             | Nội dung                                                                                              |
|--------------------|-------------------------------------------------------------------------------------------------------|
| Actor              | Khách hàng                                                                                            |
| Điều kiện trước    | Khách hàng đã đăng nhập thành công (có JWT hợp lệ)                                                    |
| Luồng chính        | 1. Khách hàng xem danh sách sân<br>2. Chọn sân, chọn ngày và khung giờ<br>3. Hệ thống kiểm tra khung giờ đã có booking active chưa<br>4. Nếu trống, khách hàng xác nhận đặt<br>5. Hệ thống tạo `CourtBooking` với trạng thái "Chờ xác nhận" |
| Luồng phụ (Extend) | Nếu khung giờ đã bị đặt → hệ thống trả lỗi 409 Conflict, yêu cầu chọn lại khung giờ khác              |
| Điều kiện sau      | Booking được lưu vào hệ thống; Nhân viên thấy booking mới trong danh sách chờ xác nhận                |
 
### UC-02: Tạo hóa đơn gộp (sản phẩm + đặt sân)

| Trường             | Nội dung                                                                             |
|--------------------|--------------------------------------------------------------------------------------|
| Actor              | Nhân viên                                                                            |
| Điều kiện trước    | Nhân viên đã đăng nhập, có quyền tạo hóa đơn                                         |
| Luồng chính        | 1. Nhân viên chọn khách hàng<br>2. Thêm dòng sản phẩm (nếu có) → hệ thống kiểm tra tồn kho đủ<br>3. Thêm dòng đặt sân đã xác nhận (nếu có)<br>4. Hệ thống tính tổng tiền<br>5. Nhân viên xác nhận tạo hóa đơn → trạng thái "Chưa thanh toán"                     |
| Luồng phụ (Extend) | Nếu tồn kho không đủ cho 1 sản phẩm → hệ thống báo lỗi, không cho thêm dòng đó       |
| Điều kiện sau      | `Bill` và các `BillDetail` được tạo; tồn kho các sản phẩm liên quan bị trừ tương ứng |

### UC-03: Nhập hàng (tự động cộng kho)

| Trường             | Nội dung                                                                                      |
|--------------------|-----------------------------------------------------------------------------------------------|
| Actor              | Nhân viên / Admin                                                                             |
| Điều kiện trước    | Đã có danh sách nhà cung cấp và sản phẩm trong hệ thống                                       |
| Luồng chính        | 1. Chọn nhà cung cấp<br>2. Thêm danh sách sản phẩm nhập kèm số lượng, đơn giá nhập<br>3. Xác nhận tạo phiếu nhập<br>4. Hệ thống tự động cộng số lượng vào `Inventory` tương ứng                                    |
| Luồng phụ (Extend) | Không có                                                                                      |
| Điều kiện sau      | `PurchaseOrder`, `PurchaseDetail` được lưu; `Inventory` được cập nhật tăng đúng số lượng nhập |

### UC-04: Đăng nhập & Phân quyền JWT
 
| Trường             | Nội dung                                                                                  |
|--------------------|-------------------------------------------------------------------------------------------|
| Actor              | Admin / Nhân viên / Khách hàng                                                            |
| Điều kiện trước    | Tài khoản đã tồn tại trong hệ thống                                                       |
| Luồng chính        | 1. Người dùng nhập username/mật khẩu<br>2. Hệ thống xác thực (so khớp mật khẩu đã hash BCrypt)<br>3. Hệ thống sinh JWT chứa thông tin `userId` và `role`<br>4. Trả JWT về client, client lưu token để gửi kèm các request sau                         |
| Luồng phụ (Extend) | Sai mật khẩu quá số lần quy định → tạm khóa đăng nhập (tùy chọn nâng cao, không bắt buộc) |
| Điều kiện sau      | Client có JWT hợp lệ, có thể gọi các API được bảo vệ theo đúng Role                       |

### UC-05: Thanh toán một phần hóa đơn

| Trường             | Nội dung                                                                    |
|--------------------|-----------------------------------------------------------------------------|
| Actor              | Nhân viên                                                                   |
| Điều kiện trước    | Hóa đơn đã tồn tại, trạng thái "Chưa thanh toán" hoặc "Thanh toán một phần" |
| Luồng chính        | 1. Nhân viên chọn hóa đơn cần thanh toán<br>2. Nhập số tiền khách trả (có thể nhỏ hơn tổng hóa đơn)<br>3. Hệ thống tạo bản ghi `Payment` gắn với `Bill`<br>4. Hệ thống tính tổng đã thanh toán, cập nhật trạng thái hóa đơn             |
| Luồng phụ (Extend) | Nếu tổng đã thanh toán = tổng hóa đơn → trạng thái chuyển "Đã thanh toán đủ"; nếu nhỏ hơn → giữ "Thanh toán một phần"                                      |
| Điều kiện sau      | `Payment` được lưu; trạng thái `Bill` phản ánh đúng số tiền đã thu           |

---

## 2. Ghi chú phiên bản

| Phiên bản | Ngày       | Nội dung thay đổi                                                                             |
|-----------|------------|-----------------------------------------------------------------------------------------------|
| 1.0       | 18/07/2026 | Khởi tạo Use Case Diagram tổng quát, chi tiết theo nhóm, và mô tả kịch bản 5 use case cốt lõi |