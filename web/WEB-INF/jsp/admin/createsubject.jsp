<%--
  Created by IntelliJ IDEA.
  User: zakharov_ga
  Date: 08.07.2015
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>

<head>
    <%--<meta http-equiv="content-type" content="text/html; charset=UTF-8">--%>
    <%--<meta charset="utf-8">--%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <%--<link rel="shortcut icon" href="http://bootstrap-3.ru/assets/ico/favicon.ico">--%>

    <title>Create new subject</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value='/resources/css/bootstrap.css'></c:url>" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:url value='/resources/css/style.css'></c:url>" rel="stylesheet" type="text/css"/>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><os-p>onlinecourses<small>.com</small></os-p></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <sec:authorize access="not isAuthenticated()">
                <form class="navbar-form navbar-right" method="post" action = "<c:url value='/login'></c:url>" role="form">
                    <div class="form-group">
                        <input type="text" placeholder="Email" class="form-control" name="username"/>
                    </div>
                    <div class="form-group">
                        <input type="password" placeholder="Password" class="form-control" name="password"/>
                    </div>
                    <button type="submit" class="btn btn-success">Sign in</button>
                </form>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="<c:url value='/logout'></c:url>"><span class="glyphicon glyphicon-log-out"></span> Log out</a></li>
                </ul>
            </sec:authorize>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<div class="container-fluid">

    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li id="courses"><a href="<c:url value='/admin/courses'></c:url>">Courses</a></li>
                <li id="subjects"><a href="<c:url value='/admin/subjects'></c:url>">Subjects</a></li>
                <li id="teachers"><a href="<c:url value='/admin/teachers'></c:url>">Teachers</a></li>
                <li id="news"><a href="<c:url value='/admin/news'></c:url>">News</a></li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">Create new subject</h1>
            <form:form method="post" class="form-horizontal" modelAttribute="createSubjectForm">
                <c:choose>
                    <c:when test="${c == null}">
                        <div class="form-group">
                            <form:label path="code" class="col-xs-4 control-label">Subject Code</form:label>
                            <div class="col-xs-8">
                                <form:input class="form-control" path="code"/>
                            </div>
                            <form:errors path="code" cssClass="errors" />
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="form-group">
                            <form:input type="hidden" class="form-control" path="code"/>
                        </div>
                    </c:otherwise>
                </c:choose>
                <div class="form-group">
                    <form:label path="name" class="col-xs-4 control-label">Subject Name</form:label>
                    <div class="col-xs-8">
                        <form:input class="form-control" path="name"/>
                    </div>
                    <form:errors path="name" cssClass="errors" />
                </div>
                <div class="form-group">
                    <form:label path="price" class="col-xs-4 control-label">Price</form:label>
                    <div class="col-xs-8">
                        <form:input class="form-control" path="price"/>
                    </div>
                    <form:errors path="price" cssClass="errors" />
                </div>
                <div class="form-group">
                    <form:label path="description" class="col-xs-4 control-label">Description</form:label>
                    <div class="col-xs-8">
                        <form:textarea class="form-control" path="description"/>
                    </div>
                    <form:errors path="description" cssClass="errors" />
                </div>
                <div class="form-group">
                    <div class="col-xs-offset-4 col-xs-10">
                        <input type="submit" value="Save" class="btn btn-default"/>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
<!-- /.container -->

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="<c:url value='/resources/js/jquery.js'></c:url>"></script>
<script src="<c:url value='/resources/js/bootstrap.js'></c:url>"></script>

</body>
</html>