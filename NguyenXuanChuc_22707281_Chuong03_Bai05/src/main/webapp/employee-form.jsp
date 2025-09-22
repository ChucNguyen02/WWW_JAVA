<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Nhân viên</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">
<h2>${employee != null ? "Cập nhật" : "Thêm"} nhân viên</h2>
<form action="employees" method="post" class="w-50">
    <input type="hidden" name="action" value="${employee != null ? 'update' : 'add'}">
    <c:if test="${employee != null}">
        <input type="hidden" name="id" value="${employee.id}">
    </c:if>
    <div class="mb-3">
        <label>Tên</label>
        <input type="text" name="name" class="form-control" value="${employee.name}" required>
    </div>
    <div class="mb-3">
        <label>Email</label>
        <input type="email" name="email" class="form-control" value="${employee.email}">
    </div>
    <div class="mb-3">
        <label>Điện thoại</label>
        <input type="text" name="phone" class="form-control" value="${employee.phone}">
    </div>
    <div class="mb-3">
        <label>Chức vụ</label>
        <input type="text" name="position" class="form-control" value="${employee.position}">
    </div>
    <div class="mb-3">
        <label>Lương</label>
        <input type="number" step="0.01" name="salary" class="form-control" value="${employee.salary}">
    </div>
    <div class="mb-3">
        <label>Phòng ban</label>
        <select name="departmentId" class="form-select">
            <c:forEach items="${departments}" var="d">
                <option value="${d.id}" ${employee.departmentId == d.id ? 'selected' : ''}>${d.name}</option>
            </c:forEach>
        </select>
    </div>
    <button type="submit" class="btn btn-primary">Lưu</button>
    <a href="employees" class="btn btn-secondary">Quay lại</a>
</form>
</body>
</html>
