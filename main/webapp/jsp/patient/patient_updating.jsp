<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:setBundle basename="locale.pagecontent"/>
<html>
<head>
    <title><fmt:message key="patientUpdating.pageTitle"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="../supporting/background.jsp"/>
<%@ include file="../supporting/header.jsp" %>

<div style="width: 1000px;
    margin: 100px auto">
    <c:choose>
        <c:when test="${validated eq true}">
            <form action="${pageContext.request.contextPath}/mainController" method="post">
                <input type="hidden" name="command" value="update_patient"/>
                <input type="hidden" name="patientId" value="${patientInfoId}">
                <div style="background-color: #f4f4f4b8">
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="inputName1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="patientUpdating.name"/></label>
                            <c:choose>
                                <c:when test="${not empty patientDataExistingFields.patientName}">
                                    <input name="patientName" type="text"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-invalid"
                                           id="inputName1"
                                           value="${patientDataExistingFields.patientName}"
                                           maxlength="45"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="patientUpdating.nameExists"/></div>
                                </c:when>
                                <c:when test="${not empty patientDataNewFields.patientName}">
                                    <input name="patientName" type="text"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-valid"
                                           id="inputName1"
                                           value="${patientDataNewFields.patientName}"
                                           maxlength="45"/>
                                </c:when>
                                <c:when test="${not empty patientDataEmptyFields.patientName}">
                                    <input name="patientName" type="text"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control"
                                           id="inputName1"
                                           maxlength="45"/>
                                </c:when>
                                <c:otherwise>
                                    <input name="patientName" type="text"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-invalid"
                                           id="inputName1"
                                           maxlength="45"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="patientUpdating.invalidNameMsg"/></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="inputSurname1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="patientUpdating.surname"/></label>
                            <c:choose>
                                <c:when test="${not empty patientDataExistingFields.patientSurname}">
                                    <input name="patientSurname" type="text"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-invalid" id="inputSurname1"
                                           maxlength="45" value="${patientDataExistingFields.patientSurname}"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="patientUpdating.nameExists"/></div>
                                </c:when>
                                <c:when test="${not empty patientDataNewFields.patientSurname}">
                                    <input name="patientSurname" type="text"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-valid" id="inputSurname1"
                                           maxlength="45" value="${patientDataNewFields.patientSurname}"/>
                                </c:when>
                                <c:when test="${not empty patientDataEmptyFields.patientSurname}">
                                    <input name="patientSurname" type="text"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control" id="inputSurname1"
                                           maxlength="45"/>
                                </c:when>
                                <c:otherwise>
                                    <input name="patientSurname" type="text"
                                           pattern="[a-zA-Z\-']{2,45}"
                                           class="form-control is-invalid" id="inputSurname1"
                                           maxlength="45"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="patientUpdating.invalidSurnameMsg"/></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="inputAge1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                                    key="patientUpdating.age"/></label>
                            <c:choose>
                                <c:when test="${not empty patientDataNewFields.patientAge}">
                                    <input name="patientAge" type="number"
                                           min="18" max="99"
                                           class="form-control is-valid"
                                           id="inputAge1"
                                           value="${patientDataNewFields.patientAge}"
                                           aria-describedby="ageHelpBlock1"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="valid-feedback">
                                        <fmt:message key="patientUpdating.ageDescribing"/>
                                    </div>
                                </c:when>
                                <c:when test="${not empty patientDataEmptyFields.patientAge}">
                                    <input name="patientAge" type="number"
                                           min="18" max="99"
                                           class="form-control"
                                           id="inputAge1"
                                           aria-describedby="ageHelpBlock1"/>
                                    <small style="font-family: 'Times New Roman', sans-serif" id="ageHelpBlock1"
                                           class="form-text text-muted">
                                        <fmt:message key="patientUpdating.ageDescribing"/>
                                    </small>
                                </c:when>
                                <c:otherwise>
                                    <input name="patientAge" type="number"
                                           min="18" max="99"
                                           class="form-control is-invalid"
                                           id="inputAge1"
                                           aria-describedby="ageHelpBlock1"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="patientUpdating.ageDescribing"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="inputGender1"
                                   style="font-weight: bold; font-family: 'Times New Roman', sans-serif"><fmt:message
                                    key="patientUpdating.gender"/></label>
                            <c:choose>
                                <c:when test="${not empty patientDataNewFields.patientGender}">
                                    <select id="inputGender1" name="patientGender" class="form-control is-valid">
                                        <option selected></option>
                                        <c:choose>
                                            <c:when test="${patientDataFields.patientGender eq 'M'}">
                                                <option style="font-family: 'Times New Roman', sans-serif" value="M"
                                                        disabled>
                                                    <fmt:message key="patientUpdating.genderMale"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="F"><fmt:message
                                                        key="patientUpdating.genderFemale"/></option>
                                            </c:when>
                                            <c:when test="${patientDataFields.patientGender eq 'F'}">
                                                <option style="font-family: 'Times New Roman', sans-serif" value="M">
                                                    <fmt:message key="patientUpdating.genderMale"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif" value="F"
                                                        disabled>
                                                    <fmt:message
                                                            key="patientUpdating.genderFemale"/></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option style="font-family: 'Times New Roman', sans-serif" value="M">
                                                    <fmt:message key="patientUpdating.genderMale"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="F"><fmt:message
                                                        key="patientUpdating.genderFemale"/></option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </c:when>
                                <c:when test="${not empty patientDataEmptyFields.patientGender}">
                                    <select id="inputGender1" name="patientGender" class="form-control">
                                        <option selected></option>
                                        <c:choose>
                                            <c:when test="${patientDataFields.patientGender eq 'M'}">
                                                <option style="font-family: 'Times New Roman', sans-serif" value="M"
                                                        disabled>
                                                    <fmt:message key="patientUpdating.genderMale"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="F"><fmt:message
                                                        key="patientUpdating.genderFemale"/></option>
                                            </c:when>
                                            <c:when test="${patientDataFields.patientGender eq 'F'}">
                                                <option style="font-family: 'Times New Roman', sans-serif" value="M">
                                                    <fmt:message key="patientUpdating.genderMale"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif" value="F"
                                                        disabled>
                                                    <fmt:message
                                                            key="patientUpdating.genderFemale"/></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option style="font-family: 'Times New Roman', sans-serif" value="M">
                                                    <fmt:message key="patientUpdating.genderMale"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="F"><fmt:message
                                                        key="patientUpdating.genderFemale"/></option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select id="inputGender1" name="patientGender" class="form-control is-invalid">
                                        <option selected></option>
                                        <c:choose>
                                            <c:when test="${patientDataFields.patientGender eq 'M'}">
                                                <option style="font-family: 'Times New Roman', sans-serif" value="M"
                                                        disabled>
                                                    <fmt:message key="patientUpdating.genderMale"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="F"><fmt:message
                                                        key="patientUpdating.genderFemale"/></option>
                                            </c:when>
                                            <c:when test="${patientDataFields.patientGender eq 'F'}">
                                                <option style="font-family: 'Times New Roman', sans-serif" value="M">
                                                    <fmt:message key="patientUpdating.genderMale"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif" value="F"
                                                        disabled>
                                                    <fmt:message
                                                            key="patientUpdating.genderFemale"/></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option style="font-family: 'Times New Roman', sans-serif" value="M">
                                                    <fmt:message key="patientUpdating.genderMale"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="F"><fmt:message
                                                        key="patientUpdating.genderFemale"/></option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message
                        key="patientUpdating.submitButton"/></button>
            </form>
        </c:when>
        <c:otherwise>
            <form action="${pageContext.request.contextPath}/mainController" method="post">
                <input type="hidden" name="command" value="update_patient"/>
                <input type="hidden" name="patientId" value="${patientInfoId}">
                <div style="background-color: #f4f4f4b8">
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="inputName"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="patientUpdating.name"/></label>
                            <input name="patientName" type="text"
                                   pattern="[a-zA-Z\-']{2,45}"
                                   class="form-control"
                                   id="inputName"
                                   maxlength="45"/>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="inputSurname"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold "><fmt:message
                                    key="patientUpdating.surname"/></label>
                            <input name="patientSurname" type="text"
                                   pattern="[a-zA-Z\-']{2,45}"
                                   class="form-control" id="inputSurname"
                                   maxlength="45"/>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="inputAge"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold"><fmt:message
                                    key="patientUpdating.age"/></label>
                            <input name="patientAge" type="number"
                                   min="18" max="99"
                                   class="form-control"
                                   id="inputAge"
                                   aria-describedby="ageHelpBlock"/>
                            <small style="font-family: 'Times New Roman', sans-serif" id="ageHelpBlock"
                                   class="form-text text-muted">
                                <fmt:message key="patientUpdating.ageDescribing"/>
                            </small>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="inputGender"
                                   style="font-weight: bold; font-family: 'Times New Roman', sans-serif"><fmt:message
                                    key="patientUpdating.gender"/></label>
                            <select id="inputGender" name="patientGender" class="form-control">
                                <option></option>
                                <c:choose>
                                    <c:when test="${patientDataFields.patientGender eq 'M'}">
                                        <option style="font-family: 'Times New Roman', sans-serif" value="M" disabled>
                                            <fmt:message key="patientUpdating.genderMale"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="F"><fmt:message
                                                key="patientUpdating.genderFemale"/></option>
                                    </c:when>
                                    <c:when test="${patientDataFields.patientGender eq 'F'}">
                                        <option style="font-family: 'Times New Roman', sans-serif" value="M">
                                            <fmt:message key="patientUpdating.genderMale"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif" value="F" disabled>
                                            <fmt:message
                                                    key="patientUpdating.genderFemale"/></option>
                                    </c:when>
                                    <c:otherwise>
                                        <option style="font-family: 'Times New Roman', sans-serif" value="M">
                                            <fmt:message key="patientUpdating.genderMale"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif"
                                                value="F"><fmt:message
                                                key="patientUpdating.genderFemale"/></option>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message
                        key="patientUpdating.submitButton"/></button>
            </form>
        </c:otherwise>
    </c:choose>
</div>

<%@include file="patient_info_part.jsp" %>

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
