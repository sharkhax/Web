<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="custom_tag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:setBundle basename="locale.pagecontent"/>
<html>
<head>
    <title><fmt:message key="recordInfo.pageTitle"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet" type="text/css">
</head>
<body>

<jsp:include page="../supporting/background.jsp"/>
<%@ include file="../supporting/header.jsp" %>

<div style="background-color: #f4f4f4b8; width: 700px; margin: 230px 0">
    <dl class="row">
        <dt class="col-sm-3">
            <span style="font-family: 'Times New Roman', sans-serif; font-weight: bold">
                <fmt:message key="recordInfo.recordId"/>
            </span>
        </dt>
        <dd class="col-sm-9">
            <span style="font-family: 'Times New Roman', sans-serif">${recordDataFields.recordId}</span>
        </dd>

        <dt class="col-sm-3">
            <span style="font-family: 'Times New Roman', sans-serif; font-weight: bold">
                <fmt:message key="recordInfo.patientId"/>
            </span>
        </dt>
        <dd class="col-sm-9">
            <a href="${pageContext.request.contextPath}/mainController?command=patient_data&requestedPatientInfoId=${recordDataFields.patientId}">
                <span style="font-family: 'Times New Roman', sans-serif">${recordDataFields.patientId}</span>
            </a>
        </dd>

        <dt class="col-sm-3">
            <span style="font-family: 'Times New Roman', sans-serif; font-weight: bold">
                <fmt:message key="recordInfo.attendingDoctorId"/>
            </span>
        </dt>
        <dd class="col-sm-9">
            <c:choose>
                <c:when test="${role eq 'admin_role'}">
                    <a href="${pageContext.request.contextPath}/mainController?command=employee_data&requestedEmployeeInfoId=${recordDataFields.doctorId}">
                        <span style="font-family: 'Times New Roman', sans-serif">${recordDataFields.doctorId}</span>
                    </a>
                </c:when>
                <c:otherwise>
                    <span style="font-family: 'Times New Roman', sans-serif">${recordDataFields.doctorId}</span>
                </c:otherwise>
            </c:choose>
        </dd>

        <dt class="col-sm-3">
            <span style="font-family: 'Times New Roman', sans-serif; font-weight: bold">
                <fmt:message key="recordInfo.treatment"/>
            </span>
        </dt>
        <dd class="col-sm-9">
            <span style="font-family: 'Times New Roman', sans-serif">${recordDataFields.patientTreatment}</span>
        </dd>

        <dt class="col-sm-3">
            <span style="font-family: 'Times New Roman', sans-serif; font-weight: bold">
                <fmt:message key="recordInfo.executorId"/>
            </span>
        </dt>
        <dd class="col-sm-9">
            <c:choose>
                <c:when test="${role eq 'admin_role'}">
                    <c:choose>
                        <c:when test="${not empty recordDataFields.executorId}">
                            <a href="${pageContext.request.contextPath}/mainController?command=employee_data&requestedEmployeeInfoId=${recordDataFields.executorId}">
                                <span style="font-family: 'Times New Roman', sans-serif">${recordDataFields.executorId}</span>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <span style="font-family: 'Times New Roman', sans-serif">-</span>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:when test="${not empty recordDataFields.executorId}">
                    <span style="font-family: 'Times New Roman', sans-serif">${recordDataFields.executorId}</span>
                </c:when>
                <c:otherwise>
                    <span style="font-family: 'Times New Roman', sans-serif">-</span>
                </c:otherwise>
            </c:choose>
        </dd>
        <dt class="col-sm-3">
            <span style="font-family: 'Times New Roman', sans-serif; font-weight: bold">
                <fmt:message key="recordInfo.diagnosis"/>
            </span>
        </dt>
        <dd class="col-sm-9">
            <c:choose>
                <c:when test="${not empty recordDataFields.patientDiagnosis}">
                    <span style="font-family: 'Times New Roman', sans-serif">${recordDataFields.patientDiagnosis}</span>
                </c:when>
                <c:otherwise>
                    <span style="font-family: 'Times New Roman', sans-serif">-</span>
                </c:otherwise>
            </c:choose>
        </dd>
    </dl>
</div>

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
