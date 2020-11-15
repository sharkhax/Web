<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:setBundle basename="locale.pagecontent"/>
<html>
<head>
    <title><fmt:message key="userUpdating.pageTitle"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="../supporting/background.jsp"/>
<%@ include file="../supporting/header.jsp" %>

<div style="width: 1200px;
    margin: 100px auto">
    <c:choose>
        <c:when test="${validated eq true}">
            <form action="${pageContext.request.contextPath}/mainController" method="post">
                <input type="hidden" name="command" value="update_user"/>
                <input type="hidden" name="userId" value="${userInfoId}">
                <div style="background-color: #f4f4f4b8">
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="inputLogin1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="userUpdating.login"/></label>
                            <c:choose>
                                <c:when test="${not empty userDataExistingFields.login}">
                                    <input name="login" type="text" value="${userDataExistingFields.login}"
                                           pattern="[\w\-.]{5,16}"
                                           class="form-control is-invalid"
                                           id="inputLogin1"
                                           minlength="5" maxlength="16"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="userUpdating.loginExistsMsg"/>
                                    </div>
                                </c:when>
                                <c:when test="${not empty userDataNewFields.login}">
                                    <input name="login" type="text" value="${userDataNewFields.login}"
                                           pattern="[\w\-.]{5,16}"
                                           class="form-control is-valid"
                                           id="inputLogin1"
                                           minlength="5" maxlength="16"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="valid-feedback">
                                        <fmt:message key="userUpdating.loginDescribing"/>
                                    </div>
                                </c:when>
                                <c:when test="${not empty userDataEmptyFields.login}">
                                    <input name="login" type="text"
                                           pattern="[\w\-.]{5,16}"
                                           class="form-control"
                                           id="inputLogin1"
                                           aria-describedby="loginHelp1" minlength="5" maxlength="16"/>
                                    <small style="font-family: 'Times New Roman', sans-serif" id="loginHelp1"
                                           class="form-text text-muted"><fmt:message
                                            key="userUpdating.loginDescribing"/></small>
                                </c:when>
                                <c:otherwise>
                                    <input name="login" type="text"
                                           pattern="[\w\-.]{5,16}"
                                           class="form-control is-invalid"
                                           id="inputLogin1"
                                           minlength="5" maxlength="16"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="userUpdating.loginDescribing"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="inputEmail1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="userUpdating.email"/></label>
                            <c:choose>
                                <c:when test="${not empty userDataExistingFields.email}">
                                    <input name="email" type="email" value="${userDataExistingFields.email}"
                                           class="form-control is-invalid"
                                           id="inputEmail1"
                                           required
                                           maxlength="50"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="userUpdating.emailExistsMsg"/></div>
                                </c:when>
                                <c:when test="${not empty userDataNewFields.email}">
                                    <input name="email" type="email" value="${userDataNewFields.email}"
                                           class="form-control is-valid"
                                           id="inputEmail1"
                                           maxlength="50"/>
                                </c:when>
                                <c:when test="${not empty userDataEmptyFields.email}">
                                    <input name="email" type="email"
                                           class="form-control"
                                           id="inputEmail1"
                                           maxlength="50"/>
                                </c:when>
                                <c:otherwise>
                                    <input name="email" type="email"
                                           class="form-control is-invalid"
                                           id="inputEmail1"
                                           maxlength="50"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        example@example.com
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message
                        key="userUpdating.submitButton"/></button>
            </form>
        </c:when>
        <c:otherwise>
            <form action="${pageContext.request.contextPath}/mainController" method="post">
                <input type="hidden" name="command" value="update_user"/>
                <input type="hidden" name="userId" value="${userInfoId}">
                <div style="background-color: #f4f4f4b8">
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="inputLogin"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="userUpdating.login"/></label>
                            <input name="login" type="text"
                                   pattern="[\w\-.]{5,16}"
                                   class="form-control"
                                   id="inputLogin"
                                   aria-describedby="loginHelp" minlength="5" maxlength="16"/>
                            <small style="font-family: 'Times New Roman', sans-serif" id="loginHelp"
                                   class="form-text text-muted"><fmt:message
                                    key="userUpdating.loginDescribing"/></small>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="inputEmail"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="userUpdating.email"/></label>
                            <input name="email" type="email"
                                   class="form-control"
                                   id="inputEmail"
                                   maxlength="50"/>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="userUpdating.submitButton"/></button>
            </form>
        </c:otherwise>
    </c:choose>
</div>

<%@include file="user_info_part.jsp" %>

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
