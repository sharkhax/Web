<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="custom_tag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:setBundle basename="locale.pagecontent"/>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet" type="text/css">
</head>
<body>

<div style="background-color: #f4f4f4b8; width: 700px; margin-left: 2px">
    <dl class="row">
        <dt class="col-sm-3">
            <span style="font-family: 'Times New Roman', sans-serif; font-weight: bold">
                <fmt:message key="patientInfo.patientId"/>
            </span>
        </dt>
        <dd class="col-sm-9">
            <span style="font-family: 'Times New Roman', sans-serif">${patientDataFields.patientId}</span>
        </dd>

        <dt class="col-sm-3">
            <span style="font-family: 'Times New Roman', sans-serif; font-weight: bold">
                <fmt:message key="patientInfo.name"/>
            </span>
        </dt>
        <dd class="col-sm-9">
            <span style="font-family: 'Times New Roman', sans-serif">${patientDataFields.patientName}</span>
        </dd>

        <dt class="col-sm-3">
            <span style="font-family: 'Times New Roman', sans-serif; font-weight: bold">
                <fmt:message key="patientInfo.surname"/>
            </span>
        </dt>
        <dd class="col-sm-9">
            <span style="font-family: 'Times New Roman', sans-serif">${patientDataFields.patientSurname}</span>
        </dd>

        <dt class="col-sm-3">
            <span style="font-family: 'Times New Roman', sans-serif; font-weight: bold">
                <fmt:message key="patientInfo.age"/>
            </span>
        </dt>
        <dd class="col-sm-9">
            <span style="font-family: 'Times New Roman', sans-serif">${patientDataFields.patientAge}</span>
        </dd>

        <dt class="col-sm-3">
            <span style="font-family: 'Times New Roman', sans-serif; font-weight: bold">
                <fmt:message key="patientInfo.gender"/>
            </span>
        </dt>
        <dd class="col-sm-9">
            <span style="font-family: 'Times New Roman', sans-serif">${patientDataFields.patientGender}</span>
        </dd>

        <dt class="col-sm-3">
            <span style="font-family: 'Times New Roman', sans-serif; font-weight: bold">
                <fmt:message key="patientInfo.diagnosis"/>
            </span>
        </dt>
        <dd class="col-sm-9">
            <span style="font-family: 'Times New Roman', sans-serif">
                <c:choose>
                    <c:when test="${not empty patientDataFields.patientDiagnosis}">
                        <span style="font-family: 'Times New Roman', sans-serif">${patientDataFields.patientDiagnosis}</span>
                    </c:when>
                    <c:otherwise>
                        <span style="font-family: 'Times New Roman', sans-serif">-</span>
                    </c:otherwise>
                </c:choose>
            </span>
        </dd>

        <dt class="col-sm-3">
            <span style="font-family: 'Times New Roman', sans-serif; font-weight: bold">
                <fmt:message key="patientInfo.status"/>
            </span>
        </dt>
        <dd class="col-sm-9">
            <span style="font-family: 'Times New Roman', sans-serif">${patientDataFields.patientStatus}</span>
        </dd>

        <dt class="col-sm-3">
            <span style="font-family: 'Times New Roman', sans-serif; font-weight: bold">
                <fmt:message key="patientInfo.lastRecordId"/>
            </span>
        </dt>
        <dd class="col-sm-9">
            <c:choose>
                <c:when test="${patientDataFields.lastRecordId ne '-'}">
                    <a href="${pageContext.request.contextPath}/mainController?command=record_data&requestedRecordInfoId=${patientDataFields.lastRecordId}">
                        <span style="font-family: 'Times New Roman', sans-serif">${patientDataFields.lastRecordId}</span>
                    </a>
                </c:when>
                <c:otherwise>
                    <span style="font-family: 'Times New Roman', sans-serif">${patientDataFields.lastRecordId}</span>
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
