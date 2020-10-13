<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:setBundle basename="locale.pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand"><fmt:message key="header.hospital"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <c:choose>
                    <c:when test="${is_main_page eq true}">
                        <a class="nav-link disabled" href="${pageContext.request.contextPath}/mainPage"><fmt:message key="header.mainPage"/><span class="sr-only">(current)</span></a>
                    </c:when>
                    <c:otherwise>
                        <a class="nav-link" href="${pageContext.request.contextPath}/mainPage"><fmt:message key="header.mainPage"/><span class="sr-only">(current)</span></a>
                    </c:otherwise>
                </c:choose>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-globe" fill="currentColor"
                         xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd"
                              d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm7.5-6.923c-.67.204-1.335.82-1.887 1.855A7.97 7.97 0 0 0 5.145 4H7.5V1.077zM4.09 4H2.255a7.025 7.025 0 0 1 3.072-2.472 6.7 6.7 0 0 0-.597.933c-.247.464-.462.98-.64 1.539zm-.582 3.5h-2.49c.062-.89.291-1.733.656-2.5H3.82a13.652 13.652 0 0 0-.312 2.5zM4.847 5H7.5v2.5H4.51A12.5 12.5 0 0 1 4.846 5zM8.5 5v2.5h2.99a12.495 12.495 0 0 0-.337-2.5H8.5zM4.51 8.5H7.5V11H4.847a12.5 12.5 0 0 1-.338-2.5zm3.99 0V11h2.653c.187-.765.306-1.608.338-2.5H8.5zM5.145 12H7.5v2.923c-.67-.204-1.335-.82-1.887-1.855A7.97 7.97 0 0 1 5.145 12zm.182 2.472a6.696 6.696 0 0 1-.597-.933A9.268 9.268 0 0 1 4.09 12H2.255a7.024 7.024 0 0 0 3.072 2.472zM3.82 11H1.674a6.958 6.958 0 0 1-.656-2.5h2.49c.03.877.138 1.718.312 2.5zm6.853 3.472A7.024 7.024 0 0 0 13.745 12H11.91a9.27 9.27 0 0 1-.64 1.539 6.688 6.688 0 0 1-.597.933zM8.5 12h2.355a7.967 7.967 0 0 1-.468 1.068c-.552 1.035-1.218 1.65-1.887 1.855V12zm3.68-1h2.146c.365-.767.594-1.61.656-2.5h-2.49a13.65 13.65 0 0 1-.312 2.5zm2.802-3.5h-2.49A13.65 13.65 0 0 0 12.18 5h2.146c.365.767.594 1.61.656 2.5zM11.27 2.461c.247.464.462.98.64 1.539h1.835a7.024 7.024 0 0 0-3.072-2.472c.218.284.418.598.597.933zM10.855 4H8.5V1.077c.67.204 1.335.82 1.887 1.855.173.324.33.682.468 1.068z"></path>
                    </svg>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <form action="${pageContext.request.contextPath}/mainController" method="post">
                        <input type="hidden" name="command" value="change_locale">
                        <c:choose>
                            <c:when test="${currentLocale eq 'ru_BY'}">
                                <button type="submit" class="dropdown-item" name="currentLocale" value="en_EN">English
                                </button>
                                <button type="submit" class="dropdown-item disabled" name="currentLocale" value="ru_BY">
                                    Русский
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button type="submit" class="dropdown-item disabled" name="currentLocale" value="en_EN">English
                                </button>
                                <button type="submit" class="dropdown-item" name="currentLocale" value="ru_BY">
                                    Русский
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </form>
                </div>
            </li>
        </ul>
        <form action="mainController" method="post" class="form-inline my-2 my-lg-0">
            <input type="hidden" name="command" value="logout">
            <span class="nav-link"><fmt:message key="header.loggedAs"/> ${login_info.login}.    </span>
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message
                    key="header.logoutButton"/></button>
        </form>
    </div>
</nav>

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
