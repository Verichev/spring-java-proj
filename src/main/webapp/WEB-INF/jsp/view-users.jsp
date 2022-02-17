<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
    <head>
        <title>View Users</title>
        <script src="utils.js"></script>
        <link href="css/common.css" rel="stylesheet">
          <style>
            #train {
              position: relative;
              cursor: pointer;
            }
          </style>
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

        <form name="userdetails" action="/user" method="post" class = "form" onsubmit="return ValidateEmail(document.userdetails.email)">
          <div>
            <label for="email">Email:</label>
            <input name="email" id="email" value="Hi">
          </div>
          <div>
            <label for="pass">Password:</label>
            <input name="pass" id="pass" value="Mom">
          </div>
          <div>
            <button class="button">Add User</button>
          </div>
        </form>

        <img id="train" src="/train.gif" />

        <script>
            train.onclick = function() {
              let start = Date.now();

              let timer = setInterval(function() {
                let timePassed = Date.now() - start;

                train.style.left = timePassed / 5 + 'px';

                if (timePassed > 2000) clearInterval(timer);

              }, 20);
            }
          </script>
    </body>
</html>