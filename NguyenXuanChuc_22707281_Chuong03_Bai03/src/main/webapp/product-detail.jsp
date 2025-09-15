<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <title>Product Detail</title>
</head>
<body>
<h2>Product Detail</h2>

<c:if test="${not empty product}">
  <ul>
    <li>ID: ${product.id}</li>
    <li>Model: ${product.model}</li>
    <li>Description: ${product.description}</li>
    <li>Quantity: ${product.quantity}</li>
    <li>Price: ${product.price}</li>
  </ul>

  <img src="images/${product.imgURL}" alt="${product.model}" width="200"><br/><br/>

  <form action="${pageContext.request.contextPath}/cart" method="post">
    <input type="hidden" name="action" value="add">
    <input type="hidden" name="id" value="${product.id}">
    <input type="number" name="quantity" value="1" min="1">
    <input type="submit" value="Add To Cart">
  </form>
</c:if>

<p><a href="${pageContext.request.contextPath}/products">Back to Product List</a></p>

</body>
</html>
