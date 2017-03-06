<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" /> <!-- opens body and container -->
<body>
<div id="container">
    <c:import url="/titlebar.jsp" /> <!-- opens body and container -->
    <p>   </p>

    <div>
        <h3>Add a Song</h3>
        <form action="UpdateSong" method="POST">
            <table>
                <thead><tr><th>Field</th><th>Value</th></tr></thead>
                <tbody>
                <tr><td>id</td><td><input type="hidden" name="id" value=${Song.id}></td></tr>
                <tr><td>name</td><td><input type="text" name="Name" value=${Song.name} /></td></tr>
                <tr><td><label>location</label></td><td><input type="file" name="location" value=${Song.location} required></td></tr>
                <tr><td>password</td><td><input type="text" name="Performer" value=${Song.performer}  /></td></tr>
                <tr><td>password</td><td><input type="text" name="Duration" value=${Song.duration}  />" seconds"</td></tr>
            </table>
            <input type="submit" value="Submit" name="Enter" />
        </form>
    </div>

    <div id="main">
        <c:import url="/mainpanel.jsp" /> <!-- links to other places -->
    </div>   <!-- end main -->

    <c:import url="/footer.jsp" /> <!-- just a copywrite statement -->

</div>   <!-- end container -->
</body>
</html>