<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" /> <!-- opens body and container -->
<div id="container">
    <div id="a">

        <h3>Select a Playlist</h3>
        <form action="ManagePlaylists" method="Post">
            <table>
                <thead><tr><th>Select</th><th>Name</th></tr></thead>
                <tbody>
                   <c:forEach var="playbill" items="${playlists}">
                       <tr>
                            <td><input type="radio" name="listID" value=${playbill.id} /></td>
                            <td><input type="text" name="name" value=${playbill.name}></td>
                       </tr>
                    </c:forEach>
                    <tr>
                        <td><input type="radio" name="listID" value=0 /></td>
                        <td>name: <input type="text" name="name"></td>
                    </tr>
                </tbody>
            </table>
            <p>   </p>

            <input type="submit" name="Manage" value="Manage" />
            <input type="submit" name="Share" value="Share" />
            <input type="submit" name="Delete" value="Delete" />
            <br />
            <input type="submit" name="Play" value="Play" />
            <input type="submit" name="Pause" value="Pause" />
            <input type="submit" name="Stop" value="Stop" />
            <input type="submit" name="Skip" value="Skip" />
            <input type="submit" name="Previous" value="Previous" />
        </form>
    </div>
</div>
    <c:import url="/footer.jsp" />


