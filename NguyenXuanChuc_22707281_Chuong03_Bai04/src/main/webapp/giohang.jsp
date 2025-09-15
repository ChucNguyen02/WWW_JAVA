<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Giỏ hàng</title></head>
<body>
<h2>Giỏ hàng</h2>
<c:if test="${empty cart.items}">
  <p>Giỏ hàng trống</p>
</c:if>
<c:if test="${not empty cart.items}">
  <table border="1">
    <tr><th>Sách</th><th>Số lượng</th><th>Giá</th><th>Tạm tính</th><th>Hành động</th></tr>
    <c:forEach var="item" items="${cart.items}">
      <tr>
        <td>${item.book.title}</td>
        <td>
          <form action="cart" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="bookId" value="${item.book.id}">
            <input type="number" name="quantity" value="${item.quantity}" min="0">
            <input type="submit" value="Cập nhật">
          </form>
        </td>
        <td>${item.book.price}</td>
        <td>${item.subtotal}</td>
        <td>
          <form action="cart" method="post">
            <input type="hidden" name="action" value="remove">
            <input type="hidden" name="bookId" value="${item.book.id}">
            <input type="submit" value="Xóa">
          </form>
        </td>
      </tr>
    </c:forEach>
  </table>
  <p><strong>Tổng cộng:</strong> ${cart.total}</p>
  <form action="cart" method="post">
    <input type="hidden" name="action" value="clear">
    <input type="submit" value="Xóa hết giỏ hàng">
  </form>
  <p><a href="thanhtoan.jsp">Thanh toán</a></p>
</c:if>
<p><a href="books">Tiếp tục mua</a></p>
</body>
</html>
