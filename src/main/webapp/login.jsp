<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<form method="post" action="login">
    <label>Select User</label>

    <c:forEach items="${userList}" var="user">

        <p><input type="radio" value="${user.id}" name="userId">${user.name}</p>

    </c:forEach>

    <button type="submit">Login</button>
</form>
</body>
</html>