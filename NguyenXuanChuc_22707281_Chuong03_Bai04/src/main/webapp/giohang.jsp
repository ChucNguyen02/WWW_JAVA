<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <title>Giỏ hàng</title>
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
  <h2 class="text-center mb-4">🛒 Giỏ Hàng</h2>

  <c:if test="${empty cart.items}">
    <div class="alert alert-warning text-center">
      🛍 Giỏ hàng đang trống.
    </div>
    <div class="text-center">
      <a href="${pageContext.request.contextPath}/books" class="btn btn-primary">⬅ Tiếp tục mua sắm</a>
    </div>
  </c:if>

  <c:if test="${not empty cart.items}">
    <div class="table-responsive">
      <table class="table table-bordered table-striped text-center align-middle">
        <thead class="table-dark">
        <tr>
          <th>Sách</th>
          <th>Số lượng</th>
          <th>Giá</th>
          <th>Tạm tính</th>
          <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${cart.items}">
          <tr>
            <td class="text-start">
              <strong>${item.book.title}</strong><br>
              <small class="text-muted">Tác giả: ${item.book.author}</small>
            </td>
            <td style="width:150px;">
              <form action="${pageContext.request.contextPath}/cart" method="post" class="d-flex">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="bookId" value="${item.book.id}">
                <input type="number" name="quantity" value="${item.quantity}" min="0" class="form-control me-2">
                <button type="submit" class="btn btn-sm btn-outline-primary">✔</button>
              </form>
            </td>
            <td>${item.book.price} đ</td>
            <td>${item.subtotal} đ</td>
            <td>
              <form action="${pageContext.request.contextPath}/cart" method="post">
                <input type="hidden" name="action" value="remove">
                <input type="hidden" name="bookId" value="${item.book.id}">
                <button type="submit" class="btn btn-sm btn-outline-danger">🗑 Xóa</button>
              </form>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>

    <div class="text-end fs-5 fw-bold">
      Tổng cộng: <span class="text-danger">${cart.total} đ</span>
    </div>

    <div class="d-flex justify-content-between mt-3">
      <form action="${pageContext.request.contextPath}/cart" method="post">
        <input type="hidden" name="action" value="clear">
        <button type="submit" class="btn btn-outline-danger">Xóa hết giỏ hàng</button>
      </form>

      <a href="${pageContext.request.contextPath}/thanhtoan.jsp" class="btn btn-success">
        💳 Thanh toán
      </a>
    </div>

    <div class="mt-3">
      <a href="${pageContext.request.contextPath}/books" class="btn btn-outline-secondary">⬅ Tiếp tục mua</a>
    </div>
  </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
