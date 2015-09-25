<%--
  Created by IntelliJ IDEA.
  User: zakharov_ga
  Date: 08.07.2015
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                <p class = "navbar-right"><a href="<c:url value='/logout'></c:url>" class=" btn btn-lg" role="button">Log out</a></p>
            </sec:authorize>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<div class="jumbotron">
    <div class="container">
        <h2><span class="label label-default">onlinecourses</span><small>.com</small></h2>
        <p>The best online university</p>
    </div>
</div>

<div class="container">

    <div class="starter-template">
        <h1>Welcome to onlinecourses.com!</h1>
    </div>
    <table class="table table-hover">
        <caption>${subject.name}</caption>
        <tbody>
            <tr>
                <td><p>Description</p></td>
                <td><p>${subject.description}</p></td>
            </tr>
            <tr>
                <td><p>Our Teachers</p></td>
                <td>
                    <c:forEach var="teacher" items="${teachers}">
                        <p><a href="<c:url value='/teachers/${teacher.id}'></c:url>"><span>${teacher.lastName}</span> <span>${teacher.firstName}</span></a></p>
                    </c:forEach>
                </td>
            </tr>
        </tbody>
    </table>

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