<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="custom_tag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:setBundle basename="locale.pagecontent"/>
<html>
<head>
    <title><fmt:message key="recordCreating.pageTitle"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet" type="text/css">
</head>
<body>

<jsp:include page="../supporting/background.jsp"/>
<%@ include file="../supporting/header.jsp" %>

<div style="width: 1000px;
    margin: 100px auto">
    <form action="${pageContext.request.contextPath}/mainController" method="post">
        <input type="hidden" name="command" value="create_record"/>
        <input type="hidden" name="patientId" value="${recordCreating.patientId}"/>
        <input type="hidden" name="doctorId" value="${recordCreating.doctorId}"/>
        <div style="background-color: #f4f4f4b8">
            <div class="form-row">
                <div class="form-group col-md-3">
                    <label for="patientName" style="font-family: 'Times New Roman', sans-serif; font-weight: bold ">
                        <fmt:message key="recordCreating.patientLabel"/>
                    </label>
                    <br/>
                    <span id="patientName"
                          style="font-family: 'Times New Roman', sans-serif">${recordCreating.patientName} ${recordCreating.patientSurname}</span>
                </div>
                <div class="form-group col-md-3">
                    <label for="doctorName" style="font-family: 'Times New Roman', sans-serif; font-weight: bold ">
                        <fmt:message key="recordCreating.attendingDoctor"/>
                    </label>
                    <br/>
                    <span id="doctorName"
                          style="font-family: 'Times New Roman', sans-serif">${recordCreating.doctorName} ${recordCreating.doctorSurname}</span>
                </div>
                <c:choose>
                    <c:when test="${validated eq true}">
                        <div class="form-group col-md-3">
                            <label for="diagnosis1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold ">
                                <fmt:message key="recordCreating.diagnosisLabel"/>
                            </label>
                            <c:choose>
                                <c:when test="${not empty recordCreatingFields.patientDiagnosis}">
                                    <input class="form-control is-valid" name="patientDiagnosis" type="text"
                                           pattern="[a-zA-Z\-', ]{2,45}"
                                           value="${recordCreatingFields.patientDiagnosis}"
                                           id="diagnosis1" required/>
                                </c:when>
                                <c:otherwise>
                                    <input class="form-control is-invalid" name="patientDiagnosis" type="text"
                                           pattern="[a-zA-Z\-', ]{2,45}"
                                           id="diagnosis1" required/>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="recordCreating.invalidDiagnosisMsg"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="treatment1"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold ">
                                <fmt:message key="recordCreating.treatment"/>
                            </label>
                            <c:choose>
                                <c:when test="${not empty recordCreatingFields.patientTreatment}">
                                    <select id="treatment1" name="patientTreatment" class="form-control is-valid"
                                            required>
                                        <c:choose>
                                            <c:when test="${recordCreatingFields.patientTreatment eq 'PROCEDURE'}">
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="PROCEDURE" selected>
                                                    <fmt:message
                                                            key="recordCreating.procedure"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="SURGERY"><fmt:message
                                                        key="recordCreating.surgery"/></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="PROCEDURE">
                                                    <fmt:message
                                                            key="recordCreating.procedure"/></option>
                                                <option style="font-family: 'Times New Roman', sans-serif"
                                                        value="SURGERY" selected><fmt:message
                                                        key="recordCreating.surgery"/></option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select id="treatment1" name="patientTreatment" class="form-control is-invalid"
                                            required>
                                        <option style="font-family: 'Times New Roman', sans-serif" value="PROCEDURE">
                                            <fmt:message
                                                    key="recordCreating.procedure"/></option>
                                        <option style="font-family: 'Times New Roman', sans-serif" value="SURGERY">
                                            <fmt:message
                                                    key="recordCreating.surgery"/></option>
                                    </select>
                                    <div style="font-family: 'Times New Roman', sans-serif" class="invalid-feedback">
                                        <fmt:message key="recordCreating.invalidTreatmentMsg"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="form-group col-md-3">
                            <label for="diagnosis"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold ">
                                <fmt:message key="recordCreating.diagnosisLabel"/>
                            </label>
                            <input class="form-control" name="patientDiagnosis" type="text"
                                   pattern="[a-zA-Z\-', ]{2,45}"
                                   id="diagnosis" required/>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="treatment"
                                   style="font-family: 'Times New Roman', sans-serif; font-weight: bold ">
                                <fmt:message key="recordCreating.treatment"/>
                            </label>
                            <select id="treatment" name="patientTreatment" class="form-control" required>
                                <option style="font-family: 'Times New Roman', sans-serif" value="PROCEDURE">
                                    <fmt:message
                                            key="recordCreating.procedure"/></option>
                                <option style="font-family: 'Times New Roman', sans-serif" value="SURGERY"><fmt:message
                                        key="recordCreating.surgery"/></option>
                            </select>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <button type="submit" class="btn btn-primary"><fmt:message
                key="recordCreating.submitButton"/></button>
    </form>
</div>

<%@ include file="../supporting/footer.jsp" %>
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
