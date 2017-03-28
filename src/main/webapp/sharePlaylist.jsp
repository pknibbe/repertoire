<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" /> <!-- opens body and container -->
<div id="container">
    <div id="a">

            <h3> Share ${playlist.name} </h3>
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
            <input type="submit" name="UnShare" value="UnShare" />
            <input type="submit" name="Cancel" value="Cancel" />
        </form>

    </div>
</div>
<c:import url="/footer.jsp" />


