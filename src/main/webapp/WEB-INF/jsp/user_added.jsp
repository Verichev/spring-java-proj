<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
    <head>
        <title>User added!</title>
        <script src="animate.js"></script>
    </head>
    <body>

        <div>
            User added!
        </div>
        <div>
            email: ${email}
        </div>
        <script>
          elem.onclick = function() {
            animate({
              duration: 1000,
              timing: function(timeFraction) {
                return timeFraction;
              },
              draw: function(progress) {
                elem.style.width = progress * 100 + '%';
              }
            });
          };
        </script>
    </body>
</html>