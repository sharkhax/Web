<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:setBundle basename="locale.pagecontent"/>
<html>
<head>
    <title><fmt:message key="employeeUpdating.pageTitle"/></title>
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
                <input type="hidden" name="command" value="update_employee"/>
                <input type="hidden" name="employeeId" value="${employeeInfoId}">
                <div style="background-color: #f4f4f4b8">
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="inputName1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="employeeUpdating.name"/></label>
                            <c:choose>
                                <c:when test="${not empty employeeDataExistingFields.employeeName}">
                                    <input name="employeeName" type="text"
                                           value="${employeeDataExistingFields.employeeName}"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-invalid"
                                           id="inputName1"
                                           maxlength="45"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="employeeUpdating.nameExists"/></div>
                                </c:when>
                                <c:when test="${not empty employeeDataNewFields.employeeName}">
                                    <input name="employeeName" type="text"
                                           value="${employeeDataNewFields.employeeName}"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-valid"
                                           id="inputName1"
                                           maxlength="45"/>
                                </c:when>
                                <c:when test="${not empty employeeDataEmptyFields.employeeName}">
                                    <input name="employeeName" type="text"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control"
                                           id="inputName1"
                                           maxlength="45"/>
                                </c:when>
                                <c:otherwise>
                                    <input name="employeeName" type="text"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-invalid"
                                           id="inputName1"
                                           maxlength="45"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="employeeUpdating.invalidNameMsg"/></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="inputSurname1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="employeeUpdating.surname"/></label>
                            <c:choose>
                                <c:when test="${not empty employeeDataExistingFields.employeeSurname}">
                                    <input name="employeeSurname" type="text"
                                           value="${employeeDataExistingFields.employeeSurname}"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-invalid" id="inputSurname1"
                                           maxlength="45"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="employeeUpdating.nameExists"/></div>
                                </c:when>
                                <c:when test="${not empty employeeDataNewFields.employeeSurname}">
                                    <input name="employeeSurname" type="text"
                                           value="${employeeDataNewFields.employeeSurname}"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-valid" id="inputSurname1"
                                           maxlength="45"/>
                                </c:when>
                                <c:when test="${not empty employeeDataEmptyFields.employeeSurname}">
                                    <input name="employeeSurname" type="text"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control" id="inputSurname1"
                                           maxlength="45"/>
                                </c:when>
                                <c:otherwise>
                                    <input name="employeeSurname" type="text"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-invalid" id="inputSurname1"
                                           maxlength="45"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="employeeUpdating.invalidSurnameMsg"/></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="inputAge1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                                    key="employeeUpdating.age"/></label>
                            <c:choose>
                                <c:when test="${not empty employeeDataNewFields.employeeAge}">
                                    <input name="employeeAge" type="number"
                                           value="${employeeDataNewFields.employeeAge}"
                                           min="18" max="99"
                                           class="form-control is-valid"
                                           id="inputAge1"
                                           aria-describedby="ageHelpBlock1"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="valid-feedback">
                                        <fmt:message key="employeeUpdating.ageDescribing"/>
                                    </div>
                                </c:when>
                                <c:when test="${not empty employeeDataEmptyFields.employeeAge}">
                                    <input name="employeeAge" type="number"
                                           value="${employeeDataEmptyFields.employeeAge}"
                                           min="18" max="99"
                                           class="form-control"
                                           id="inputAge1"
                                           aria-describedby="ageHelpBlock1"/>
                                    <small style="font-family: 'Times New Roman', sans-serif" id="ageHelpBlock1"
                                           class="form-text text-muted"><fmt:message
                                            key="employeeUpdating.ageDescribing"/></small>
                                </c:when>
                                <c:otherwise>
                                    <input name="employeeAge" type="number"
                                           min="18" max="99"
                                           class="form-control is-invalid"
                                           id="inputAge1"
                                           aria-describedby="ageHelpBlock1"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message
                                                key="employeeUpdating.ageDescribing"/></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="inputGender1"
                                   style="font-weight: bold; font-family: 'Times New Roman', sans-serif"><fmt:message
                                    key="employeeUpdating.gender"/></label>
                            <c:choose>
                                <c:when test="${not empty employeeDataNewFields.employeeGender}">
                                    <select id="inputGender1" name="employeeGender" class="form-control is-valid">
                                        <option></option>
                                        <c:choose>
                                            <c:when test="${employeeDataNewFields.employeeGender eq 'M'}">
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="M" selected><fmt:message
                                                        key="employeeUpdating.genderMale"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="F"><fmt:message
                                                        key="employeeUpdating.genderFemale"/></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="M"><fmt:message
                                                        key="employeeUpdating.genderMale"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="F" selected><fmt:message
                                                        key="employeeUpdating.genderFemale"/></option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </c:when>
                                <c:when test="${not empty employeeDataEmptyFields.employeeGender}">
                                    <select id="inputGender1" name="employeeGender" class="form-control">
                                        <option></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="M"><fmt:message key="employeeUpdating.genderMale"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="F"><fmt:message
                                                key="employeeUpdating.genderFemale"/></option>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select id="inputGender1" name="employeeGender" class="form-control is-invalid">
                                        <option></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="M"><fmt:message key="employeeUpdating.genderMale"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="F"><fmt:message
                                                key="employeeUpdating.genderFemale"/></option>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="inputRole1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="employeeUpdating.position"/></label>
                            <c:choose>
                                <c:when test="${not empty employeeDataNewFields.employeePosition}">
                                    <select id="inputRole1" name="employeePosition" class="form-control is-valid">
                                        <option></option>
                                        <c:choose>
                                            <c:when test="${employeeDataNewFields.employeePosition eq 'DOCTOR'}">
                                                <option></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="DOCTOR" selected><fmt:message
                                                        key="employeeUpdating.positionDoctor"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="ASSISTANT"><fmt:message
                                                        key="employeeUpdating.positionAssistant"/></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="DOCTOR"><fmt:message
                                                        key="employeeUpdating.positionDoctor"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="ASSISTANT" selected><fmt:message
                                                        key="employeeUpdating.positionAssistant"/></option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </c:when>
                                <c:when test="${not empty employeeDataEmptyFields.employeePosition}">
                                    <select id="inputRole1" name="employeePosition" class="form-control">
                                        <option></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="DOCTOR"><fmt:message
                                                key="employeeUpdating.positionDoctor"/></option>
                                        <option style="font-weight: bold; font-family: 'Times New Roman', sans-serif"
                                                value="ASSISTANT"><fmt:message
                                                key="employeeUpdating.positionAssistant"/></option>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select id="inputRole1" name="employeePosition" class="form-control is-invalid">
                                        <option></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="DOCTOR"><fmt:message
                                                key="employeeUpdating.positionDoctor"/></option>
                                        <option style="font-weight: bold; font-family: 'Times New Roman', sans-serif"
                                                value="ASSISTANT"><fmt:message
                                                key="employeeUpdating.positionAssistant"/></option>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="inputDate1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                                    key="employeeUpdating.hireDate"/></label>
                            <c:choose>
                                <c:when test="${not empty employeeDataNewFields.hireDate}">
                                    <input name="hireDate" type="text"
                                           pattern="((20)[0-9]{2})-((0[1-9])|(1[0-2]))-((0[1-9])|([1-2][0-9])|(3[0-1]))"
                                           class="form-control is-valid" id="inputDate1"
                                           aria-describedby="dateHelpBlock1"/>
                                    <small id="dateHelpBlock1" style="font-family: 'Times New Roman', sans-serif"
                                         class="form-text text-muted"><fmt:message
                                            key="employeeUpdating.dateDescribing"/></small>
                                </c:when>
                                <c:when test="${not empty employeeDataEmptyFields.hireDate}">
                                    <input name="hireDate" type="text"
                                           pattern="((20)[0-9]{2})-((0[1-9])|(1[0-2]))-((0[1-9])|([1-2][0-9])|(3[0-1]))"
                                           class="form-control" id="inputDate1"
                                           aria-describedby="dateHelpBlock1"/>
                                </c:when>
                                <c:otherwise>
                                    <input name="hireDate" type="text"
                                           pattern="((20)[0-9]{2})-((0[1-9])|(1[0-2]))-((0[1-9])|([1-2][0-9])|(3[0-1]))"
                                           class="form-control is-invalid" id="inputDate1"/>
                                    <div class="invalid-feedback" style="font-family: 'Times New Roman', sans-serif"
                                           class="form-text text-muted"><fmt:message
                                            key="employeeUpdating.dateDescribing"/></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message
                        key="employeeUpdating.submitButton"/></button>
            </form>
        </c:when>
        <c:otherwise>
            <form action="${pageContext.request.contextPath}/mainController" method="post">
                <input type="hidden" name="command" value="update_employee"/>
                <input type="hidden" name="employeeId" value="${employeeInfoId}">
                <div style="background-color: #f4f4f4b8">
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="inputName"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="employeeUpdating.name"/></label>
                            <input name="employeeName" type="text"
                                   pattern="[a-zA-Z\-']{2,45}"
                                   class="form-control"
                                   id="inputName"
                                   maxlength="45"/>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="inputSurname"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="employeeUpdating.surname"/></label>
                            <input name="employeeSurname" type="text"
                                   pattern="[a-zA-Z\-']{2,45}"
                                   class="form-control" id="inputSurname"
                                   maxlength="45"/>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="inputAge"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                                    key="employeeUpdating.age"/></label>
                            <input name="employeeAge" type="number"
                                   min="18" max="99"
                                   class="form-control"
                                   id="inputAge"
                                   aria-describedby="ageHelpBlock"/>
                            <small style="font-family: 'Times New Roman', sans-serif" id="ageHelpBlock"
                                   class="form-text text-muted">
                                <fmt:message key="employeeUpdating.ageDescribing"/>
                            </small>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="inputGender"
                                   style="font-weight: bold; font-family: 'Times New Roman', sans-serif"><fmt:message
                                    key="employeeUpdating.gender"/></label>
                            <select id="inputGender" name="employeeGender" class="form-control">
                                <c:choose>
                                    <c:when test="${employeeDataFields.employeeGender eq 'M'}">
                                        <option></option>
                                        <option style="font-family: 'Times New Roman', sans-serif" value="M" disabled><fmt:message key="employeeUpdating.genderMale"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="F"><fmt:message
                                                key="employeeUpdating.genderFemale"/></option>
                                    </c:when>
                                    <c:when test="${employeeDataFields.employeeGender eq 'F'}">
                                        <option></option>
                                        <option style="font-family: 'Times New Roman', sans-serif" value="M"><fmt:message key="employeeUpdating.genderMale"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif" value="F" disabled><fmt:message
                                                key="employeeUpdating.genderFemale"/></option>
                                    </c:when>
                                    <c:otherwise>
                                        <option></option>
                                        <option style="font-family: 'Times New Roman', sans-serif" value="M"><fmt:message key="employeeUpdating.genderMale"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="F"><fmt:message
                                                key="employeeUpdating.genderFemale"/></option>
                                    </c:otherwise>
                                </c:choose>

                            </select>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="inputRole"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="employeeUpdating.position"/></label>
                            <select id="inputRole" name="employeePosition" class="form-control">
                                <c:choose>
                                    <c:when test="${employeeDataFields.position eq 'DOCTOR'}">
                                        <option></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="DOCTOR" disabled><fmt:message
                                                key="employeeUpdating.positionDoctor"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="ASSISTANT"><fmt:message
                                                key="employeeUpdating.positionAssistant"/></option>
                                    </c:when>
                                    <c:when test="${employeeDataFields.position eq 'ASSISTANT'}">
                                        <option></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="DOCTOR"><fmt:message
                                                key="employeeUpdating.positionDoctor"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="ASSISTANT" disabled><fmt:message
                                                key="employeeUpdating.positionAssistant"/></option>
                                    </c:when>
                                    <c:otherwise>
                                        <option></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="DOCTOR"><fmt:message
                                                key="employeeUpdating.positionDoctor"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="ASSISTANT"><fmt:message
                                                key="employeeUpdating.positionAssistant"/></option>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="inputDate"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                                    key="employeeUpdating.hireDate"/></label>
                            <input name="hireDate" type="text"
                                   pattern="((20)[0-9]{2})-((0[1-9])|(1[0-2]))-((0[1-9])|([1-2][0-9])|(3[0-1]))"
                                   class="form-control" id="inputDate"
                                   aria-describedby="dateHelpBlock"/>
                            <small id="dateHelpBlock" style="font-family: 'Times New Roman', sans-serif"
                                   class="form-text text-muted"><fmt:message
                                    key="employeeUpdating.dateDescribing"/></small>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message
                        key="employeeUpdating.submitButton"/></button>
            </form>
        </c:otherwise>
    </c:choose>
</div>

<%@include file="employee_info_part.jsp" %>

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
