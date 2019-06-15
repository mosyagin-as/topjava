<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h3>Edit meal</h3>
<hr>

<form method="POST" action='meals'>
    <dl>
        <dt>DateTime</dt>
        <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    </dl>
<%--    Date : <input type="text" name="date" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${mdate}" />" /> <br/>--%>
<%--    Time : <input type="text" name="time" value="<fmt:formatDate pattern="HH:mm:ss" value="${mtime}" />" /> <br/>--%>
    <input type="submit" value="Submit" />
</form>
</body>
</html>