<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" /> <!-- opens body and container -->
<body>
    <div id="container">
        <c:import url="/titlebar.jsp" /> <!-- opens body and container -->
        <p>   </p>

        <div>
            <h3>Manage Accounts</h3>
            <form action="UpdateAccounts" method="POST">
                <table>
                    <thead><tr><th>Select</th><th>ID</th><th>Name</th><th>Username</th><th>Password</th><th>Role</th></tr></thead>
                    <tbody>
                    <c:forEach var="person" items="${users}">
                        <tr>
                            <td><input type="radio" name="userID" value=${person.id} /></td>
                            <td>${person.id}</td>
                            <td>${person.name}</td>
                            <td>${person.user_name}</td>
                            <td>***************</td>
                            <td>${person.role_name}</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td><input type="radio" name="userID" value="0" /></td>
                        <td>New User</td>
                        <td><input type="text" name="Name" value="" /></td>
                        <td><input type="text" name="Username" value="" /></td>
                        <td><input type="text" name="Password" value="" /></td>
                        <td><select name="Role">
                            <option value="administrator">administrator</option>
                            <option value="registered-user">registered-user</option>
                        </select></td>
                    </tr>
                    </tbody>
                </table><br/><br/>
                <input type="submit" name="Update" value="Update" />
                <input type="submit" name="Delete" value="Delete" />
            </form>
        </div>

        <div id="main">
            <c:import url="/mainpanel.jsp" /> <!-- links to other places -->
        </div>   <!-- end main -->

        <c:import url="/footer.jsp" /> <!-- just a copywrite statement -->

    </div>   <!-- end container -->
</body>
</html>
