# API Documentation - Auth, Employee, Customer

## Base URL
`http://localhost:8080`

## Auth

### POST /api/auth/register
Đăng ký tài khoản mới (mặc định role CUSTOMER).

**Body:**
```json
{
  "tenDangNhap": "string",
  "matKhau": "string (min 6 ky tu)",
  "email": "string",
  "soDienThoai": "string",
  "hoTen": "string"
}
```

**Response 201:**
```json
{
  "token": "string",
  "tenDangNhap": "string",
  "vaiTro": "CUSTOMER"
}
```

### POST /api/auth/login
**Body:** `{ "tenDangNhap": "string", "matKhau": "string" }`
**Response 200:** giống register response.

## Employee (yêu cầu role ADMIN)

| Method | Endpoint | Mô tả |
|---|---|---|
| GET | /api/admin/employees | Danh sách (phân trang) |
| GET | /api/admin/employees/{id} | Chi tiết |
| POST | /api/admin/employees | Tạo mới |
| PUT | /api/admin/employees/{id} | Cập nhật |
| DELETE | /api/admin/employees/{id} | Xóa mềm |

## Customer (role ADMIN/EMPLOYEE, xóa chỉ ADMIN)

| Method | Endpoint | Mô tả |
|---|---|---|
| GET | /api/employee/customers?tuKhoa= | Danh sách/Tìm kiếm |
| GET | /api/employee/customers/{id} | Chi tiết |
| POST | /api/employee/customers | Tạo mới |
| PUT | /api/employee/customers/{id} | Cập nhật |
| DELETE | /api/employee/customers/{id} | Xóa mềm (chỉ ADMIN) |

## Mã lỗi chung
| Code | Ý nghĩa |
|---|---|
| 400 | Dữ liệu không hợp lệ (validation) |
| 401 | Sai thông tin đăng nhập |
| 403 | Không đủ quyền |
| 404 | Không tìm thấy resource |
| 409 | Trùng dữ liệu (username...) |