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
    <h4>My Messages</h4>
    <form action="/Read" method="GET">
        Message1:
        <input type="radio" name="read" value="list1" /><br />
        Message2:
        <input type="radio" name="read" value="listb" /><br />
        Message3:
        <input type="radio" name="read" value="list3" /><br />
        <input type="submit" name="Read" value="Read" />
    </form>
</div>

<div>
    <h4>New Message</h4>

    <form action="/Post" method="POST">
        Recipient:
        <select name="recipient" id="recipient">
            <option value="Peter">Peter</option>
            <option value="Marie">Marie</option>
            <option value="Rose">Rose</option>
        </select>
        <input type="text" value="Hello" name=content" /><br/>
        <input type="submit" value="Submit" name="Enter" />
    </form>
</div>

</body>
