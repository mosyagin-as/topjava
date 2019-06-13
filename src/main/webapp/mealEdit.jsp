<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
    <title>Add new user</title>
</head>
<body>
<%--<script>--%>
<%--    $(function() {--%>
<%--        $('input[name=dob]').datepicker();--%>
<%--    });--%>
<%--</script>--%>

<form method="POST" action='MealController' name="frmAddUser">
<%--    Meal ID : <input type="text" readonly="readonly" name="id"--%>
<%--                     value="<c:out value="${meal.id}" />" /> <br />--%>
    Date : <input type="text" name="date" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${mdate}" />" /> <br/>
    Time : <input type="text" name="time" value="<fmt:formatDate pattern="HH:mm:ss" value="${mtime}" />" /> <br/>

<%--    First Name : <input--%>
<%--        type="text" name="dateTime"--%>
<%--        value="<c:out value="${user.firstName}" />" /> <br />--%>
<%--    Last Name : <input--%>
<%--        type="text" name="lastName"--%>
<%--        value="<c:out value="${user.lastName}" />" /> <br />--%>
<%--    Email : <input type="text" name="email"--%>
<%--                   value="<c:out value="${user.email}" />" /> <br />--%>
    <input type="submit" value="Submit" />
</form>
</body>
</html>