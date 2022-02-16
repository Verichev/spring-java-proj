<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
    <head>
        <title>View Users</title>
    </head>
    <body>
        <table>
            <thead>
                <tr>
                    <th>UserId</th>
                    <th>Email</th>
                    <th>Password</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.email}</td>
                        <td>${user.password}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>