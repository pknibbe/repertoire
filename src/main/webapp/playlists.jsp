<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" /> <!-- opens body and container -->

        <h3>Select a Playlist</h3>
        <form action="ViewPlaylist" method="Post">
            <table>
               <c:forEach var="playbill" items="${playlists}">
                   <tr>
                        <td><input type="radio" name="listID" value=${playbill.id} /></td>
                        <td>${playbill.name}</td>
                   </tr>
                </c:forEach>
                <tr>
                    <td><input type="radio" name="listID" value=0 /></td>
                    <td>NewPlaylist: <input type="text" name="name"></td>
                </tr>
            </table>
            <p>   </p>
            <input type="submit" name="Enter" value="Enter" />
            <input type="submit" name="Delete" value="Delete" />
        </form>

    <c:import url="/footer.jsp" />


