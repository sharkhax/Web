<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<form action="mainController" method="post">
    <input type="hidden" name="command" value="login"/>
    Login:<br/>
    <input type="text" name="login"/>
    <br/><br/>
    Password:<br/><input type="text" name="password"/>
    <br/><br/>
    <input type="submit" value="Login"/>
    <br/><br/>
    ${invalidLoginOrPassMsg}
    <br/>
    ${wrongAction}
    <br/>
    ${nullPage}
</form>
</body>
</html>
