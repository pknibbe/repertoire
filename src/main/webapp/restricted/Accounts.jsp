<%@include file="../head.jsp"%>
<html>
<c:set var="title" value="Account Management" />
<body>
<h2>${SessionMessage}</h2><br/><br/>

<div>
    <h2>Manage Accounts</h2>
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