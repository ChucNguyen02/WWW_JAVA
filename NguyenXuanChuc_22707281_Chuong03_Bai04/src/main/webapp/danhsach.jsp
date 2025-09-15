<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Danh sách Sách</title>
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

<div class="container mt-4">
    <h2 class="text-center mb-4">IUH STORE</h2>

    <form action="${pageContext.request.contextPath}/books" method="get" class="mb-4">
        <div class="row g-2">
            <div class="col-md-4">
                <input type="text" class="form-control" name="keyword" placeholder="🔍 Từ khóa..."
                       value="${param.keyword}">
            </div>
            <div class="col-md-3">
                <input type="number" class="form-control" name="min" placeholder="Giá tối thiểu"
                       value="${param.min}">
            </div>
            <div class="col-md-3">
                <input type="number" class="form-control" name="max" placeholder="Giá tối đa"
                       value="${param.max}">
            </div>
            <div class="col-md-2">
                <button class="btn btn-outline-primary w-100" type="submit">Tìm</button>
            </div>
        </div>
    </form>

    <div class="row">
        <c:if test="${empty books}">
            <p class="text-center text-muted">❌ Không tìm thấy sách nào.</p>
        </c:if>

        <c:forEach items="${books}" var="b">
            <div class="col-md-3 mb-4">
                <div class="card h-100 shadow-sm">
                    <img src="images/${b.imgURL}" class="card-img-top" style="height: 250px; object-fit: contain;" alt="${b.title}">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">${b.title}</h5>
                        <p class="card-text text-muted">${b.author}</p>
                        <p class="fw-bold text-danger mb-3">${b.price} đ</p>

                        <form action="${pageContext.request.contextPath}/cart" method="post" class="mt-auto">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="id" value="${b.id}">
                            <button type="submit" class="btn btn-primary w-100 mb-2">Thêm vào giỏ</button>
                        </form>

                        <a href="${pageContext.request.contextPath}/book?id=${b.id}" class="btn btn-outline-secondary w-100">Xem Chi Tiết</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
