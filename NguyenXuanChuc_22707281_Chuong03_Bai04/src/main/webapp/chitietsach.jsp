<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Chi tiết sách</title></head>
<body>
<h2>Chi tiết Sách</h2>
<c:if test="${not empty book}">
    <p>Tiêu đề: ${book.title}</p>
    <p>Tác giả: ${book.author}</p>
    <p>Giá: ${book.price} đ</p>
    <img src="images/${book.imgURL}" width="200"><br>
    <p>${book.description}</p>
    <form action="cart" method="post">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="id" value="${book.id}">
        <input type="submit" value="Thêm vào giỏ">
    </form>
</c:if>
<p><a href="books">Quay lại danh sách</a></p>
</body>
</html>
