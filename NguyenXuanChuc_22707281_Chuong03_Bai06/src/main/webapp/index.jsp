<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý tin tức</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="index.jsp">TinTucOnline</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="danhsachtintuc">Danh sách tin tức</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="tintucform">Thêm tin tức</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="quanly">Quản lý tin tức</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-5">
    <div class="text-center">
        <h1 class="mb-4">Chào mừng đến hệ thống quản lý tin tức</h1>
        <p class="lead">Bạn có thể xem tin tức, thêm mới hoặc quản lý dữ liệu tin tức trực tuyến.</p>
        <div class="mt-4 d-flex justify-content-center gap-3">
            <a href="danhsachtintuc" class="btn btn-primary btn-lg">Xem tin tức</a>
            <a href="tintucform" class="btn btn-success btn-lg">Thêm tin mới</a>
            <a href="quanly" class="btn btn-warning btn-lg">Quản lý</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
