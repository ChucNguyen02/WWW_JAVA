<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Phòng ban</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">
<h2>${department != null ? "Cập nhật" : "Thêm"} phòng ban</h2>
<form action="departments" method="post" class="w-50">
    <input type="hidden" name="action" value="${department != null ? 'update' : 'add'}">
    <c:if test="${department != null}">
        <input type="hidden" name="id" value="${department.id}">
    </c:if>
    <div class="mb-3">
        <label>Tên phòng ban</label>
        <input type="text" name="name" class="form-control" value="${department.name}" required>
    </div>
    <div class="mb-3">
        <label>Ghi chú</label>
        <textarea name="note" class="form-control">${department.note}</textarea>
    </div>
    <button type="submit" class="btn btn-primary">Lưu</button>
    <a href="departments" class="btn btn-secondary">Quay lại</a>
</form>
</body>
</html>
