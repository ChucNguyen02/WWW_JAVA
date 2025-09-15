<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Thanh toán</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/books">📚 Nhà Sách</a>
        <div class="d-flex">
            <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/cart">🛒 Giỏ Hàng</a>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <h2 class="text-center mb-4">💳 Thông tin Thanh Toán</h2>

    <c:if test="${not empty message}">
        <div class="alert alert-success text-center">${message}</div>
    </c:if>

    <c:if test="${not empty cart.items}">
        <div class="table-responsive mb-4">
            <table class="table table-bordered text-center">
                <thead class="table-dark">
                <tr>
                    <th>Sách</th>
                    <th>Số lượng</th>
                    <th>Giá</th>
                    <th>Tạm tính</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${cart.items}">
                    <tr>
                        <td class="text-start">
                            <strong>${item.book.title}</strong><br>
                            <small class="text-muted">${item.book.author}</small>
                        </td>
                        <td>${item.quantity}</td>
                        <td>${item.book.price} đ</td>
                        <td>${item.subtotal} đ</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="text-end fs-5 fw-bold mb-4">
            Tổng cộng: <span class="text-danger">${cart.total} đ</span>
        </div>


        <div class="card shadow-sm p-4">
            <form action="checkout" method="post">
                <div class="mb-3">
                    <label class="form-label">Họ tên</label>
                    <input type="text" name="name" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Địa chỉ</label>
                    <input type="text" name="address" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" name="email" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-success w-100">✅ Xác nhận Thanh toán</button>
            </form>
        </div>
    </c:if>

    <c:if test="${empty cart.items}">
        <div class="alert alert-warning text-center">
            🛒 Giỏ hàng đang trống. Hãy thêm sách trước khi thanh toán.
        </div>
        <div class="text-center">
            <a href="${pageContext.request.contextPath}/books" class="btn btn-primary">⬅ Quay lại mua sắm</a>
        </div>
    </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
