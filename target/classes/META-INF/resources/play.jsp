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
    <form action="/Play" method="POST">
        Song1:<br />
        Song2:<br />
        <input type="submit" name="Play" value="Play" />
        <input type="submit" name="Pause" value="Pause" />
        <input type="submit" name="Stop" value="Stop" />
        <input type="submit" name="Skip" value="Skip" />
        <input type="submit" name="Shuffle" value="Shuffle" />
    </form>
</div>

<div>
    <iframe src="http://www.charlottefabrics.com" width="100%" height="4000" frameborder="0" scrolling="yes"></iframe>
</div>
</body>

