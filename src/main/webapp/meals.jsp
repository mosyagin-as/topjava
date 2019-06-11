<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1">
    <thead>
    <tr>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${mealToList}">
        <tr style="background-color:${meal.excess ? 'red' : 'greenyellow'}">
            <td>${meal.stringDateTime}</td>
<%--            <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime" type="both"/>--%>
<%--            <td><fmt:formatDate pattern="yyyy-MMM-dd HH:mm:ss" value="${parsedDateTime}" /></td>--%>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>