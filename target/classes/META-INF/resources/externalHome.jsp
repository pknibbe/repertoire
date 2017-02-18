<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Repertoire</title>
    <link href="style.css" rel="stylesheet" type="text/css"/>
</head>

<body>

<div>
<h4>Login</h4>
<form action="/ExternalAction" method="POST">
    user name:  <input type="text" name="userName"  /><br />
    privileges: <input type="text" name="privileges" /><br />
    <input type="submit" value="Submit" name="Enter" />
</form>
</div>

<br /><br />

<div>

<h4>Request sign-in help</h4>
<form action="/ExternalAction" method="POST">
    Request an account:
    <input type="radio" name="options" value="account" /><br />
    request reminder of user name:
    <input type="radio" name="options" value="username" /><br />
    request password reset:
    <input type="radio" name="options" value="password" /><br />
    <input type="submit" name="George" value="Enter" />
</form>
</div>

</body>
