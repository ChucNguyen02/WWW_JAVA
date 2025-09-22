<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Danh sách phòng ban</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">
<h2>Danh sách phòng ban</h2>

<!-- Form tìm kiếm -->
<form method="get" action="departments" class="mb-3 d-flex">
    <input type="text" name="keyword" placeholder="Tìm theo tên"
           class="form-control me-2" style="max-width:300px;"
           value="${param.keyword}">
    <button type="submit" class="btn btn-primary me-2">Tìm</button>
    <a href="departments?action=form" class="btn btn-success">Thêm phòng ban</a>
</form>

<!-- Bảng dữ liệu -->
<table class="table table-bordered table-striped">
    <thead class="table-light">
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Ghi chú</th>
        <th>Nhân viên</th>
        <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${departments}" var="d">
        <tr>
            <td>${d.id}</td>
            <td>${d.name}</td>
            <td>${d.note}</td>
            <td>
                <a href="employees?deptId=${d.id}" class="btn btn-info btn-sm">
                    Xem nhân viên
                </a>
            </td>
            <td>
                <a href="departments?action=form&id=${d.id}" class="btn btn-warning btn-sm">Sửa</a>
                <form action="departments" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${d.id}">
                    <button type="submit" class="btn btn-danger btn-sm"
                            onclick="return confirm('Xóa phòng ban này?')">
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
