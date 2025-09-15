<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Shopping Cart</title>
    <style>
        table { border-collapse: collapse; width: 80%; margin: 20px auto; }
        th, td { border: 1px solid #ccc; padding: 10px; text-align: center; }
    </style>
</head>
<body>
<h2>Shopping Cart</h2>

<c:if test="${empty cart.items}">
    <p>Your cart is empty.</p>
</c:if>

<c:if test="${not empty cart.items}">
    <table>
        <tr>
            <th>Model</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Subtotal</th>
            <th>Action</th>
        </tr>
        <c:forEach var="item" items="${cart.items}">
            <tr>
                <td>${item.product.model}</td>
                <td>
                    <!-- Update quantity -->
                    <form action="${pageContext.request.contextPath}/cart" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="productId" value="${item.product.id}">
                        <input type="number" name="quantity" value="${item.quantity}" min="0">
                        <input type="submit" value="Update">
                    </form>
                </td>
                <td>${item.product.price}</td>
                <td>${item.subtotal}</td>
                <td>
                    <!-- Remove product -->
                    <form action="${pageContext.request.contextPath}/cart" method="post">
                        <input type="hidden" name="action" value="remove">
                        <input type="hidden" name="productId" value="${item.product.id}">
                        <input type="submit" value="Remove">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <p><strong>Total: </strong> ${cart.total}</p>

    <!-- Clear cart -->
    <form action="${pageContext.request.contextPath}/cart" method="post">
        <input type="hidden" name="action" value="clear">
        <input type="submit" value="Clear Cart">
    </form>
</c:if>

<p><a href="${pageContext.request.contextPath}/products">Continue Shopping</a></p>

</body>
</html>
