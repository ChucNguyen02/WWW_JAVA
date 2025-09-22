<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm tin tức</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">
<h2>Thêm tin tức</h2>

<c:set var="errors" value="${errors}" />

<form action="tintucform" method="post" class="w-75">
    <div class="mb-3">
        <label class="form-label">Tiêu đề *</label>
        <input type="text" name="tieude" class="form-control" value="${tieude}">
        <small class="text-danger">${errors != null ? errors.tieude : ''}</small>
    </div>

    <div class="mb-3">
        <label class="form-label">Nội dung * (<=255 ký tự)</label>
        <textarea name="noidungtt" class="form-control" maxlength="255">${noidungtt}</textarea>
        <small class="text-danger">${errors != null ? errors.noidungtt : ''}</small>
    </div>

    <div class="mb-3">
        <label class="form-label">Liên kết * (bắt đầu bằng http://)</label>
        <input type="text" name="lienket" class="form-control" value="${lienket}">
        <small class="text-danger">${errors != null ? errors.lienket : ''}</small>
    </div>

    <div class="mb-3">
        <label class="form-label">Danh mục *</label>
        <select name="madm" class="form-select">
            <option value="">-- Chọn danh mục --</option>
            <c:forEach items="${danhmucs}" var="dm">
                <option value="${dm.madm}" ${selectedMadm == dm.madm ? 'selected' : ''}>${dm.tenDanhMuc}</option>
            </c:forEach>
        </select>
        <small class="text-danger">${errors != null ? errors.madm : ''}</small>
    </div>

    <button type="submit" class="btn btn-primary">Thêm</button>
    <a href="danhsachtintuc" class="btn btn-secondary">Quay lại</a>
</form>

<script>
    document.querySelector('form').addEventListener('submit', function(e){
        const lienket = document.querySelector('input[name="lienket"]').value.trim();
        const noidung = document.querySelector('textarea[name="noidungtt"]').value.trim();
        const linkRe = /^http:\/\//i;
        let ok = true;
        if (!linkRe.test(lienket)) {
            alert('Liên kết phải bắt đầu bằng http://');
            ok = false;
        }
        if (noidung.length > 255) {
            alert('Nội dung tối đa 255 ký tự');
            ok = false;
        }
        if (!ok) e.preventDefault();
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
