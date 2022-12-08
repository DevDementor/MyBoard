<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <jsp:include page="../common/header.jsp"/>
    <h2>Spring MVC03</h2>
    <div class="panel panel-default">
        <div class="panel-heading">Panel Heading</div>
        <div class="panel-body">
            <form>
                <table>
                    <tr>
                        <td style="width: 110px; vertical-align: middle;">아이디</td>
                        <td><input id="memID" name="memID" class="form-control" type="text" maxlength="20"
                                   placeholder="아이디를 입력하세요."/></td>
                        <td style="width: 110px;">
                            <button type="button" class="btn btn-primary btn-sm" onclick="registerCheck()">중복확인</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div class="panel-footer pull-right">회원가입 양식</div>
    </div>
</div>

</body>
</html>