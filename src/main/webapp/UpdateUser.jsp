<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Repertoire</title>
    <link href="style.css" rel="stylesheet" type="text/css"/>
</head>

<body>
        <h3>${sessionMessage}</h3><br/>
        <h4>Add a User</h4>
        <form action="/UpdateUserInformation" method="POST">
            <table>
                <thead><tr><th>Field</th><th>Value</th></tr></thead>
                <tbody>
                <tr><td>id</td><td><input type="hidden" name="id" value=${UserInfo.id}></td></tr>
                <tr><td>name</td><td><input type="text" name="Name" value=${UserInfo.name} /></td></tr>
                <tr><td>user name</td><td><input type="text" name="UserName" value=${UserInfo.user_name} /></td></tr>
                <tr><td>password</td><td><input type="text" name="NewPassword" value=${UserInfo.pw}  /></td></tr>
                <tr><td>role</td><td>
                    <select name="Role">
                        <option value=${UserInfo.role_name}>${UserInfo.role_name}</option>
                        <option value="admin">Admin</option>
                        <option value="edit">Editor</option>
                        <option value="readOnly">Listener</option>
                    </select></td></tr>
            </table>
            <input type="submit" value="Submit" name="Enter" />
        </form>
        <br /><br />

    </body>
</html>