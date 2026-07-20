# Deployment Diagram - ShopBadminton

```mermaid
flowchart TB
    Browser["Web Browser"] -- HTTPS --> NextJS["Next.js :3000"]
    Mobile["Flutter App"] -- REST API --> Spring["Spring Boot :8080"]
    NextJS -- REST API --> Spring
    Spring -- JDBC --> SQL[("SQL Server :1433")]
```

## Port local

| Thành phần | Port |
|---|---|
| Backend | 8080 |
| Web | 3000 |
| SQL Server | 1433 |

⚠️ Android Emulator gọi API qua `10.0.2.2:8080`, không dùng `localhost`.