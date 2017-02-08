<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp"/>

<body>
<h3>${SessionMessage}</h3><br/><br/>

<div>
    <h4>Manage Accounts</h4>
    <form action="/UpdateAccounts" method="POST">
        <table>
            <thead><tr><th>Select</th><th>ID</th><th>Name</th><th>UserName</th><th>Password</th><th>Privileges</th></tr></thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td><input type="radio" name="userID" value=${user.id} /></td>
                        <td>${user.id}</td>
                        <td><input type="text" name="Name" value="${user.name}" /></td>
                        <td><input type="text" name="UserName" value="${user.username}" /></td>
                        <td>"Password"</td>
                        <td><input type="text" name="privileges" value="${user.privileges}" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table><br/><br/>
        <input type="submit" name="Edit" value="Edit" />
        <input type="submit" name="Delete" value="Delete" />
    </form>
</div>

<div>
    <h4>MessageCenter</h4>

    <form action="/MessageCenter" method="POST">
        <input type="submit" value="Submit" name="Enter" />
    </form>
</div>

<br /><br />

<div>

    <h4>Account Center</h4>

    <form action="/AccountCenter" method="POST">
        <input type="submit" value="Submit" name="Enter" />
    </form></div>

</body>

</html>