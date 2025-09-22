<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách tin tức</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">
<h2>
    <c:choose>
        <c:when test="${not empty selectedDanhMuc}">
            Tin tức - ${selectedDanhMuc.tenDanhMuc}
        </c:when>
        <c:otherwise>Tất cả tin tức</c:otherwise>
    </c:choose>
</h2>

<form action="danhsachtintuc" method="get" class="mb-3 d-flex gap-2">
    <select name="madm" onchange="this.form.submit()" class="form-select w-25">
        <option value="">-- Tất cả danh mục --</option>
        <c:forEach items="${danhmucs}" var="dm">
            <option value="${dm.madm}" ${selectedDanhMuc != null && selectedDanhMuc.madm == dm.madm ? 'selected' : ''}>${dm.tenDanhMuc}</option>
        </c:forEach>
    </select>
    <a href="tintucform" class="btn btn-success">Thêm tin mới</a>
</form>

<table class="table table-bordered table-striped">
    <thead class="table-light">
    <tr><th>MATT</th><th>TIÊU ĐỀ</th><th>NỘI DUNG</th><th>LIÊN KẾT</th><th>DANHMUC</th></tr>
    </thead>
    <tbody>
    <c:forEach items="${tintucs}" var="t">
        <tr>
            <td>${t.matt}</td>
            <td>${t.tieude}</td>
            <td>${t.noidungtt}</td>
            <td><a href="${t.lienket}" target="_blank">${t.lienket}</a></td>
            <td>
                <c:forEach items="${danhmucs}" var="dm">
                    <c:if test="${dm.madm == t.madm}">${dm.tenDanhMuc}</c:if>
                </c:forEach>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
