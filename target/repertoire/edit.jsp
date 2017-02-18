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
    <h4>My Playlist</h4>
    <form action="/Edit" method="POST">
        <input type="text" name="list1" value="list a" />
        <input type="submit" name="changeName" value="ChangeName" />
    </form>
</div>

<div>
    <br /><br />
    <form action="/Edit" method="POST">
       <h4>Name           Location           Performer            Duration</h4><br />
        <input type="text" value="Yada" name="name" />
        <input type="text" value="d://music/yada.mp3" name="location" />
        <input type="text" value="Yoda" name="performer" />
        <input type="text" value="5:04" name="duration" />
        <input type="submit" value="Remove" name="action" />
        <input type="submit" value="Up" name="name" />
        <input type="submit" value="Down" name="namo" />
        <input type="submit" value="Update" name="nombre" />
    </form>
</div>

</body>
