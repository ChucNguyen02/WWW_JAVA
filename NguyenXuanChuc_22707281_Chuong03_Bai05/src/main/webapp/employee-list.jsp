<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Danh sách nhân viên</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">
<h2>
    <c:choose>
        <c:when test="${not empty selectedDeptName}">
            Nhân viên của phòng: <span class="text-primary">${selectedDeptName}</span>
        </c:when>
        <c:otherwise>
            Danh sách nhân viên
        </c:otherwise>
    </c:choose>
</h2>

<!-- Chọn phòng ban -->
<form method="get" action="employees" class="mb-3 d-flex">
    <select name="deptId" onchange="this.form.submit()" class="form-select me-2" style="max-width:300px;">
        <option value="">-- Tất cả phòng ban --</option>
        <c:forEach items="${departments}" var="d">
            <option value="${d.id}" ${selectedDeptId == d.id ? 'selected' : ''}>${d.name}</option>
        </c:forEach>
    </select>
    <a href="employees?action=form" class="btn btn-success">Thêm nhân viên</a>
</form>

<table class="table table-bordered table-striped">
    <thead class="table-light">
    <tr>
        <th>ID</th><th>Tên</th><th>Email</th><th>Điện thoại</th>
        <th>Chức vụ</th><th>Lương</th><th>Phòng ban</th><th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${employees}" var="e">
        <tr>
            <td>${e.id}</td>
            <td>${e.name}</td>
            <td>${e.email}</td>
            <td>${e.phone}</td>
            <td>${e.position}</td>
            <td>${e.salary}</td>
            <td>
                <c:forEach items="${departments}" var="d">
                    <c:if test="${d.id == e.departmentId}">${d.name}</c:if>
                </c:forEach>
            </td>
            <td>
                <a href="employees?action=form&id=${e.id}" class="btn btn-warning btn-sm">Sửa</a>
                <form action="employees" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${e.id}">
                    <button type="submit" class="btn btn-danger btn-sm"
                            onclick="return confirm('Xóa nhân viên này?')">
                        Xóa
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
