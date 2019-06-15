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
<a href="meals?action=create">Add meal</a>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <%--    <jsp:useBean id="mealToList" type="java.util.List"/>--%>
    <c:forEach var="meal" items="${mealToList}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="background-color:${meal.excess ? 'red' : 'greenyellow'}">
            <td>
                <fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parsedDateTime"/>
                <fmt:formatDate pattern="yyyy-MMM-dd HH:mm:ss" value="${parsedDateTime}"/>
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>