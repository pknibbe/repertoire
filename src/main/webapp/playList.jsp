<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" /> <!-- opens body and container -->
<body>
<div id="container">
    <c:import url="/titlebar.jsp" /> <!-- opens body and container -->
    <p>   </p>

    <div>
        <h3>Manage Playlist ${playlist.name}</h3>
        <form action="ProcessPlayListRequest" method="POST">
            <table>
                <input type="hidden" name="playlistID" value="${playlist.id}">
                <thead><tr><th>Select</th><th>Name</th><th>Performer</th><th>Duration</th></tr></thead>
                <tbody>
                    <c:forEach var="song" items="${listSongs}">
                        <tr>
                            <td><input type="radio" name="songID" value=${song.id} /></td>
                            <td>${song.name}</td>
                            <td>${song.performer}</td>
                            <td>${song.duration}</td>
                        </tr>
                    </c:forEach>

                    <tr>
                        <td><input type="radio" name="songID" value="0" /></td>
                        <td><select name="song">
                            <c:forEach var="ditty" items="${allSongs}">
                                <option value="${ditty.id}">${ditty.name}</option>
                            </c:forEach>
                        </select></td>
                        <td></td>
                        <td></td>
                    </tr>
                </tbody>
            </table><br/><br/>
            <input type="submit" name="submit" value="Add" />
            <input type="submit" name="submit" value="Delete" />
            <input type="submit" name="submit" value="Play" />
            <input type="submit" name="submit" value="About" />
        </form>
    </div>

    <div id="main">
        <c:import url="/mainpanel.jsp" /> <!-- links to other places -->
    </div>   <!-- end main -->

    <c:import url="/footer.jsp" /> <!-- just a copywrite statement -->

</div>   <!-- end container -->
</body>
</html>


