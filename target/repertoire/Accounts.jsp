<%@include file="taglib.jsp"%>
<c:set var="title" value="Account Management" />
<%@include file="head.jsp"%>
<html>
<body>
<h3>${SessionMessage}</h3><br/><br/>

<div>
    <h4>Manage Accounts</h4>
    <form action="UpdateAccounts" method="POST">
        <table>
            <thead><tr><th>Select</th><th>ID</th><th>Name</th></tr></thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td><input type="radio" name="userID" value=${user.id} /></td>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                    </tr>
                </c:forEach>
                <tr>
                    <td><input type="radio" name="userID" value="0" /></td>
                    <td></td>
                    <td>New</td>
                </tr>
            </tbody>
        </table><br/><br/>
        Value for User Name:
        <input type="text" name="Name" value="" /><br />
        Value for Password:
        <input type="text" name="Password" value="" /><br />
        <input type="submit" name="Update" value="Update" />
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