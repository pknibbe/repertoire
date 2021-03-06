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

<!-- header -->
<div id="header">
    <div class=pure-g">
        <div class="pure-u-1-3"></div>
        <div class="pure-u-1-3">
            <img src="images/logo.png" width = 250 alt = "Repertoire">
        </div>
    </div>

    <div class=pure-g">

        <div class="pure-u-1-3"> </div>
        <div class="pure-u-1-3">noun: All the Music You Can Play</div>
        <div class="pure-u-1-4"></div>
        <h1></h1>
    </div>

    <c:if test="${user.id > 0}">
        <div class="pure-g" id="menu">
            <div class="pure-u-1-5"><a href="showPlaylists.jsp">Music</a></div>
            <div class="pure-u-1-5"><a href="ShowMessages">Messages</a></div>
            <div class="pure-u-1-5"><a href="ShowUsers">User Information</a></div>
            <div class="pure-u-1-5"><a href="LogOut">LogOut</a></div>
        </div>
    </c:if>

    <!--div class="pure-g">
        <div class="pure-u-1-1"><h2>${message}</h2></div>
        <div class="pure-u-1-1"><h4>${securityMessage}</h4></div>
        <div class="pure-u-1-1"><p>. </p></div>
        <div class="pure-u-1-1"><p>. </p></div>
        <div class="pure-u-1-1"><p>. </p></div>
    </div-->
</div>

    <!--end header -->
    <!-- main -->

