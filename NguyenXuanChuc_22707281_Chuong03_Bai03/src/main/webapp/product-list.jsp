<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <title>Product List</title>
  <style>
    .product-card {
      display: inline-block;
      border: 1px solid #ccc;
      margin: 10px;
      padding: 10px;
      width: 200px;
      text-align: center;
    }
    img { max-width: 150px; height: auto; }
  </style>
</head>
<body>
<h2>Product List</h2>

<p><a href="cart">View Cart</a></p>

<c:forEach items="${products}" var="p">
  <div class="product-card">
    <b>${p.model}</b><br/>
    <img src="images/${p.imgURL}" alt="${p.model}"><br/>
    Price: ${p.price}<br/>

    <!-- Add To Cart Form -->
    <form action="${pageContext.request.contextPath}/cart" method="post">
      <input type="hidden" name="action" value="add">
      <input type="hidden" name="id" value="${p.id}">
      <input type="number" name="quantity" value="1" min="1"><br/>
      <input type="submit" value="Add To Cart">
    </form>

    <!-- View Detail -->
    <p><a href="${pageContext.request.contextPath}/product?id=${p.id}">View Details</a></p>
  </div>
</c:forEach>

</body>
</html>
