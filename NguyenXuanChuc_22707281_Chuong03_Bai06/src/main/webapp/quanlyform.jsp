<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý tin tức</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">
<h2>Quản lý tin tức</h2>

<table class="table table-bordered">
    <thead class="table-light">
    <tr><th>MATT</th><th>TIÊU ĐỀ</th><th>LIÊN KẾT</th><th>DANHMUC</th><th>HÀNH ĐỘNG</th></tr>
    </thead>
    <tbody>
    <c:forEach items="${tintucs}" var="t">
        <tr>
            <td>${t.matt}</td>
            <td>${t.tieude}</td>
            <td><a href="${t.lienket}" target="_blank">${t.lienket}</a></td>
            <td>
                <c:forEach items="${danhmucs}" var="dm"><c:if test="${dm.madm==t.madm}">${dm.tenDanhMuc}</c:if></c:forEach>
            </td>
            <td>
                <form action="quanly" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="matt" value="${t.matt}">
                    <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Xác nhận xóa?')">Xóa</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
