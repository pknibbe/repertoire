<%--
  Created by IntelliJ IDEA.
  User: peter
  Date: 2/11/2017
  Time: 4:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="taglib.jsp"%>
<c:set var="title" value="Test Home Page" />
<%@include file="head.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
        <h2>This is a test of the deployment</h2>

        <form action="ShowUsers" class="form-inline">
            <div class="form-group">
                <button type="submit" name="submit" value="submit" />
            </div>
        </form>
</body>
</html>
