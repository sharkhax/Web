<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:setBundle basename="locale.pagecontent"/>
<html>
<head>
    <title><fmt:message key="userReg.pageTitle"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet" type="text/css">

</head>
<body>
<jsp:include page="background.jsp"/>
<%@include file="header.jsp" %>

<div style="width: 1200px;
    margin: 100px auto">
    <c:choose>
        <c:when test="${validated eq true}">
            <form action="${pageContext.request.contextPath}/mainController" method="post">
                <input type="hidden" name="command" value="register_user"/>
                <div style="background-color: #f4f4f4b8">
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="inputLogin1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="userReg.login"/></label>
                            <c:choose>
                                <c:when test="${not empty userRegistrationExistingFields.login}">
                                    <input name="login" type="text" value="${userRegistrationExistingFields.login}"
                                           pattern="[\w\-.]{5,16}"
                                           class="form-control is-invalid" id="inputLogin1"
                                           required minlength="5" maxlength="16"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="userReg.loginExistsMsg"/></div>
                                </c:when>
                                <c:when test="${not empty userRegistrationFields.login}">
                                    <input name="login" type="text" value="${userRegistrationFields.login}"
                                           pattern="[\w\-.]{5,16}"
                                           class="form-control is-valid" id="inputLogin1"
                                           required minlength="5" maxlength="16"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="valid-feedback">
                                        <fmt:message key="userReg.loginDescribing"/></div>
                                </c:when>
                                <c:otherwise>
                                    <input name="login" type="text" pattern="[\w\-.]{5,16}"
                                           class="form-control is-invalid" id="inputLogin1"
                                           required minlength="5" maxlength="16"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="userReg.loginDescribing"/></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="inputEmail1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="userReg.email"/></label>
                            <c:choose>
                                <c:when test="${not empty userRegistrationExistingFields.email}">
                                    <input name="email" type="email" value="${userRegistrationExistingFields.email}"
                                           class="form-control is-invalid" id="inputEmail1" required maxlength="50"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="userReg.emailExistsMsg"/></div>
                                </c:when>
                                <c:when test="${not empty userRegistrationFields.email}">
                                    <input name="email" type="email" value="${userRegistrationFields.email}"
                                           class="form-control is-valid" id="inputEmail1" required maxlength="50"/>
                                </c:when>
                                <c:otherwise>
                                    <input name="email" type="email" class="form-control is-invalid"
                                           id="inputEmail1" required maxlength="50"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        example@example.com
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="inputPassword1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="userReg.password"/></label>
                            <c:choose>
                                <c:when test="${not empty userRegistrationFields.password}">
                                    <input name="password" value="${userRegistrationFields.password}" type="password"
                                           pattern="[\w~!-]{5,16}" class="form-control is-valid"
                                           id="inputPassword1" required minlength="5" maxlength="16"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="valid-feedback">
                                        <fmt:message key="userReg.passwordDescribing"/></div>
                                </c:when>
                                <c:otherwise>
                                    <input name="password" type="password" pattern="[\w~!-]{5,16}"
                                           class="form-control is-invalid"
                                           id="inputPassword1" required minlength="5" maxlength="16"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="userReg.passwordDescribing"/></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="inputName1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="userReg.name"/></label>
                            <c:choose>
                                <c:when test="${not empty userRegistrationExistingFields.employeeName}">
                                    <input name="employeeName" type="text" value="${userRegistrationExistingFields.employeeName}"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-invalid" id="inputName1" required maxlength="45"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="userReg.nameExistsMsg"/></div>
                                </c:when>
                                <c:when test="${not empty userRegistrationFields.employeeName}">
                                    <input name="employeeName" type="text" value="${userRegistrationFields.employeeName}"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-valid" id="inputName1" required maxlength="45"/>
                                </c:when>
                                <c:otherwise>
                                    <input name="employeeName" type="text" pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-invalid" id="inputName1" required maxlength="45"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="userReg.invalidNameMsg"/></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="inputSurname1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="userReg.surname"/></label>
                            <c:choose>
                                <c:when test="${not empty userRegistrationExistingFields.employeeSurname}">
                                    <input name="employeeSurname" type="text" value="${userRegistrationExistingFields.employeeSurname}"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-invalid" id="inputSurname1" required maxlength="45"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="userReg.nameExistsMsg"/></div>
                                </c:when>
                                <c:when test="${not empty userRegistrationFields.employeeSurname}">
                                    <input name="employeeSurname" type="text" value="${userRegistrationFields.employeeSurname}"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-valid" id="inputSurname1" required maxlength="45"/>
                                </c:when>
                                <c:otherwise>
                                    <input name="employeeSurname" type="text" pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-invalid" id="inputSurname1" required maxlength="45"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="userReg.invalidSurnameMsg"/></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="inputRole1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="userReg.position"/></label>
                            <c:choose>
                                <c:when test="${not empty userRegistrationFields.employeePosition}">
                                    <select id="inputRole1" name="employeePosition" class="form-control is-valid" required>
                                        <c:choose>
                                            <c:when test="${userRegistrationFields.employeePosition eq 'assistant'}">
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="doctor"><fmt:message
                                                        key="userReg.positionDoctor"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="assistant" selected><fmt:message
                                                        key="userReg.positionAssistant"/></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="doctor" selected><fmt:message
                                                        key="userReg.positionDoctor"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="assistant"><fmt:message
                                                        key="userReg.positionAssistant"/></option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select id="inputRole1" name="employeePosition" class="form-control is-invalid" required>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="doctor" selected><fmt:message
                                                key="userReg.positionDoctor"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="assistant"><fmt:message
                                                key="userReg.positionAssistant"/></option>
                                    </select>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="userReg.invalidPositionMsg"/></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="inputAge1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                                    key="userReg.age"/></label>
                            <c:choose>
                                <c:when test="${not empty userRegistrationFields.employeeAge}">
                                    <input name="employeeAge" type="number" value="${userRegistrationFields.employeeAge}" min="18"
                                           max="99"
                                           class="form-control is-valid" id="inputAge1" required/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="valid-feedback">
                                        <fmt:message key="userReg.ageDescribing"/></div>
                                </c:when>
                                <c:otherwise>
                                    <input name="employeeAge" type="number" min="18" max="99"
                                           class="form-control is-invalid" id="inputAge1" required/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="userReg.ageDescribing"/></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="inputGender1"
                                   style="font-weight: bold; font-family: 'Times New Roman', sans-serif"><fmt:message
                                    key="userReg.gender"/></label>
                            <c:choose>
                                <c:when test="${not empty userRegistrationFields.employeeGender}">
                                    <select id="inputGender1" name="employeeGender" class="form-control is-valid" required>
                                        <c:choose>
                                            <c:when test="${userRegistrationFields.employeeGender eq 'F'}">
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="M"><fmt:message key="userReg.genderMale"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="F" selected><fmt:message
                                                        key="userReg.genderFemale"/></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="M" selected><fmt:message
                                                        key="userReg.genderMale"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="F"><fmt:message key="userReg.genderFemale"/></option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select id="inputGender1" name="employeeGender" class="form-control is-invalid" required>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="M" selected><fmt:message key="userReg.genderMale"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="F"><fmt:message key="userReg.genderFemale"/></option>
                                    </select>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="userReg.ageDescribing"/></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-2">
                            <label for="inputDate1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                                    key="userReg.hireDate"/></label>
                            <c:choose>
                                <c:when test="${not empty userRegistrationFields.hireDate}">
                                    <input name="hireDate" type="text"
                                           pattern="((20)[0-9]{2})-((0[1-9])|(1[0-2]))-((0[1-9])|([1-2][0-9])|(3[0-1]))"
                                           value="${userRegistrationFields.hireDate}" class="form-control is-valid"
                                           id="inputDate1" required/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="valid-feedback">
                                        <fmt:message key="userReg.dateDescribing"/></div>
                                </c:when>
                                <c:otherwise>
                                    <input name="hireDate" type="text"
                                           pattern="((20)[0-9]{2})-((0[1-9])|(1[0-2]))-((0[1-9])|([1-2][0-9])|(3[0-1]))"
                                           class="form-control is-invalid" id="inputDate1" required/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="userReg.dateDescribing"/></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="userReg.registerButton"/></button>
            </form>
        </c:when>
        <c:otherwise>
            <form action="${pageContext.request.contextPath}/mainController" method="post">
                <input type="hidden" name="command" value="register_user"/>
                <div style="background-color: #f4f4f4b8">
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="inputLogin"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="userReg.login"/></label>
                            <input name="login" type="text" value="${userRegistrationFields.login}"
                                   pattern="[\w\-.]{5,16}"
                                   class="form-control"
                                   id="inputLogin"
                                   aria-describedby="loginHelp" required minlength="5" maxlength="16"/>
                            <small style="font-family: 'Times New Roman', sans-serif" id="loginHelp"
                                   class="form-text text-muted"><fmt:message key="userReg.loginDescribing"/></small>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="inputEmail"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="userReg.email"/></label>
                            <input name="email" type="email" value="${userRegistrationFields.email}"
                                   class="form-control"
                                   id="inputEmail"
                                   required
                                   maxlength="50"/>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="inputPassword"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="userReg.password"/></label>
                            <input name="password" type="password" pattern="[\w~!-]{5,16}" class="form-control"
                                   id="inputPassword"
                                   aria-describedby="passwordHelp" required minlength="5" maxlength="16"/>
                            <small style="font-family: 'Times New Roman', sans-serif" id="passwordHelp"
                                   class="form-text text-muted"><fmt:message key="userReg.passwordDescribing"/></small>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="inputName"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="userReg.name"/></label>
                            <input name="employeeName" type="text" value="${userRegistrationFields.employeeName}"
                                   pattern="[a-zA-Z\-']{2,45}"
                                   class="form-control"
                                   id="inputName" required
                                   maxlength="45"/>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="inputSurname"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="userReg.surname"/></label>
                            <input name="employeeSurname" type="text" value="${userRegistrationFields.employeeSurname}"
                                   pattern="[a-zA-Z\-']{2,45}"
                                   class="form-control" id="inputSurname"
                                   required maxlength="45"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="inputRole"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="userReg.position"/></label>
                            <select id="inputRole" name="employeePosition" class="form-control" required>
                                <c:choose>
                                    <c:when test="${userRegistrationFields.employeePosition eq 'assistant'}">
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="doctor"><fmt:message key="userReg.positionDoctor"/></option>
                                        <option style="font-weight: bold; font-family: 'Times New Roman', sans-serif"
                                                value="assistant" selected><fmt:message
                                                key="userReg.positionAssistant"/></option>
                                    </c:when>
                                    <c:otherwise>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="doctor" selected><fmt:message
                                                key="userReg.positionDoctor"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="assistant"><fmt:message
                                                key="userReg.positionAssistant"/></option>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="inputAge"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                                    key="userReg.age"/></label>
                            <input name="employeeAge" type="number" value="${userRegistrationFields.employeeAge}" min="18" max="99"
                                   class="form-control"
                                   id="inputAge"
                                   aria-describedby="ageHelpBlock"
                                   required/>
                            <small style="font-family: 'Times New Roman', sans-serif" id="ageHelpBlock"
                                   class="form-text text-muted"><fmt:message key="userReg.ageDescribing"/></small>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="inputGender"
                                   style="font-weight: bold; font-family: 'Times New Roman', sans-serif"><fmt:message
                                    key="userReg.gender"/></label>
                            <select id="inputGender" name="employeeGender" class="form-control" required>
                                <c:choose>
                                    <c:when test="${userRegistrationFields.employeeGender eq 'F'}">
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="M"><fmt:message key="userReg.genderMale"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="F" selected><fmt:message key="userReg.genderFemale"/></option>
                                    </c:when>
                                    <c:otherwise>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="M" selected><fmt:message key="userReg.genderMale"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="F"><fmt:message key="userReg.genderFemale"/></option>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-2">
                            <label for="inputDate"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                                    key="userReg.hireDate"/></label>
                            <input name="hireDate" type="text"
                                   pattern="((20)[0-9]{2})-((0[1-9])|(1[0-2]))-((0[1-9])|([1-2][0-9])|(3[0-1]))"
                                   value="${userRegistrationFields.hireDate}" class="form-control" id="inputDate"
                                   aria-describedby="dateHelpBlock"
                                   required/>
                            <small id="dateHelpBlock" style="font-family: 'Times New Roman', sans-serif"
                                   class="form-text text-muted"><fmt:message key="userReg.dateDescribing"/></small>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="userReg.registerButton"/></button>
                <c:if test="${registrationSuccess eq true}">
                    <span style="color: white; font-size: 14pt; font-weight: bold"><fmt:message
                            key="userReg.registrationSuccess"/></span>
                </c:if>
            </form>
        </c:otherwise>
    </c:choose>
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
