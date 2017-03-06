<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" /> <!-- opens body and container -->
<body>
<div id="container">
    <c:import url="/titlebar.jsp" /> <!-- opens body and container -->
    <p>   </p>

    <div>
        <h3>Select a Playlist</h3>
        <form action="ViewPlaylist" method="Get">
            <table>
               <c:forEach var="agenda" items="${playlists}">
                   <tr>
                        <td><input type="radio" name="listID" value=${agenda.id} /></td>
                        <td>${agenda.name}</td>
                   </tr>
                </c:forEach>
            </table>
            <p>   </p>
            <input type="submit" name="Enter" value="Enter" />
            <input type="submit" name="Delete" value="Delete" />
        </form>
    </div>

    <div id="main">
        <c:import url="/mainpanel.jsp" /> <!-- links to other places -->
    </div>   <!-- end main -->

    <c:import url="/footer.jsp" /> <!-- just a copywrite statement -->

</div>   <!-- end container -->
</body>
</html>

