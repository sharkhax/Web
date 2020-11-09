<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="custom_tag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:setBundle basename="locale.pagecontent"/>
<html>
<head>
    <title><fmt:message key="passChanging.pageTitle"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet" type="text/css">
</head>
<body>

<jsp:include page="background.jsp"/>
<%@ include file="header.jsp" %>

<div style="width: 1200px; margin: 100px auto">
    <c:choose>
        <c:when test="${validated eq true}">
            <form action="${pageContext.request.contextPath}/mainController" method="post">
                <input type="hidden" name="command" value="update_user_password"/>
                <input type="hidden" name="userId" value="${userId}"/>
                <div class="form-row">
                    <div class="form-group col-md-4" style="background-color: #f4f4f4b8">
                        <label for="inputNewPassword" style="font-family: 'Times New Roman', sans-serif">
                            <fmt:message key="passChanging.newPassword"/></label>
                        <c:choose>
                            <c:when test="${not empty changingPasswordFields.newPassword}">
                                <input type="password" name="newPassword"
                                       value="${changingPasswordFields.newPassword}"
                                       pattern="[\w~!-]{5,16}" class="form-control is-valid" id="inputNewPassword"
                                       required minlength="5" maxlength="16"/>
                                <div style="font-family: 'Times New Roman', sans-serif" class="valid-feedback">
                                    <fmt:message key="userReg.passwordDescribing"/></div>
                            </c:when>
                            <c:otherwise>
                                <input type="password" name="newPassword" pattern="[\w~!-]{5,16}"
                                       class="form-control is-invalid" id="inputNewPassword" required minlength="5"
                                       maxlength="16"/>
                                <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                    <fmt:message key="userReg.passwordDescribing"/></div>
                            </c:otherwise>
                        </c:choose>

                    </div>
                </div>
                <button type="submit" class="btn btn-primary">
                    <fmt:message key="passChanging.changePasswordButton"/></button>
            </form>
        </c:when>
        <c:otherwise>
            <form action="${pageContext.request.contextPath}/mainController" method="post">
                <input type="hidden" name="command" value="update_user_password"/>
                <input type="hidden" name="userId" value="${userId}"/>
                <div class="form-row">
                    <div class="form-group col-md-4" style="background-color: #f4f4f4b8">
                        <label for="inputNewPassword2"
                               style="font-family: 'Times New Roman', sans-serif"><fmt:message
                                key="passChanging.newPassword"/></label>
                        <input type="password" name="newPassword" pattern="[\w~!-]{5,16}" class="form-control"
                               aria-describedby="newPasswordHelp" id="inputNewPassword2" required minlength="5"
                               maxlength="16"/>
                        <small style="font-family: 'Times New Roman', sans-serif" id="newPasswordHelp"
                               class="form-text text-muted"><fmt:message key="userReg.passwordDescribing"/>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message
                        key="passChanging.changePasswordButton"/></button>
            </form>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="footer.jsp" %>
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
