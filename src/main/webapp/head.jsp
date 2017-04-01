<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
Created by: Reality Software | www.realitysoftware.ca
Released by: Free Web Design Community | www.designity.org
Note: This is a free template released under the Creative Commons Attribution 3.0 license,
which means you can use it in any way you want provided you keep links to authors intact.
Don't want our links in template? You can pay a link removal fee: www.realitysoftware.ca/templates/
You can also purchase a PSD-file for this template.
-->
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <link href="style.css" rel="stylesheet" type="text/css"/>
</head>

<body>
    <c:set var="userID" value="${user_id}"></c:set>
    <!-- header -->
    <div id="header">
        <img src="images/logo.png" width = 800>

        <h1>noun: All the Music You Can Play</h1>
        <h2>${message}</h2>
        <p>  </p>
        <c:if test="userID gt 0">
            <h2>Welcome, ${name}. If you are not ${name}, please logout and log back in as yourself.</h2>
        </c:if>
    </div>
    <div id="menu">
        <c:set var="role" value="${user_role}"></c:set>
        <c:set var="boss" value="administrator"></c:set>

        <ul>
            <li><a href="ShowPlayLists">Music Center</a></li>
            <li><a href="ShowMessages">Message Center</a></li>
            <c:if test="${role eq boss}" >
                <li><a href="ShowUsers">Administration Center</a></li>
            </c:if>
            <li><a href="LogOut">LogOut</a></li>
        </ul>
    </div>
    <!--end header -->
    <!-- main -->

