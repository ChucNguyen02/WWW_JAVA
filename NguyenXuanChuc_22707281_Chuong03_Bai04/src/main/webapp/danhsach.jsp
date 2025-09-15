<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Danh sách sách</title></head>
<body>
<h2>Danh sách Sách</h2>
<p><a href="cart">Xem Giỏ Hàng</a></p>
<c:forEach items="${books}" var="b">
  <div style="border:1px solid #ccc;margin:10px;padding:10px;">
    <b>${b.title}</b> - ${b.author}<br>
    Giá: ${b.price} đ<br>
    <img src="images/${b.imgURL}" width="150"><br>
    <form action="cart" method="post">
      <input type="hidden" name="action" value="add">
      <input type="hidden" name="id" value="${b.id}">
      <input type="submit" value="Mua">
    </form>
    <a href="book?id=${b.id}">Xem Chi Tiết</a>
  </div>
</c:forEach>
</body>
</html>
