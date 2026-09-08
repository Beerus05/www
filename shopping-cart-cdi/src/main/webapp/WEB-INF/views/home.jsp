<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang chủ</title>
</head>
<body>
<div>
    <p>Số người đang online: <strong>${applicationScope.activeUsersCount}</strong></p>
    <a href="${pageContext.request.contextPath}/products">Quay lại danh sách sản phẩm</a>
</div>
</body>
</html>