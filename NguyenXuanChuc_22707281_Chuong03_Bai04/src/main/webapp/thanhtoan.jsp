<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Thanh toán</title></head>
<body>
<h2>Thông tin Thanh Toán</h2>

<c:if test="${not empty message}">
    <p style="color:green; font-weight:bold;">${message}</p>
</c:if>

<form action="checkout" method="post">
    Họ tên: <input type="text" name="name" required><br>
    Địa chỉ: <input type="text" name="address" required><br>
    Email: <input type="email" name="email" required><br>
    <input type="submit" value="Xác nhận thanh toán">
</form>
<p><a href="books">Quay lại mua sắm</a></p>
</body>
</html>
