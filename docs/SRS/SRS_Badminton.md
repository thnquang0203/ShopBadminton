# SRS - Software Requirements Specification
# Dự án: ShopBadminton - Hệ thống quản lý cửa hàng cầu lông

**Phiên bản:** 1.0
**Ngày:** 17/07/2026
**Người thực hiện:** thnquang0203

---

## 1. Giới thiệu

### 1.1 Mục đích tài liệu

Tài liệu này mô tả chi tiết các yêu cầu chức năng và phi chức năng của hệ thống **ShopBadminton** — làm căn cứ thiết kế cơ sở dữ liệu, xây dựng API, phát triển giao diện Web và Mobile App, cũng như kiểm thử hệ thống trong suốt quá trình phát triển dự án.

### 1.2 Phạm vi dự án

ShopBadminton là hệ thống quản lý toàn diện cho một cửa hàng/trung tâm cầu lông, phục vụ đồng thời hai nhu cầu:

1. **Kinh doanh dịch vụ**: cho thuê sân cầu lông theo giờ, quản lý lịch đặt sân.
2. **Kinh doanh sản phẩm**: mua bán vợt, phụ kiện cầu lông.

Hệ thống gồm 3 thành phần triển khai:

- **Backend API** (Spring Boot + SQL Server) — xử lý nghiệp vụ, cung cấp REST API dùng chung.
- **Web Admin** (Next.js) — dành cho Admin/Nhân viên quản lý vận hành.
- **Mobile App** (Flutter) — dành cho Khách hàng đặt sân, xem sản phẩm, xem hóa đơn cá nhân.

### 1.3 Định nghĩa, từ viết tắt

| Từ viết tắt | Ý nghĩa                                            |
|-------------|----------------------------------------------------|
| SRS         | Software Requirements Specification                |
| API         | Application Programming Interface                  |
| JWT         | JSON Web Token - cơ chế xác thực người dùng        |
| CRUD        | Create, Read, Update, Delete                       |
| DTO         | Data Transfer Object                               |
| FR          | Functional Requirement (Yêu cầu chức năng)         |
| NFR         | Non-Functional Requirement (Yêu cầu phi chức năng) |
| ERD         | Entity Relationship Diagram                        |

---

## 2. Actor (Vai trò người dùng)

| Actor                    | Mô tả                                                                                                                           | Kênh truy cập  |
|--------------------------|---------------------------------------------------------------------------------------------------------------------------------|----------------|
| **Admin**                | Toàn quyền hệ thống. Quản lý nhân viên, sản phẩm, kho, nhà cung cấp, xem thống kê doanh thu toàn hệ thống, cấu hình phân quyền. | Web            |
| **Nhân viên (Employee)** | Thực hiện nghiệp vụ bán hàng, lập hóa đơn, xác nhận/quản lý đặt sân, nhập hàng vào kho. giới hạn quyền xem 1 số tính năng.      | Web            |
| **Khách hàng (Customer)**| Xem danh sách sân, đặt sân, xem sản phẩm, đặt mua sản phẩm, xem lịch sử đặt sân/hóa đơn của chính mình.                         | Mobile App, Web|

---

## 3. Yêu cầu chức năng (Functional Requirements)

### FR-01: Quản lý sân cầu lông
- Admin/Nhân viên thêm, sửa, xóa, xem danh sách sân cầu lông.
- Mỗi sân có: mã sân, tên sân, loại sân, giá thuê theo giờ, trạng thái (Trống/Đang sử dụng/Bảo trì).

### FR-02: Mua bán vợt cầu lông
- Khách hàng xem danh sách vợt/phụ kiện, xem chi tiết sản phẩm (giá, mô tả, hình ảnh, tồn kho).
- Nhân viên/Khách hàng có thể tạo đơn mua sản phẩm dẫn tới sinh hóa đơn.

### FR-03: Quản lý khách hàng
- Admin/Nhân viên xem, thêm, sửa, xóa thông tin khách hàng.
- Khách hàng tự đăng ký tài khoản, cập nhật thông tin cá nhân.
- Tìm kiếm khách hàng theo tên/số điện thoại.

### FR-04: Quản lý nhân viên
- Chỉ Admin có quyền thêm, sửa, xóa, xem danh sách nhân viên.
- Mỗi nhân viên gắn với 1 tài khoản đăng nhập (User) và 1 Role.

### FR-05: Quản lý hóa đơn
- Nhân viên lập hóa đơn cho khách hàng (mua sản phẩm và/hoặc thuê sân).
- Hóa đơn có thể gồm nhiều dòng chi tiết (sản phẩm + booking sân) trong cùng 1 hóa đơn.
- Xem lại lịch sử hóa đơn, chi tiết từng hóa đơn.

### FR-06: Quản lý nhập hàng
- Nhân viên/Admin tạo phiếu nhập hàng từ nhà cung cấp.
- Mỗi phiếu nhập gồm nhiều sản phẩm, số lượng, đơn giá nhập.
- Sau khi nhập, hệ thống tự động cộng số lượng vào tồn kho.

### FR-07: Quản lý kho
- Theo dõi tồn kho theo từng sản phẩm.
- Tồn kho tự động giảm khi bán hàng, tự động tăng khi nhập hàng.
- Cảnh báo khi tồn kho dưới ngưỡng tối thiểu.

### FR-08: Thống kê doanh thu
- Chỉ Admin xem được thống kê doanh thu theo ngày/tháng/năm.
- Thống kê doanh thu tách riêng: doanh thu bán sản phẩm và doanh thu cho thuê sân.
- Thống kê sản phẩm bán chạy, tỷ lệ lấp đầy sân theo khung giờ.

### FR-09: Dashboard
- Trang tổng quan hiển thị: doanh thu hôm nay, số đơn hàng, số lượt đặt sân, sản phẩm sắp hết hàng.
- Biểu đồ trực quan (doanh thu theo tuần/tháng).

### FR-10: Phân quyền tài khoản
- Hệ thống có 3 nhóm quyền: Admin, Nhân viên, Khách hàng (theo bảng Actor ở mục 2).
- Mỗi API endpoint được bảo vệ theo Role tương ứng (dùng JWT + Spring Security).

### FR-11: Quản lý đặt sân
- Khách hàng chọn sân, chọn ngày giờ, tạo yêu cầu đặt sân.
- Hệ thống kiểm tra không cho đặt trùng khung giờ đã có người đặt.
- Nhân viên xác nhận/hủy đặt sân.

### FR-12: Quản lý lịch đặt sân
- Xem lịch đặt sân dạng calendar theo ngày/theo sân.
- Lọc lịch theo trạng thái (Đã xác nhận/Chờ xác nhận/Đã hủy).

### FR-13: Thanh toán
- Hỗ trợ ghi nhận thanh toán: tiền mặt, chuyển khoản, QR code.
- Một hóa đơn có thể thanh toán một lần hoặc nhiều lần (thanh toán một phần).
- Cập nhật trạng thái hóa đơn: Chưa thanh toán/Đã thanh toán một phần/Đã thanh toán đủ.

### FR-14: Quản lý sản phẩm
- CRUD sản phẩm: tên, mô tả, giá bán, danh mục, thương hiệu, hình ảnh.
- Phân loại theo Category (loại vợt, phụ kiện...) và Brand (thương hiệu).
- Tìm kiếm, lọc sản phẩm theo danh mục/thương hiệu/khoảng giá.

### FR-15: Upload hình ảnh sản phẩm
- Cho phép upload nhiều hình ảnh cho 1 sản phẩm.
- Hỗ trợ xóa/thay thế hình ảnh.
- Giới hạn định dạng (jpg, png) và dung lượng file.

---

## 4. Yêu cầu phi chức năng (Non-Functional Requirements)

### NFR-01: Hiệu năng
- Thời gian phản hồi API cho truy vấn thông thường (không phải thống kê phức tạp) dưới 500ms.
- Hỗ trợ phân trang (pagination) cho mọi API trả về danh sách để tránh tải dữ liệu quá lớn.

### NFR-02: Bảo mật
- Xác thực người dùng bằng JWT, token có thời hạn (expiry).
- Mật khẩu người dùng được mã hóa bằng BCrypt trước khi lưu vào database.
- Mọi API (trừ đăng nhập/đăng ký/xem công khai) đều yêu cầu xác thực JWT hợp lệ.
- Phân quyền chi tiết theo Role ở tầng API (không chỉ ẩn/hiện ở giao diện).

### NFR-03: Khả năng mở rộng
- Kiến trúc RESTful API dùng chung cho cả Web và Mobile, không phụ thuộc vào 1 client cụ thể.
- Áp dụng Clean Architecture, Repository Pattern, Service Layer để dễ mở rộng thêm module trong tương lai (ví dụ: thêm module khuyến mãi, membership).

### NFR-04: Giao diện & Trải nghiệm người dùng
- Web Admin: giao diện responsive, hỗ trợ Dark Mode/Light Mode, có Sidebar điều hướng.
- Mobile App: tuân theo chuẩn Material Design, thao tác mượt, dễ sử dụng cho người dùng phổ thông.

### NFR-05: Khả năng bảo trì
- Code tuân thủ nguyên tắc SOLID, Clean Code.
- Có xử lý Exception tập trung (Global Exception Handler), logging đầy đủ cho các thao tác quan trọng (nhập hàng, thanh toán, đặt sân).

### NFR-06: Khả năng tương thích
- Backend chạy trên Java 17, Spring Boot, kết nối SQL Server.
- Web chạy trên trình duyệt hiện đại (Chrome, Edge, Firefox bản mới).
- Mobile chạy trên Android (tối thiểu Android 8.0 trở lên).

---

## 5. Ràng buộc nghiệp vụ quan trọng

Các quy tắc dưới đây **bắt buộc phải phản ánh đúng khi thiết kế Database** và logic Service ở Backend:

1. **Không được đặt trùng khung giờ sân**: một sân tại một khung giờ chỉ thuộc về đúng 1 lượt đặt đang hoạt động (trạng thái khác "Đã hủy").
2. **Hóa đơn có thể gộp nhiều loại**: một hóa đơn (`Bills`) có thể chứa vừa chi tiết sản phẩm (`BillDetails` liên kết `Products`) vừa chi tiết đặt sân (liên kết `CourtBookings`).
3. **Tồn kho luôn đồng bộ tự động**:
   - Khi tạo hóa đơn bán sản phẩm → trừ `Inventory` tương ứng.
   - Khi tạo phiếu nhập hàng (`PurchaseOrders`) → cộng `Inventory` tương ứng.
4. **Một hóa đơn có thể thanh toán nhiều lần**: tổng các `Payments` liên kết 1 `Bill` không được vượt quá tổng tiền hóa đơn.
5. **Phân quyền dữ liệu theo Actor**:
   - Khách hàng chỉ xem được dữ liệu (hóa đơn, lịch đặt sân) của chính mình.
   - Nhân viên không xem được API Thống kê doanh thu toàn hệ thống (chỉ Admin).
6. **Một sản phẩm luôn thuộc đúng 1 Category và 1 Brand** (không cho phép để trống 2 trường này khi tạo sản phẩm).
7. **Xóa mềm (Soft Delete)** áp dụng cho: `Products`, `Customers`, `Employees` — không xóa cứng khỏi database để giữ toàn vẹn lịch sử hóa đơn/đặt sân đã phát sinh.

---

## 6. Danh sách thực thể dữ liệu sơ bộ (Thiết kế Database)

`Users`, `Roles`, `Employees`, `Customers`, `Products`, `ProductImages`, `Categories`, `Brands`, `Suppliers`, `PurchaseOrders`, `PurchaseDetails`, `Inventory`, `BadmintonCourts`, `CourtBookings`, `Bills`, `BillDetails`, `Payments`.

---

## 7. Ghi chú phiên bản

| Phiên bản | Ngày       | Nội dung thay đổi             |
|-----------|------------|-------------------------------|
| 1.0       | 17/07/2026 | Khởi tạo tài liệu SRS ban đầu |