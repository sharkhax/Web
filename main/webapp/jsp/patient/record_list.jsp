<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="custom_tag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:setBundle basename="locale.pagecontent"/>
<html>
<head>
    <title><fmt:message key="table.recordListPageTitle"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet" type="text/css">
</head>
<body>

<jsp:include page="../supporting/background.jsp"/>
<%@include file="../supporting/header.jsp" %>

<label for="recordList"
       style="font-weight: bold; background-color: #f4f4f4b8; font-family: 'Times New Roman', sans-serif; margin: 13px 10px">
    <fmt:message key="table.patientIdLabel"/> ${patientId}
</label>
<div id="recordList" style="width: 1200px; margin: 13px 10px">
    <ctg:record_list patientId="${patientId}"/>
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
<script type="text/javascript">
    function goToRecordInfo(id) {
        document.location.href = "${pageContext.request.contextPath}/mainController?command=record_data&requestedRecordInfoId=" + id;
    }
</script>

</body>
</html>
