<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:setBundle basename="locale.pagecontent"/>
<html>
<head>
    <title><fmt:message key="main.personalSettingsButton"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet" type="text/css">
</head>
<body>

<jsp:include page="background.jsp"/>
<%@include file="header.jsp" %>

<div class="main-menu">
    <div class="list-group">
        <form action="mainController" method="post">
            <input type="hidden" name="command" value="redirect_command">
            <button class="list-group-item list-group-item-action" type="submit" name="r_page"
                    value="/settings/changePassword" style="width: 90%;
    color: #495057;
    text-align: center;
    background-color: #ffffffb5;
    font-weight: bold;
    font-family: 'Times New Roman', sans-serif">
                <span style=""><fmt:message key="settings.changePasswordButton"/></span>
            </button>
        </form>
    </div>
</div>

<div style="background-color: #f4f4f4b8; width: 700px; margin-left: 2px">
    <dl class="row">
        <dt class="col-sm-3"><span style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                key="settings.login"/></span></dt>
        <dd class="col-sm-9"><span style="font-family: 'Times New Roman', sans-serif">${userDataFields.login}</span>
        </dd>

        <dt class="col-sm-3"><span style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                key="settings.email"/></span></dt>
        <dd class="col-sm-9"><span style="font-family: 'Times New Roman', sans-serif">${userDataFields.email}</span>
        </dd>

        <dt class="col-sm-3"><span style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                key="settings.name"/></span></dt>
        <dd class="col-sm-9"><span style="font-family: 'Times New Roman', sans-serif">${userDataFields.name}</span></dd>

        <dt class="col-sm-3"><span style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                key="settings.surname"/></span></dt>
        <dd class="col-sm-9"><span style="font-family: 'Times New Roman', sans-serif">${userDataFields.surname}</span>
        </dd>

        <dt class="col-sm-3"><span style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                key="settings.age"/></span></dt>
        <dd class="col-sm-9"><span style="font-family: 'Times New Roman', sans-serif">${userDataFields.age}</span></dd>

        <dt class="col-sm-3"><span style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                key="settings.gender"/></span></dt>
        <dd class="col-sm-9"><span style="font-family: 'Times New Roman', sans-serif">${userDataFields.gender}</span>
        </dd>

        <dt class="col-sm-3"><span style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                key="settings.position"/></span></dt>
        <dd class="col-sm-9"><span style="font-family: 'Times New Roman', sans-serif">${userDataFields.position}</span>
        </dd>

        <dt class="col-sm-3"><span style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                key="settings.hireDate"/></span></dt>
        <dd class="col-sm-9"><span style="font-family: 'Times New Roman', sans-serif">${userDataFields.hireDate}</span>
        </dd>

        <dt class="col-sm-3"><span style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                key="settings.employeeStatus"/></span></dt>
        <dd class="col-sm-9"><span
                style="font-family: 'Times New Roman', sans-serif">${userDataFields.employeeStatus}</span></dd>
    </dl>
</div>

<%@include file="footer.jsp" %>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
        integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
        crossorigin="anonymous"></script>

</body>
</html>
