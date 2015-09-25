<%--
  Created by IntelliJ IDEA.
  User: zakhar
  Date: 19.07.2015
  Time: 20:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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

    <title>Onlinecourses home page</title>

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
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">
                <os-p>onlinecourses
                    <small>.com</small>
                </os-p>
            </a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <sec:authorize access="not isAuthenticated()">
                <form class="navbar-form navbar-right" method="post" action="<c:url value='/login'></c:url>"
                      role="form">
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
                    <li><a href="<c:url value='/logout'></c:url>"><span class="glyphicon glyphicon-log-out"></span> Log
                        out</a></li>
                        <%--<li><sec:authentication property="firstName"></sec:authentication></li>--%>
                    <li><sec:authentication property="principal.lastName"></sec:authentication></li>
                </ul>
            </sec:authorize>
        </div>
        <!--/.nav-collapse -->
    </div>
</nav>

<div class="jumbotron">
    <div class="container">
        <h2>The best online university</h2>
    </div>
</div>

<div class="container">
    <!-- Example row of columns -->
    <div class="row">
        <div class="col-md-8">
            <div class="panel panel-default">
                <div class="panel-heading">${course.subject.name} on ${course.startDate}</div>
                <div class="panel-body">
                    <form class="navbar-form navbar-right" method="post" action = "<c:url value='/teacher/courses/${courseId}/${userId}/rate'></c:url>" role="form">
                        <div class="form-group">
                            <label class="col-xs-2 control-label">Student</label>
                            <div class="form-group">
                                <input type="text" placeholder="Mark" class="form-control" name="mark"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-success">Rate</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-default">
                <!-- Default panel contents -->
                <div class="panel-heading">News</div>
                <div class="panel-body">
                    <p>Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text
                        Text Text Text Text Text Text Text Text </p>

                    <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /.container -->

<footer class="footer">
    <div class="container">
        <p class="text-muted">Place sticky footer content here.</p>
    </div>
</footer>


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="<c:url value='/resources/js/jquery.js'></c:url>"></script>
<script src="<c:url value='/resources/js/bootstrap.js'></c:url>"></script>

</body>
</html>