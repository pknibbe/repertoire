<%--@elvariable id="otherUsers" type="javax.servlet.http.HttpSession"--%>
<%--@elvariable id="sharingUsers" type="javax.servlet.http.HttpSession"--%>
<%--@elvariable id="listName" type="javax.servlet.http.HttpSession"--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" />
<body>
<c:import url="/bodyTop.jsp" />
<div id="container">
    <c:set var="otherCount" value="0"></c:set>
    <c:forEach var="person" items="${otherUsers}">
        <c:set var="otherCount" value="1"></c:set>
    </c:forEach>

    <c:set var="sharingCount" value="0"></c:set>
    <c:forEach var="person" items="${sharingUsers}">
        <c:set var="sharingCount" value="1"></c:set>
    </c:forEach>

    <c:if test="${otherCount > 0}">

        <div id="left">

            <h3> Share ${listName} playlist with other users</h3>
            <form action="SharePlaylist" method="POST">
                <table>
                    <thead><tr><th>Select</th><th>User</th></tr></thead>
                    <tbody>
                    <c:forEach var="user" items="${otherUsers}">
                        <tr>
                            <td><input type="checkbox" name=${user.id} /></td>
                            <td>${user.name}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table><br/><br/>
                <input type="submit" name="Share" value="Share" />
                <input type="submit" name="Cancel" value="Cancel" />
            </form>
        </div>
    </c:if>

    <c:if test="${sharingCount > 0}">

        <div id="request">

            <h3> Un-Share ${listName} playlist</h3>
            <form action="SharePlaylist" method="POST">
                <table>
                    <thead><tr><th>Select</th><th>User</th></tr></thead>
                    <tbody>
                    <c:forEach var="user" items="${sharingUsers}">
                        <tr>
                            <td><input type="checkbox" name=${user.id} /></td>
                            <td>${user.name}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table><br/><br/>
                <input type="submit" name="UnShare" value="UnShare" />
                <input type="submit" name="Cancel" value="Cancel" />
            </form>

        </div>
    </c:if>

</div>
<c:import url="/footer.jsp" />


</body>
</html>
