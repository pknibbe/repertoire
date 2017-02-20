<<<<<<< HEAD
<<<<<<< HEAD
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Account Management" />
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>$title</title>
    <link href="style.css" rel="stylesheet" type="text/css"/>
</head><html>
=======
=======
>>>>>>> parent of 162c7c9... Back to local version for now. AWS build complications too frustrating
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp"/>

<<<<<<< HEAD
>>>>>>> parent of 162c7c9... Back to local version for now. AWS build complications too frustrating
=======
>>>>>>> parent of 162c7c9... Back to local version for now. AWS build complications too frustrating
<body>
<h2>${SessionMessage}</h2><br/><br/>

<div>
<<<<<<< HEAD
<<<<<<< HEAD
    <h2>Manage Accounts</h2>
    <form action="UpdateAccounts" method="POST">
=======
    <h4>Manage Accounts</h4>
    <form action="/UpdateAccounts" method="POST">
>>>>>>> parent of 162c7c9... Back to local version for now. AWS build complications too frustrating
=======
    <h4>Manage Accounts</h4>
    <form action="/UpdateAccounts" method="POST">
>>>>>>> parent of 162c7c9... Back to local version for now. AWS build complications too frustrating
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
                        <option value="admin">Admin</option>
                        <option value="edit">Editor</option>
                        <option value="readOnly">Listener</option>
                    </select></td>
                </tr>
            </tbody>
        </table><br/><br/>
        <input type="submit" name="Update" value="Update" />
        <input type="submit" name="Delete" value="Delete" />
    </form>
</div>

</body>

</html>