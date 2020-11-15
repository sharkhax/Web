<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:setBundle basename="locale.pagecontent"/>
<html>
<head>
    <title><fmt:message key="patientCreating.pageTitle"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet" type="text/css">
</head>
<body>

<jsp:include page="../supporting/background.jsp"/>
<%@include file="../supporting/header.jsp" %>

<div style="width: 1000px;
    margin: 100px auto">
    <c:choose>
        <c:when test="${validated eq true}">
            <form action="${pageContext.request.contextPath}/mainController" method="post">
                <input type="hidden" name="command" value="register_patient"/>
                <div style="background-color: #f4f4f4b8">
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="inputName"
                                   style="font-weight: bold; font-family: 'Times New Roman', sans-serif">
                                <fmt:message key="patientCreating.name"/></label>
                            <c:choose>
                                <c:when test="${not empty patientCreatingExistingFields.patientName}">
                                    <input name="patientName" type="text" class="form-control is-invalid" id="inputName"
                                           pattern="[a-zA-Z\-']{2,45}" required
                                           value="${patientCreatingExistingFields.patientName}"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="patientCreating.nameExistsMsg"/>
                                    </div>
                                </c:when>
                                <c:when test="${not empty patientCreatingFields.patientName}">
                                    <input name="patientName" type="text" class="form-control is-valid" id="inputName"
                                           pattern="[a-zA-Z\-']{2,45}" required
                                           value="${patientCreatingFields.patientName}"/>
                                </c:when>
                                <c:otherwise>
                                    <input name="patientName" type="text" class="form-control is-invalid" id="inputName"
                                           pattern="[a-zA-Z\-']{2,45}" required/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="patientCreating.invalidNameMsg"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="inputSurname"
                                   style="font-weight: bold; font-family: 'Times New Roman', sans-serif">
                                <fmt:message key="patientCreating.surname"/></label>
                            <c:choose>
                                <c:when test="${not empty patientCreatingExistingFields.patientSurname}">
                                    <input name="patientSurname" type="text" class="form-control is-invalid"
                                           id="inputSurname" pattern="[a-zA-Z\-']{2,45}" required
                                           value="${patientCreatingExistingFields.patientSurname}"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="patientCreating.nameExistsMsg"/>
                                    </div>
                                </c:when>
                                <c:when test="${not empty patientCreatingFields.patientSurname}">
                                    <input name="patientSurname" type="text" class="form-control is-valid"
                                           id="inputSurname" pattern="[a-zA-Z\-']{2,45}" required
                                           value="${patientCreatingFields.patientSurname}"/>
                                </c:when>
                                <c:otherwise>
                                    <input name="patientSurname" type="text" class="form-control is-invalid"
                                           id="inputSurname" pattern="[a-zA-Z\-']{2,45}" required/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="patientCreating.invalidNameMsg"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="inputAge"
                                   style="font-weight: bold; font-family: 'Times New Roman', sans-serif">
                                <fmt:message key="patientCreating.age"/></label>
                            <c:choose>
                                <c:when test="${not empty patientCreatingFields.patientAge}">
                                    <input name="patientAge" type="number" min="18" max="99"
                                           class="form-control is-valid" id="inputAge" required
                                           value="${patientCreatingFields.patientAge}"/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="valid-feedback">
                                        <fmt:message key="patientCreating.ageDescribing"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <input name="patientAge" type="number" min="18" max="99"
                                           class="form-control is-invalid" id="inputAge" required/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="patientCreating.ageDescribing"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                        </div>
                        <div class="form-group col-md-3">
                            <label for="inputGender"
                                   style="font-weight: bold; font-family: 'Times New Roman', sans-serif">
                                <fmt:message key="patientCreating.gender"/>
                            </label>
                            <c:choose>
                                <c:when test="${not empty patientCreatingFields.patientGender}">
                                    <select id="inputGender" name="patientGender"
                                            class="form-control is-valid" required>
                                        <c:choose>
                                            <c:when test="${patientCreatingFields.patientGender eq 'F'}">
                                                <option style="font-family: 'Times New Roman', sans-serif" value="M">
                                                    <fmt:message key="userReg.genderMale"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif" value="F"
                                                        selected>
                                                    <fmt:message key="userReg.genderFemale"/></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option style="font-family: 'Times New Roman', sans-serif" value="M"
                                                        selected>
                                                    <fmt:message key="userReg.genderMale"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif" value="F">
                                                    <fmt:message key="userReg.genderFemale"/></option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select id="inputGender" name="patientGender"
                                            class="form-control is-invalid" required>
                                        <option style="font-family: 'Times New Roman', sans-serif" value="M" selected>
                                            <fmt:message key="userReg.genderMale"/>
                                        </option>
                                        <option style="font-family: 'Times New Roman', sans-serif" value="F">
                                            <fmt:message key="userReg.genderFemale"/>
                                        </option>
                                    </select>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="patientCreating.invalidGenderMsg"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="patientCreating.submitButton"/></button>
            </form>
        </c:when>
        <c:otherwise>
            <form action="${pageContext.request.contextPath}/mainController" method="post">
                <input type="hidden" name="command" value="register_patient"/>
                <div style="background-color: #f4f4f4b8">
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="inputName1"
                                   style="font-weight: bold; font-family: 'Times New Roman', sans-serif">
                                <fmt:message key="patientCreating.name"/></label>
                            <input name="patientName" type="text" class="form-control" id="inputName1"
                                   pattern="[a-zA-Z\-']{2,45}" value="${patientCreatingFields.patientName}" required/>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="inputSurname1"
                                   style="font-weight: bold; font-family: 'Times New Roman', sans-serif">
                                <fmt:message key="patientCreating.surname"/></label>
                            <input name="patientSurname" type="text" class="form-control" id="inputSurname1"
                                   pattern="[a-zA-Z\-']{2,45}" value="${patientCreatingFields.patientSurname}"
                                   required/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="inputAge1"
                                   style="font-weight: bold; font-family: 'Times New Roman', sans-serif">
                                <fmt:message key="patientCreating.age"/></label>
                            <input name="patientAge" type="number" min="18" max="99"
                                   class="form-control"
                                   id="inputAge1"
                                   aria-describedby="ageHelpBlock1"
                                   value="${patientCreatingFields.patientAge}" required/>
                            <small style="font-family: 'Times New Roman', sans-serif" id="ageHelpBlock1"
                                   class="form-text text-muted"><fmt:message
                                    key="patientCreating.ageDescribing"/></small>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="inputGender1"
                                   style="font-weight: bold; font-family: 'Times New Roman', sans-serif"><fmt:message
                                    key="patientCreating.gender"/></label>
                            <select id="inputGender1" name="patientGender" class="form-control" required>
                                <c:choose>
                                    <c:when test="${patientCreatingFields.patientGender eq 'F'}">
                                        <option style="font-family: 'Times New Roman', sans-serif" value="M">
                                            <fmt:message key="userReg.genderMale"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif" value="F" selected>
                                            <fmt:message key="userReg.genderFemale"/></option>
                                    </c:when>
                                    <c:otherwise>
                                        <option style="font-family: 'Times New Roman', sans-serif" value="M" selected>
                                            <fmt:message key="userReg.genderMale"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif" value="F">
                                            <fmt:message key="userReg.genderFemale"/></option>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="patientCreating.submitButton"/></button>
                <c:if test="${patientCreatingSuccess eq true}">
                    <br/><span style="color: white; font-size: 14pt; font-weight: bold">
                        <fmt:message key="patientCreating.success"/>
                        </span>
                </c:if>
            </form>
        </c:otherwise>
    </c:choose>
</div>

<%@include file="../supporting/footer.jsp" %>

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
