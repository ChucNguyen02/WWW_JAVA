<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Chi tiết Sách</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/books">📚 Nhà Sách</a>
        <div class="d-flex">
            <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/cart">🛒 Xem Giỏ Hàng</a>
        </div>
    </div>
</nav>

<div class="container mt-5">
    <c:if test="${not empty book}">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow-lg">
                    <div class="row g-0">

                        <div class="col-md-5 d-flex align-items-center justify-content-center p-3">
                            <img src="images/${book.imgURL}" class="img-fluid rounded" alt="${book.title}"
                                 style="max-height: 300px; object-fit: contain;">
                        </div>

                        <div class="col-md-7">
                            <div class="card-body">
                                <h3 class="card-title mb-3">${book.title}</h3>
                                <h5 class="text-muted">✍️ Tác giả: ${book.author}</h5>
                                <h4 class="text-danger fw-bold mt-3">${book.price} đ</h4>
                                <p class="mt-3">${book.description}</p>

                                <form action="${pageContext.request.contextPath}/cart" method="post" class="mt-3">
                                    <input type="hidden" name="action" value="add">
                                    <input type="hidden" name="id" value="${book.id}">
                                    <button type="submit" class="btn btn-success w-100">
                                        ➕ Thêm vào giỏ hàng
                                    </button>
                                </form>

                                <a href="${pageContext.request.contextPath}/books" class="btn btn-outline-secondary w-100 mt-2">
                                    ⬅ Quay lại danh sách
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${empty book}">
        <div class="alert alert-warning text-center">
            ❌ Không tìm thấy sách.
            <br>
            <a href="${pageContext.request.contextPath}/books" class="btn btn-outline-primary mt-3">
                Quay lại danh sách
            </a>
        </div>
    </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
