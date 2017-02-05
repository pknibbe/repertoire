<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Repertoire</title>
    <link href="style.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<h3>${SessionMessage}</h3><br/><br/>

<div>
    <h4>Manage Accounts</h4>
    <form action="/Update" method="POST">
        username:
        <select value="username" id="username">
            <option value="Peter">Peter</option>
            <option value="Marie">Marie</option>
            <option value="Beth">Beth</option>
        </select>
        <input type="submit" name="Update" value="Edit" />
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
