<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 03.07.2015
  Time: 22:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

    <title>Onlinecourses home page</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value='/resources/css/bootstrap.css'></c:url>" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap-datetimepicker.min.css'></c:url>" rel="stylesheet">

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

    <%--<div class="starter-template">--%>
        <form:form method="post" class="form-horizontal" modelAttribute="registerForm">
            <div class="form-group">
                <form:label path="email" class="col-xs-2 control-label">Email</form:label>
                <div class="col-xs-10">
                    <form:input class="form-control" path="email"/>
                </div>
                <form:errors path="email" cssClass="errors" />
            </div>
            <div class="form-group">
                <form:label path="lastName" class="col-xs-2 control-label">Last Name</form:label>
                <div class="col-xs-10">
                    <form:input class="form-control" path="lastName"/>
                </div>
                <form:errors path="lastName" cssClass="errors" />
            </div>
            <div class="form-group">
                <form:label path="firstName" class="col-xs-2 control-label">First Name</form:label>
                <div class="col-xs-10">
                    <form:input class="form-control" path="firstName"/>
                </div>
                <form:errors path="firstName" cssClass="errors" />
            </div>
            <div class="form-group">
                <form:label path="phoneNumber" class="col-xs-2 control-label">Phone Number</form:label>
                <div class="col-xs-10">
                    <form:input class="form-control" path="phoneNumber"/>
                </div>
                <form:errors path="phoneNumber" cssClass="errors" />
            </div>
            <div class="form-group">
                <form:label path="country" class="col-xs-2 control-label">Country</form:label>
                <div class="col-xs-10">
                    <form:input class="form-control" path="country"/>
                </div>
                <form:errors path="country" cssClass="errors" />
            </div>
            <div class="form-group">
                <form:label path="city" class="col-xs-2 control-label">City</form:label>
                <div class="col-xs-10">
                    <form:input class="form-control" path="city"/>
                </div>
                <form:errors path="city" cssClass="errors" />
            </div>
            <div class="form-group">
                <form:label path="birthDate" class="col-xs-2 control-label">Birth Date</form:label>
                <div class="col-xs-10">
                    <form:input class="form-control" path="birthDate" id="datetimepicker2"/>
                </div>
                <form:errors path="birthDate" cssClass="errors" />
            </div>
            <div class="form-group">
                <form:label path="description" class="col-xs-2 control-label">Information about yourself</form:label>
                <div class="col-xs-10">
                    <form:textarea class="form-control" path="description"/>
                </div>
                <form:errors path="description" cssClass="errors" />
            </div>
            <div class="form-group">
                <form:label path="password" class="col-xs-2 control-label">Password</form:label>
                <div class="col-xs-10">
                    <form:input class="form-control" path="password" type="password"/>
                </div>
                <form:errors path="password" cssClass="errors" />
            </div>
            <div class="form-group">
                <form:label path="confirmPassword" class="col-xs-2 control-label">Confirm Password</form:label>
                <div class="col-xs-10">
                    <form:input class="form-control" path="confirmPassword" type="password"/>
                </div>
                <form:errors path="confirmPassword" cssClass="errors" />
            </div>
            <div class="form-group">
                <div class="col-xs-offset-2 col-xs-10">
                    <input type="submit" value="Register" class="btn btn-default"/>
                </div>
            </div>
        </form:form>




    <%--</div>--%>

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
<script src="<c:url value='/resources/js/moment-with-locales.min.js'></c:url>"></script>
<script src="<c:url value='/resources/js/bootstrap-datetimepicker.min.js'></c:url>"></script>
<script type="text/javascript">
    $(function () {
        $('#datetimepicker2').datetimepicker({pickTime: false});
    });
</script>
</body>
</html>