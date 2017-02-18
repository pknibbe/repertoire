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
        <h4>My Playlists</h4>
    <form action="/Play" method="POST">
        Playlist1:
        <input type="radio" name="list1" value="list1" /><br />
        Playlist2:
        <input type="radio" name="list2" value="listb" /><br />
        Playlist3:
        <input type="radio" name="listc" value="list3" /><br />
        <input type="submit" name="Edit" value="Edit" />
        <input type="submit" name="Play" value="Play" />
    </form>
</div>

<br /><br />

<div>

    <form action="/MessageCenter" method="POST">
        <input type="submit" value="MessageCenter" name="MessageCenter" />
    </form>
</div>

<br /><br />

<div>


    <form action="/AccountCenter" method="POST">
        <input type="submit" value="AccountCenter" name="AccountCenter" />
    </form></div>

</body>
