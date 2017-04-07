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


    <c:set var="userID" value="${user_id}"></c:set>
    <!-- header -->
    <div id="header">
        <img src="images/logo.png" width = 800>

        <h1>noun: All the Music You Can Play</h1>
        <h2>${message}</h2>
        <c:if test="${userID > 0}">
            <h4>If you are not ${name}, please logout and log back in as yourself.</h4>
        </c:if>
    </div>
    <div id="menu">
        <c:set var="role" value="${user_role}"></c:set>
        <c:set var="boss" value="administrator"></c:set>

        <div class=pure-g">
            <div class="pure-u-1-5"><a href="ShowPlaylists">Music</a></div>
            <div class="pure-u-1-5"><a href="ShowMessages">Messages</a></div>
            <c:if test="${role eq boss}" >
                <div class="pure-u-1-5"><a href="ShowUsers">Administration</a></div>
            </c:if>
            <div class="pure-u-1-5"><a href="LogOut">LogOut</a></div>
        </div>
    </div>
    <!--end header -->
    <!-- main -->

