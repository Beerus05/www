<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c"
           uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Account List</title>
</head>
<body>
<h2>Account List</h2>

<table border="1">

    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th>Date of Birth</th>
    </tr>

    <c:forEach
            var="account"
            items="${accounts}">

        <tr>

            <td>${account.id}</td>

            <td>${account.firstName}</td>

            <td>${account.lastName}</td>

            <td>${account.email}</td>

            <td>${account.dateOfBirth}</td>

        </tr>

    </c:forEach>

</table>

</body>
</html>
